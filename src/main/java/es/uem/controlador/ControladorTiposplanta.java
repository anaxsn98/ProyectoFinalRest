package es.uem.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

	/**
	 * Lista de todos los tipos de plantas
	 * 
	 * @return lista de tipos de plantas
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/tipoplanta", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Tiposplanta>> getTipoPlantaById() {

		// Búsqueda por id
		List<Tiposplanta> t = daoTiposplanta.findAll();

		if (t != null) {
			return new ResponseEntity<List<Tiposplanta>>(t, HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<List<Tiposplanta>>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}
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
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/tipoplanta/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Tiposplanta> getTipoPlantaByNombre(@PathVariable("nombre") String nombre) {

		// Búsqueda por nombre
		Tiposplanta t = daoTiposplanta.findByNombre(nombre);

		if (t != null) {
			return new ResponseEntity<Tiposplanta>(t, HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Tiposplanta>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping(path = "tipoplanta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Tiposplanta> altaTipoPlanta(@RequestBody Tiposplanta p) {
		daoTiposplanta.save(p);
		return new ResponseEntity<Tiposplanta>(p, HttpStatus.CREATED);// 201 CREATED
	}
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping(path = "tipoplanta/{nombre}")
	public ResponseEntity<Tiposplanta> borrarTipoPlanta(@PathVariable("nombre") String nombre) {

		daoTiposplanta.deleteByNombre(nombre);
		Tiposplanta p = daoTiposplanta.findByNombre(nombre);
		if (p == null) {
			return new ResponseEntity<Tiposplanta>(HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Tiposplanta>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}
}
