package es.uem.seguridad.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.util.StringUtils;

import es.uem.usuario.modelo.Usuario;
import es.uem.usuario.negocio.GestorUsuario;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private GestorUsuario gestorUsuario;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getJwtFromRequest(request);

			//comprobamos que el estring no esté vacío y lo validamos
			if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
				int userId = tokenProvider.getUserIdFromJWT(token);

				Usuario user =  gestorUsuario.findUsuarioById(userId);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
						null, user.getAuthorities());
				
				//No influye son detalles solo es por si hubiera un sesion id, la dirección remota...
				authentication.setDetails(new WebAuthenticationDetails(request));

				//contexto de seguridad donde guardamos la autenticación con el usuario
				SecurityContextHolder.getContext().setAuthentication(authentication);

			}
		} catch (Exception e) {
			System.out.println("No se ha podido establecer la autenticación de usuario en el contexto de seguridad");
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * Método que recibe la petición y devuelve el token
	 * 
	 * @param request request de la que se va a sacar el header
	 * @return devuelve del header de la request la parte correspondiente al token quitando el prefijo,
	 * 			en caso contrario se devuelve null
	 */
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(JwtTokenProvider.TOKEN_HEADER);
		
		//Si el bearerToken tiene texto y empieza por el prefijo Bearer
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
			return bearerToken.substring(JwtTokenProvider.TOKEN_PREFIX.length(), bearerToken.length());
		}
		return null;
	}

}
