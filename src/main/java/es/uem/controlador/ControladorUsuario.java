package es.uem.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uem.usuario.modelo.Usuario;
import es.uem.usuario.persistencia.DaoUsuario;

@RestController
public class ControladorUsuario {
	@Autowired
	private DaoUsuario daoUsuario;

	/**
	 * Buscar persona por nombre o correo en la base de datos
	 * 
	 * @param nombre nombre o correo de la persona que se quiere buscar en la bbdd
	 * @return el codigo 200 "OK" si existe o 404 NOT FOUND si no existe
	 */
	@GetMapping(path = "/usuarios/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> getPersonaByNombre(@PathVariable("nombre") String nombre) {
		System.out.println("Buscando usuario con nombre: " + nombre);
		//Búsqueda por nombre
		Usuario u = daoUsuario.findByNombre(nombre);
		//Búsqueda por correo
		if(u == null)
			 u = daoUsuario.findByCorreo(nombre);
		
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
	@GetMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> login(@RequestParam("nombre") String nombre,@RequestParam("pwd") String pwd) {
		System.out.println("Buscando usuario con nombre: " + nombre);
		//Búsqueda por nombre
		Usuario u = daoUsuario.findByNombreAndPwd(nombre,pwd);
		//Búsqueda por correo
		if(u == null)
			 u = daoUsuario.findByCorreoAndPwd(nombre,pwd);
		
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
	@PostMapping(path = "usuarios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> altaPersona(@RequestBody Usuario u) {
		System.out.println("altaPersona: objeto persona: " + u.toString());
		daoUsuario.save(u);
		return new ResponseEntity<Usuario>(u, HttpStatus.CREATED);// 201 CREATED
	}

	/**
	 * Modificar los datos del usuario
	 * @param id id del usuario que que quiere modificar
	 * @param u	usuario
	 * @return el codigo de respuesta 200 "OK" si existe o 404 NOT FOUND si no existe
	 */
	@PutMapping(path="usuarios/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> modificarPersona(
			@PathVariable("id") int id, 
			@RequestBody Usuario u) {
		System.out.println("ID a modificar: " + id);
		System.out.println("Datos a modificar: " + u);
		
		//daoUsuario.updatePwd(u.getPwd(),id);
		if(id == u.getId())
			daoUsuario.save(u);
		//buscamos el usuario en la bbbdd para comprobar que se ha actualizado la pwd
		Usuario user = daoUsuario.findById(id);
		if(u.getPwd() == user.getPwd()) {
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
	@DeleteMapping(path = "usuarios/{id}")
	public ResponseEntity<Usuario> borrarPersona(@PathVariable("id") int id) {
		System.out.println("ID a borrar: " + id);
		daoUsuario.deleteById(id);
		Usuario u = daoUsuario.findById(id);
		if (u == null) {
			return new ResponseEntity<Usuario>( HttpStatus.OK);// 200 OK
		} else {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);// 404 NOT FOUND
		}
	}
}
