package es.uem.modelo.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.uem.modelo.entidad.Planta;
import es.uem.modelo.entidad.Tiposplanta;
import es.uem.usuario.modelo.Usuario;

@Repository
public interface DaoTiposplanta extends JpaRepository<Tiposplanta, Integer> {
	/**
	 * Busca en la base de datos el tipo de planta con el id que se le pase por
	 * parámetro
	 * 
	 * @param id id del tipo de planta que se está buscando
	 * @return null si no se ha encontrado o el tipo de planta
	 */
	@Query(value = "SELECT * FROM tiposplanta WHERE id_tipoplanta= :id", nativeQuery = true)
	public Tiposplanta findById(@Param("id") int id);

	/**
	 * Búsqueda de todos los tipos de plantas que se encuentran en la base de datos
	 */
	public List<Tiposplanta> findAll();

}