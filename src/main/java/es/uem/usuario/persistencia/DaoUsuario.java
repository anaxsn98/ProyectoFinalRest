package es.uem.usuario.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.uem.modelo.entidad.Planta;
import es.uem.usuario.modelo.Usuario;

@Repository
public interface DaoUsuario extends JpaRepository<Usuario, Integer> {

	/**
	 * Búsqueda de usuario por correo
	 * @param correo correo del usuario
	 * @return usuario si se ha encontrado o nunll en caso contrario
	 */
	public Usuario findByCorreo(String correo);

	/**
	 * Búsqueda de usuario por nombre
	 * @param nombre nombre del usuario
	 * @return usuario si se ha encontrado o nunll en caso contrario
	 */
	public Usuario findByNombre(String nombre);

	/**
	 * Búsqueda de usuario por id
	 * @param id id del usuario
	 * @return usuario si se ha encontrado o nunll en caso contrario
	 */
	public Usuario findById(int id);

	/**
	 * Eliminar usuario por id
	 * @param id id del usuario
	 * @return usuario si se ha encontrado o nunll en caso contrario
	 */
	public Usuario deleteById(int id);

}