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

	/**
	 * Búsqueda de planta por id
	 * 
	 * @param id id de la planta
	 * @return la planta si se ha encontrado o null
	 */
	public Planta findById(int id);

	/**
	 * Búsqueda de planta por id de usuario
	 * 
	 * @param id id de usuario
	 * @return la planta si se ha encontrado o null
	 */
	public Planta findByUsuario_Id(int id);

	/**
	 * Búsqueda de todas las plantas por id de usuario
	 * @param id id de usuario
	 * @return lista de plantas o null
	 */
	public List<Planta> findAllByUsuario_id(int id);

	/**
	 * Busca la planta actual de un usuario
	 * 
	 * @param id id del usuairo
	 * @return null si no se ha encontrado  o la planta si se ha encontrado
	 */
	@Query(value = "SELECT * FROM personalizarplanta WHERE id_usuario= :id_user and fecha_fin IS NULL", nativeQuery = true)
	public Planta buscarPlantaActual(@Param("id_user") int id);

	/**
	 * Borrar todas las plantas del usuairo
	 * 
	 * @param id id de la planta
	 * @return 0 si no se ha encontrado
	 */
	@Query(value = "DELETE FROM personalizarplanta WHERE id_usuario = :id", nativeQuery = true)
	public int deleteByIdUser(@Param("id") int id);

	/**
	 * Borrar planta por id
	 * @param id de la planta que se quiere borrar
	 * @return planta 
	 */
	public Planta deleteById(int id);

}