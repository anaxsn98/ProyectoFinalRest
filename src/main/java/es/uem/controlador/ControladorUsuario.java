package es.uem.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uem.usuario.dto.AltaUsuarioDto;
import es.uem.usuario.modelo.Usuario;
import es.uem.usuario.negocio.GestorUsuario;

@RestController
public class ControladorUsuario {
	@Autowired
	private GestorUsuario gestorUsuario;

	/*
	 * PARA ACCEDER A TODOS LOS SERVICIOS DE ESTA CLASE SE REQUIERE QUE SE LES PASE
	 * UN TOKEN DE AUTENTIFICACIÓN
	 */

	/**
	 * Busca una persona por id
	 * 
	 * @param id de la persona
	 * @return Devuelve el usuario y el codigo de respuesta 200 ok o un 404 not
	 *         found si no se ha encontrado un usuario con ese id
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
	 * Modificar los datos del usuario
	 * 
	 * @param id id del usuario
	 * @param u  datos del usuario a modificar
	 * @return Devuelve el codigo de respuesta 200 ok si se ha modificado el usuario
	 *         o 404 not found si no se ha encontrado un usuario con ese id
	 */
	@PreAuthorize("isAuthenticated()")
	@PutMapping(path = "usuarios/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> modificarPersona(@PathVariable("id") int id, @RequestBody AltaUsuarioDto u) {
		if (gestorUsuario.guardarCambiosUsuairo(u, id)) {
			return new ResponseEntity<Usuario>(HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}

	/**
	 * Eliminar de la base de datos un usuario por id eliminando a su vez todas las
	 * plantas que tenga asociadas
	 *
	 * @param id id del usuario
	 * @return Devuelve el codigo de respuesta 200 ok si se ha dado de baja el
	 *         usuario o 404 not found si no se ha encontrado un usuario con ese id
	 */
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping(path = "usuarios/{id}")
	public ResponseEntity<Usuario> borrarPersona(@PathVariable("id") int id) {

		if (gestorUsuario.bajaUsurio(id)) {
			return new ResponseEntity<Usuario>(HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}
}
