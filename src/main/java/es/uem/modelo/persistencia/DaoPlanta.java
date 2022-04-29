package es.uem.modelo.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.uem.modelo.entidad.Planta;
import es.uem.modelo.entidad.Usuario;

@Repository
public interface DaoPlanta extends JpaRepository<Planta, Integer> {

	// JpaData proporciona una convencion para la creacion metodos que hagan
	// peticiones a la bbdd. Si seguimos la convencion que nos marca JpaData
	// podemos hacer queries a bbdd de manera muy sencilla

	// Si el metodo empieza por "findBy" y luego ponemos el atributo que queramos
	// ,hacemos busquedas por ese atributo. Al devolver una lista nos devolvera
	// todas las coincidencias exactas, si pusieramos una unica Usuario, nos
	// devolveria la primera coincidencia
	public Usuario findById(int id);
	public Usuario findByNombre(String nombre);
	
	public Usuario deleteById(int id);

}