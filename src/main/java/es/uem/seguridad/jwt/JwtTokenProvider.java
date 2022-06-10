package es.uem.seguridad.jwt;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

@Component
public class JwtTokenProvider {

	@Value("${jwt.expiration}")
	public long JWT_TOKEN_VALIDITY;

	@Value("${jwt.secret}")
	private String secret;

	/**
	 * recuperar el nombre de usuario del token jwt
	 * @param token
	 * @return nombre del usuario
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * recuperar la fecha de caducidad del token jwt
	 * 
	 * @param token
	 * @return fecha de caducidad del token
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * para recuperar cualquier información del token, necesitaremos la clave
	 * secreta
	 * 
	 * @param token token
	 * @return
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * comprobar si el token ha caducado
	 * 
	 * @param token token que se quiere comprobar
	 * @return true si ha expirado false en caso contrario
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * Generar token para el usuario guardando en el el id de usuario
	 * 
	 * @param userDetails datos del usuario
	 * @param id          id del usuario para guardarlo en el token
	 * @return el token generado
	 */
	public String generateToken(UserDetails userDetails, int id) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", id);
		return doGenerateToken(claims, userDetails.getUsername());
	}

	// Generar un token
	// 1. Definir reclamos del token, como Emisor, Vencimiento, Asunto y la ID
	// 2. Firme el JWT utilizando el algoritmo HS512 y la clave secreta.
	// 3. Según JWS Compact Serialization
	// (https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compactación del JWT en una cadena segura para URL
	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * Validar token
	 * 
	 * @param token       token
	 * @param userDetails datos del usuario
	 * @return true si el token es válido false en caso contrario
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
