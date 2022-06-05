package es.uem.controlador;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

import es.uem.modelo.entidad.Evento;
import es.uem.modelo.entidad.Planta;
import es.uem.modelo.negocio.GestorPlanta;

@RestController
public class ControladorPlanta {
	@Autowired
	private GestorPlanta gestorPlanta;

	/**
	 * Devuelve los eventos de la planta
	 * 
	 * @param nombre nombre de la planta que se quiere buscar en la bbdd
	 * @return el codigo 200 "OK" si existe o 404 NOT FOUND si no existe
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "usuarios/{id}/plantas/eventos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Evento>> getEvento(@PathVariable("id") int id_usuario) {
		Planta p = gestorPlanta.buscarPlantaActual(id_usuario);
		if (p != null)
			p.inicializarEventos();

		if (p.getEventos() != null) {
			return new ResponseEntity<List<Evento>>(p.getEventos(), HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<List<Evento>>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}

	/**
	 * Buscar planta por id de usuario que sea la actual es decir que la fecha final
	 * no exista
	 * 
	 * @param id del usuario
	 * @return planta actual del usuario
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "usuarios/{id}/plantas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Planta> buscarPlantaActual(@PathVariable("id") int id) {

		Planta p = gestorPlanta.buscarPlantaActual(id);

		if (p != null) {
			return new ResponseEntity<Planta>(p, HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Planta>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}

	/**
	 * Buscar todas las plantas de un usuario por el id de usuario
	 * 
	 * @param id id del usuario
	 * @return lista de las plantas del usuario
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "usuarios/{id}/plantas/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Planta>> buscarTodasLasPlantasDelUsuario(@PathVariable("id") int id) {

		List<Planta> p = gestorPlanta.findAllByUsuario_id(id);

		if (p != null) {
			return new ResponseEntity<List<Planta>>(p, HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<List<Planta>>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}

	/**
	 * Dar de alta una planta en la base de datos
	 * 
	 * @param u planta que queremos dar de alta
	 * @return codigo de respuesta 201 CREATED
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping(path = "usuarios/{id}/plantas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Planta> altaPlanta(@PathVariable("id") int id_user, @RequestBody Planta p) {

		Planta planta = gestorPlanta.guardar(p, id_user);

		return new ResponseEntity<Planta>(planta, HttpStatus.CREATED);// 201 CREATED
	}

	/**
	 * Modificar los datos de la planta
	 * 
	 * @param id id de la planta que quiere modificar
	 * @param p  planta
	 * @return el codigo de respuesta 200 "OK" si existe o 404 NOT FOUND si no
	 *         existe
	 */
	@PreAuthorize("isAuthenticated()")
	@PutMapping(path = "usuarios/{id}/plantas", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Planta> modificarPlanta(@PathVariable("id") int id_user, @RequestBody Planta p) {

		gestorPlanta.actualizar(p, id_user);

		return new ResponseEntity<Planta>(HttpStatus.OK);// 200 OK

	}

	@PreAuthorize("isAuthenticated()")
	@PutMapping(path = "usuarios/{id}/plantas/finalizar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Planta> terminarPlantaActual(@PathVariable("id") int id_user) {
		Planta p = gestorPlanta.finalizarPlantaActual(id_user);
		if(p != null) {
			return new ResponseEntity<Planta>(p, HttpStatus.OK);// 200 OK
		}else {
			return new ResponseEntity<Planta>(HttpStatus.NOT_FOUND);// 404 NOT FOUND	
		}
	}

	/**
	 * Eliminar de la base de datos una planta por id
	 * 
	 * @param id de la planta que se quiere eliminar
	 * @return el codigo de respuesta 200 "OK" si existe o 404 NOT FOUND si no
	 *         existe
	 */
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping(path = "plantas/{id}")
	public ResponseEntity<Planta> borrarPlanta(@PathVariable("id") int id) {

		Planta p = gestorPlanta.deleteById(id);
		if (p == null) {
			return new ResponseEntity<Planta>(HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Planta>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}
}
