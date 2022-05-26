package es.uem.modelo.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.uem.modelo.entidad.Planta;

@Repository
public interface DaoPlanta extends JpaRepository<Planta, Integer> {

	// JpaData proporciona una convencion para la creacion metodos que hagan
	// peticiones a la bbdd. Si seguimos la convencion que nos marca JpaData
	// podemos hacer queries a bbdd de manera muy sencilla

	// Si el metodo empieza por "findBy" y luego ponemos el atributo que queramos
	// ,hacemos busquedas por ese atributo. Al devolver una lista nos devolvera
	// todas las coincidencias exactas, si pusieramos una unica Usuario, nos
	// devolveria la primera coincidencia
	public Planta findById(int id);

	public Planta findByNombre(String nombre);
	public Planta findByUsuario_Id(int id);
	public List<Planta> findAllByUsuario_id(int id);

	/**
	 * Busca todas planta con id del usuario
	 * 
	 * @param id id de la planta
	 * @return null si no se ha encontrado
	 */
	
	@Query(value = "SELECT * FROM personalizarplanta WHERE id_usuario= :id_user", nativeQuery = true)
	public List<Planta> buscarPlantasDeUsuario(@Param("id_user") int id);
	/**
	 * Busca una planta con id que le pases que sea la planta actual
	 * 
	 * @param id id de la planta
	 * @return null si no se ha encontrado
	 */
	
	@Query(value = "SELECT * FROM personalizarplanta WHERE id_usuario= :id_user and fecha_fin IS NULL", nativeQuery = true)
	public Planta buscarPlantaActual(@Param("id_user") int id);

	/**
	 * Añade el tipo de planta a la planta
	 * 
	 * @param id        id del tipo de planta
	 * @param id_planta id de la planta a la que se le quiere añadir el tipo de
	 *                  planta
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE personalizarplanta SET id_tipoplanta= :id_tipoPlanta WHERE id_planta= :id_planta", nativeQuery = true)
	public int UpdateTipoPlanta(@Param("id_tipoPlanta") int id, @Param("id_planta") int id_planta);
	

	/**
	 * Busca id del tipo planta con id de la planta
	 * 
	 * @param id id de la planta
	 * @return 0 si no se ha encontrado
	 */
	@Query(value = "SELECT id_tipoplanta FROM personalizarplanta WHERE id_planta = :id", nativeQuery = true)
	public int buscarIdTipoPlantaDePlanta(@Param("id") int id);

	public Planta deleteById(int id);

}