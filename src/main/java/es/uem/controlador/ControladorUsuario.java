package es.uem.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uem.seguridad.jwt.JwtTokenProvider;
import es.uem.seguridad.jwt.modelo.JwtResponse;
import es.uem.usuario.dto.AltaUsuarioDto;
import es.uem.usuario.dto.UserDtoConverter;
import es.uem.usuario.modelo.Usuario;
import es.uem.usuario.negocio.GestorUsuario;
import es.uem.usuario.persistencia.DaoUsuario;

@RestController
public class ControladorUsuario {
	@Autowired
	private GestorUsuario gestorUsuario;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private UserDtoConverter converter;
	/**
	 * Buscar persona por nombre o correo en la base de datos
	 * 
	 * @param nombre nombre o correo de la persona que se quiere buscar en la bbdd
	 * @return el codigo 200 "OK" si existe o 404 NOT FOUND si no existe
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/usuarios/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> getPersonaByNombre(@PathVariable("nombre") String nombre) {
		System.out.println("Buscando usuario con nombre: " + nombre);
		//Búsqueda por nombre
		Usuario u = gestorUsuario.findUsuarioByNombre(nombre);
		//Búsqueda por correo
		if(u == null)
			 u = gestorUsuario.findUsuarioByCorreo(nombre);
		
		if (u != null) {
			return new ResponseEntity<Usuario>(u, HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}
	
	/**
	 * Comprobar que existe un usuario con la contraseña indicada contraseña
	 * 
	 * @param nombre nombre o correo de la persona que se quiere buscar en la bbdd
	 * @param pwd contrasenia
	 * @return el codigo 200 "OK" si existe o 404 NOT FOUND si no existe
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> login(@RequestParam("nombre") String nombre,@RequestParam("pwd") String pwd) {
		System.out.println("Buscando usuario con nombre: " + nombre);
		//Búsqueda por nombre
		Usuario u = gestorUsuario.findByNombreAndPwd(nombre,pwd);
		//Búsqueda por correo
		if(u == null)
			 u = gestorUsuario.findByCorreoAndPwd(nombre,pwd);
		
		System.out.println(u);
		if (u != null) {
			return new ResponseEntity<Usuario>(u, HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}

	/**
	 * Dar de alta un usuario en la base de datos
	 * 
	 * @param u usuario que queremos dar de alta
	 * @return codigo de respuesta 201 CREATED
	 */
//	@PostMapping(path = "/usuarios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public JwtResponse altaPersona(@RequestBody AltaUsuarioDto u) {
//		if(gestorUsuario.altaUsuairo(u)) {
//			try {
//				authenticate(u.getNombre(), u.getPwd());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			final UserDetails userDetails = gestorUsuario
//					.loadUserByUsername(u.getNombre());
//			Usuario user = gestorUsuario
//					.findUsuarioByNombre(u.getPwd());
//
//			final String token = tokenProvider.generateToken(userDetails,user.getId());
//
//			return new JwtResponse(token,Integer.toString(user.getId()),user.getNombre());
//		}
//		return null;
//	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	/**
	 * Modificar los datos del usuario
	 * @param id id del usuario que que quiere modificar
	 * @param u	usuario
	 * @return el codigo de respuesta 200 "OK" si existe o 404 NOT FOUND si no existe
	 */
	@PreAuthorize("isAuthenticated()")
	@PutMapping(path="usuarios/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> modificarPersona(
			@PathVariable("id") int id, 
			@RequestBody AltaUsuarioDto u) {
		System.out.println("ID a modificar: " + id);
		System.out.println("Datos a modificar: " + u);
		
		gestorUsuario.guardarCambiosUsuairo(u);
		//buscamos el usuario en la bbbdd para comprobar que se ha actualizado la pwd
		Usuario user = gestorUsuario.findUsuarioById(id);
		if(u.getPwd().equals(user.getPwd())) {
			return new ResponseEntity<Usuario>(HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
	
	/**
	 * Eliminar de la base de datos una persona por id 
	 * @param id de la persona que se quiere eliminar
	 * @return el codigo de respuesta 200 "OK" si existe o 404 NOT FOUND si no existe
	 */
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping(path = "usuarios/{id}")
	public ResponseEntity<Usuario> borrarPersona(@PathVariable("id") int id) {
		System.out.println("ID a borrar: " + id);
		gestorUsuario.bajaUsurio(id);
		Usuario u = gestorUsuario.findUsuarioById(id);
		if (u == null) {
			return new ResponseEntity<Usuario>( HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}
}
