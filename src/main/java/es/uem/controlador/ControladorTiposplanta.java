package es.uem.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.uem.modelo.entidad.Planta;
import es.uem.modelo.entidad.Tiposplanta;
import es.uem.modelo.persistencia.DaoPlanta;
import es.uem.modelo.persistencia.DaoTiposplanta;
import es.uem.usuario.modelo.Usuario;

@RestController
public class ControladorTiposplanta {
	@Autowired
	private DaoTiposplanta daoTiposplanta;
	
	/* PARA ACCEDER A TODOS LOS SERVICIOS DE ESTA CLASE SE REQUIERE QUE SE LES PASE UN TOKEN DE AUTENTIFICACIÓN*/

	/**
	 * Lista de todos los tipos de plantas que hay en la base de datos
	 * 
	 * @return lista de tipos de plantas y codigo de respuesta 200 ok o un 404 not
	 *         found si no se ha encontrado ningún tipoplanta
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/tipoplanta", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Tiposplanta>> getAllTipoPlanta() {

		// Búsqueda por id
		List<Tiposplanta> t = daoTiposplanta.findAll();

		if (t != null) {
			return new ResponseEntity<List<Tiposplanta>>(t, HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<List<Tiposplanta>>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}
	
	/**
	 * Búsqueda de tiposplanta por id
	 * @param id id de tipo planta 
	 * @return si se ha encontrado devuelve un tiposplanta y codigo de respuesta 200 ok o un 404 not
	 *         found si no se ha encontrado ningún tipoplanta con ese id
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/tipoplanta/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Tiposplanta> getTipoPlantaById(@PathVariable("id") int id) {

		// Búsqueda por id
		Tiposplanta t = daoTiposplanta.findById(id);

		if (t != null) {
			return new ResponseEntity<Tiposplanta>(t, HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Tiposplanta>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}
}
