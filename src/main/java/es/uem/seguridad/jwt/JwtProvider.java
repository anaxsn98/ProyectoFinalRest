package es.uem.seguridad.jwt;

import org.springframework.stereotype.Component;

import es.uem.usuario.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.util.Date;

import org.springframework.security.core.Authentication;

@Component
public class JwtProvider {
	// constantes
	public static final String TOKEN_HEADER = "Authorization";// Encabezado necesario para hacer autentificacion
	public static final String TOKEN_PREFIX = "Bearer ";// prefijo que va a llegar
	public static final String TOKEN_TYPE = "JWT"; // tipo

	// clave secreta jwt.secret que se encuentra en properties
	private final String jwtSecreto = "EnunLugar1DeLamancha5Decuyonombrenomeacuerdo9";
	// 86400 es el jwt.expiration que se encuentra en properties que es la duracion
	// del token
	private final int jwtDuracionTokenEnSegundos = 86400;

	public String generateToken(Authentication authentication) {

		Usuario user = (Usuario) authentication.getPrincipal();

		Date tokenExpirationDate = new Date(System.currentTimeMillis() + (jwtDuracionTokenEnSegundos * 1000));
		// firma
		// hashea la clave
		return Jwts.builder().signWith(SignatureAlgorithm.HS512, Keys.hmacShaKeyFor(jwtSecreto.getBytes()))
				.setHeaderParam("typ", TOKEN_TYPE) // tipo de token
				.setSubject(Integer.toString(user.getId()))
				.setIssuedAt(new Date()).setExpiration(tokenExpirationDate)
				.claim("nombre", user.getNombre()).compact();

	}

	public int getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(jwtSecreto.getBytes())).parseClaimsJws(token)
				.getBody();

		return Integer.parseInt(claims.getSubject());

	}

	public boolean validateToken(String authToken) {

		try {
			Jwts.parser().setSigningKey(jwtSecreto.getBytes()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			System.out.println("Error en la firma del token JWT: " + ex.getMessage());
		} catch (MalformedJwtException ex) {
			System.out.println("Token malformado: " + ex.getMessage());
		} catch (ExpiredJwtException ex) {
			System.out.println("El token ha expirado: " + ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			System.out.println("Token JWT no soportado: " + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			System.out.println("JWT claims vacío");
		}
		return false;

	}
}
