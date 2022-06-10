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
	
	/* PARA ACCEDER A TODOS LOS SERVICIOS DE ESTA CLASE SE REQUIERE QUE SE LES PASE UN TOKEN DE AUTENTIFICACIÓN*/

	/**
	 * Devuelva una lista de eventos de la planta actual. En caso de que el usuario
	 * no tenga una planta actual devolverá 404 not found
	 * 
	 * @param id_usuario id de usuario
	 * @return Una lista de eventos y el codigo de respuesta 200 ok o un 404 not
	 *         found si el usuario no tiene ninguna planta actual
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "usuarios/{id}/plantas/eventos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Evento>> getEvento(@PathVariable("id") int id_usuario) {
		Planta p = gestorPlanta.buscarPlantaActual(id_usuario);
		if (p != null) {
			p.inicializarEventos();
			return new ResponseEntity<List<Evento>>(p.getEventos(), HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<List<Evento>>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}

	/**
	 * Devuelve una lista de porcentajes de luz, riego y ventilación respectivamente
	 * en caso de que el usuario tenga una planta actual
	 * 
	 * @param id_usuario id de usuario
	 * @return Una lista de porcentajes y el codigo de respuesta 200 ok o un 404 not
	 *         found si el usuario no tiene ninguna planta actual
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "usuarios/{id}/plantas/progress", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Integer>> getProgressBar(@PathVariable("id") int id_usuario) {
		List<Integer> lista = gestorPlanta.progressbar(id_usuario);
		if (lista != null) {
			return new ResponseEntity<List<Integer>>(lista, HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<List<Integer>>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}

	/**
	 * Busca la planta que está actualmente en el invernadero del usuario
	 * 
	 * @param id id del usuario
	 * @return Devuelve la planta actual y el codigo de respuesta 200 ok o un 404
	 *         not found si el usuario no tiene ninguna planta actual
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
	 * Búsqueda de todas las plantas que ha tenido y tiene el usuario
	 * 
	 * @param id id del usuario
	 * @return Devuelve una lista de planta y el codigo de respuesta 200 ok o un 404
	 *         not found si el usuario no tiene ninguna planta
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
	 * @param id_user id de usuario
	 * @param p       planta que se quiere dar de alta
	 * @return el codigo de respuesta 201 created
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
	 * @param id id del usuairo
	 * @param p  planta
	 * @return el codigo de respuesta 200 "OK"
	 */
	@PreAuthorize("isAuthenticated()")
	@PutMapping(path = "usuarios/{id}/plantas", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Planta> modificarPlanta(@PathVariable("id") int id_user, @RequestBody Planta p) {

		gestorPlanta.actualizar(p, id_user);

		return new ResponseEntity<Planta>(HttpStatus.OK);// 200 OK

	}

	/**
	 * Comprueba si el usuairo tiene una planta actual y en caso de tenerla la da de
	 * baja del invernadero es decir pone su fecha fin con la fecha actual
	 * 
	 * @param id_user id del usuario
	 * @return Devuelve la planta modificada y el codigo de respuesta 200 ok o un
	 *         404 not found si el usuario no tiene ninguna planta actual
	 */
	@PreAuthorize("isAuthenticated()")
	@PutMapping(path = "usuarios/{id}/plantas/finalizar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Planta> terminarPlantaActual(@PathVariable("id") int id_user) {
		Planta p = gestorPlanta.finalizarPlantaActual(id_user);
		if (p != null) {
			return new ResponseEntity<Planta>(p, HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Planta>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}

	/**
	 * Eliminar una planta de la base de datos
	 * 
	 * @param id id de la planta que se quiere eliminar
	 * @return Devuelve el codigo de respuesta 200 ok si se ha eliminado o un
	 *         404 not found si no se ha encontrado una planta con ese id
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
