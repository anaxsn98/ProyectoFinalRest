package es.uem.seguridad.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.uem.seguridad.jwt.modelo.JwtUserResponse;
import es.uem.seguridad.jwt.modelo.LoginRequest;
import es.uem.usuario.dto.GetUsuarioDto;
import es.uem.usuario.dto.UserDtoConverter;
import es.uem.usuario.modelo.Usuario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private UserDtoConverter converter;

	@PostMapping("/login")
	public JwtUserResponse login(@RequestBody LoginRequest loginRequest) {

		// autenticar al usuario
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()

				));

		// lo guardamos en el contexto de seguridad
		SecurityContextHolder.getContext().setAuthentication(authentication);

		Usuario user = (Usuario) authentication.getPrincipal();
		// generamos el token
		String jwtToken = tokenProvider.generateToken(authentication);

		return convertUserEntityAndTokenToJwtUserResponse(user, jwtToken);

	}
	
	//@AuthenticationPrincipal lo obtenemos del contexto de seguridad
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/user/me")
	public GetUsuarioDto me(@AuthenticationPrincipal Usuario user) {
		return converter.convertUserEntityToGetUserDto(user);
	}

	private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(Usuario user, String jwtToken) {
		return new JwtUserResponse(user.getId(), user.getCorreo(), user.getNombre(), user.getCodigo_invernadero(),
				jwtToken);

	}

}