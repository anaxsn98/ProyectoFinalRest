package es.uem.controlador;

import java.util.ArrayList;
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
import es.uem.modelo.entidad.Tiposplanta;
import es.uem.modelo.persistencia.DaoPlanta;
import es.uem.modelo.persistencia.DaoTiposplanta;
import es.uem.usuario.modelo.Usuario;

@RestController
public class ControladorPlanta {
	@Autowired
	private DaoPlanta daoPlanta;
	@Autowired
	private DaoTiposplanta daoTiposplanta;
	private List<Planta> plantasPorDefecto;//0 Hierba buena, 1 Perejil, 2 Cherrys
	
	/**
	 * Método que inicializa los valores de las plantas por defecto
	 */
	public void inicializarListaPlantasPorDefecto() {
		List<Tiposplanta> tipos = daoTiposplanta.findAll();
		//1 Hierba buena, 2 Perejil, 3 Cherrys, 4 Personalizada
		plantasPorDefecto = new ArrayList<>();
		Planta p = new Planta();
		Planta p2 = new Planta();
		Planta p3 = new Planta();

		p.setMl(1);
		p.setMinLuz(10);
		p.setMinVentilador(10);
		
		p.setRegar(1);
		p.setLuz(1);
		p.setVentilador(1);
		
		p.setIntervaloTiempoRiego(2);
		p.setIntervaloTiempoLuz(2);
		p.setIntervaloTiempoVentilador(1);
		
		p.setTiposplanta(tipos.get(0));
		
		p2.setMl(1);
		p2.setMinLuz(10);
		p2.setMinVentilador(10);
		
		p2.setRegar(1);
		p2.setLuz(1);
		p2.setVentilador(1);
		
		p2.setIntervaloTiempoRiego(2);
		p2.setIntervaloTiempoLuz(2);
		p2.setIntervaloTiempoVentilador(1);
		
		p2.setTiposplanta(tipos.get(1));
		
		p3.setMl(1);
		p3.setMinLuz(10);
		p3.setMinVentilador(10);
		
		p3.setRegar(1);
		p3.setLuz(1);
		p3.setVentilador(1);
		
		p3.setIntervaloTiempoRiego(2);
		p3.setIntervaloTiempoLuz(2);
		p3.setIntervaloTiempoVentilador(1);
		
		p3.setTiposplanta(tipos.get(2));
		
		plantasPorDefecto.add(p);//1 hierba buena
		plantasPorDefecto.add(p2);//2 Perejil
		plantasPorDefecto.add(p3);//3 Cherrys
	}
	
	/**
	 * Devuelve los eventos de la planta
	 * 
	 * @param nombre nombre de la planta que se quiere buscar en la bbdd
	 * @return el codigo 200 "OK" si existe o 404 NOT FOUND si no existe
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "plantas/{id}/eventos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Evento> > getEvento(@PathVariable("id") int id) {
		Planta p = daoPlanta.buscarPlantaActual(id);
		if(p!=null)
			p.inicializarEventos();

		if (p.getEventos() != null) {
			return new ResponseEntity<List<Evento> >(p.getEventos(), HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<List<Evento> >(HttpStatus.NOT_FOUND);// 404 NOT FOUND
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
	@GetMapping(path = "plantas/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Planta> buscarPlantaActual(@PathVariable("id") int id) {
		int id_tipoplanta;
		Tiposplanta t;
		
		Planta p = daoPlanta.buscarPlantaActual(id);
		id_tipoplanta = daoPlanta.buscarIdTipoPlantaDePlanta(p.getId());
		t = daoTiposplanta.findById(id_tipoplanta);
		p.setImg(t.getImg_url());

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
	@GetMapping(path = "usuarios/{id}/plantas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Planta>> buscarTodasLasPlantasDelUsuario(@PathVariable("id") int id) {
		int id_tipoplanta;
			
		List<Planta> p = daoPlanta.findAllByUsuario_id(id);
		for (Planta planta : p) {
			id_tipoplanta = daoPlanta.buscarIdTipoPlantaDePlanta(planta.getId());
			Tiposplanta t = daoTiposplanta.findById(id_tipoplanta);
			planta.setImg(t.getImg_url());
		}
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
	@PostMapping(path = "plantas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Planta> altaPersona(@RequestBody Planta p) {
		
		
		if(plantasPorDefecto == null) {
			System.out.println("inicializado");
			inicializarListaPlantasPorDefecto();
		}
		//miramos el tipo de planta que es 
		if(p.getTiposplanta() != null && p.getTiposplanta().getId_tipoplanta() < 4) {// si es una por defecto 
			Planta planta = plantasPorDefecto.get(p.getTiposplanta().getId_tipoplanta()-1);
			p.setMl(planta.getMl());
			p.setMinLuz(planta.getMinLuz());
			p.setMinVentilador(planta.getMinVentilador());
			
			p.setRegar(planta.getRegar());
			p.setLuz(planta.getLuz());
			p.setVentilador(planta.getVentilador());
			
			p.setIntervaloTiempoRiego(planta.getIntervaloTiempoRiego());
			p.setIntervaloTiempoLuz(planta.getIntervaloTiempoLuz());
			p.setIntervaloTiempoVentilador(planta.getIntervaloTiempoVentilador());
			
			//p.setTiposplanta(planta.getTiposplanta());
					
			p.setNombre(p.getNombre());
			p.setFechaIni(p.getFechaIni());
			p.setUsuario(p.getUsuario());
			System.out.println(planta);
		}
		
		daoPlanta.save(p);
		
		return new ResponseEntity<Planta>(p, HttpStatus.CREATED);// 201 CREATED
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
	@PutMapping(path = "plantas/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Planta> modificarPlanta(@PathVariable("id") int id, @RequestBody Planta p) {
		System.out.println("ID a modificar: " + id);
		System.out.println("Datos a modificar: " + p);

		// DaoPlanta.updatePwd(u.getPwd(),id);
		
		if (id == p.getId())
			daoPlanta.save(p);
			
		// buscamos el usuario en la bbbdd para comprobar que se ha actualizado la pwd
		Planta planta = daoPlanta.findById(id);
		if (planta != null) {
			return new ResponseEntity<Planta>(HttpStatus.OK);// 200 OK
		} else {
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
		System.out.println("ID a borrar: " + id);
		daoPlanta.deleteById(id);
		Planta p = daoPlanta.findById(id);
		if (p == null) {
			return new ResponseEntity<Planta>(HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Planta>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}
}
