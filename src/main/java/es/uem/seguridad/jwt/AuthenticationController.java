package es.uem.seguridad.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.uem.seguridad.jwt.modelo.JwtRequest;
import es.uem.seguridad.jwt.modelo.JwtResponse;
import es.uem.usuario.dto.AltaUsuarioDto;
import es.uem.usuario.dto.GetUsuarioDto;
import es.uem.usuario.dto.UserDtoConverter;
import es.uem.usuario.modelo.Usuario;
import es.uem.usuario.negocio.GestorUsuario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private UserDtoConverter converter;
	@Autowired
	private GestorUsuario gestorUsuario;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping(path ="/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public JwtResponse login(@RequestBody JwtRequest authenticationRequest) {

		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final UserDetails userDetails = gestorUsuario
				.loadUserByUsername(authenticationRequest.getUsername());
		
		Usuario user = gestorUsuario
				.findUsuarioByNombre(authenticationRequest.getUsername());
		
		if(user == null)
			user = gestorUsuario
			.findUsuarioByCorreo(authenticationRequest.getUsername());
	
		if (user == null)
			return null;
					
		if(!passwordEncoder.matches(authenticationRequest.getPassword(),user.getPwd())) {
			return null;
		}

		final String token = tokenProvider.generateToken(userDetails,user.getId());

		return new JwtResponse(token,Integer.toString(user.getId()),user.getNombre());
	}
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	private JwtResponse convertUserEntityAndTokenToJwtUserResponse(Usuario user, String jwtToken) {
		return new JwtResponse(jwtToken, Integer.toString(user.getId()),user.getNombre());

	}

	@PostMapping(path = "usuarios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public JwtResponse altaPersona(@RequestBody AltaUsuarioDto u) {
		if(gestorUsuario.altaUsuairo(u)) {
			System.out.println("dado de alta");
			try {
				authenticate(u.getNombre(), u.getPwd());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			final UserDetails userDetails = gestorUsuario
					.loadUserByUsername(u.getNombre());
			Usuario user = gestorUsuario
					.findUsuarioByNombre(u.getNombre());

			final String token = tokenProvider.generateToken(userDetails,user.getId());

			return new JwtResponse(token,Integer.toString(user.getId()),user.getNombre());
		}
		System.out.println("no dado de alta");
		return null;
	}
}