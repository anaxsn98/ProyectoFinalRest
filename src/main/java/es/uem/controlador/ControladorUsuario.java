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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uem.modelo.negocio.GestorPlanta;
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
	private GestorPlanta gestorPlanta;
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
	@GetMapping(path = "/usuarios/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> getPersonaById(@PathVariable("id") int id) {
		
		Usuario u = gestorUsuario.findUsuarioById(id);
		
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
		
		if(gestorUsuario.guardarCambiosUsuairo(u,id)) {
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
		Usuario u = gestorUsuario.findUsuarioById(id);
		if(u != null)//borrar todas las plantas del usuario
			gestorPlanta.deleteByIdUser(id);

		
		gestorUsuario.bajaUsurio(id);
		u = gestorUsuario.findUsuarioById(id);
		if (u == null) {
			return new ResponseEntity<Usuario>( HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}
}
