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

	// devolveria la primera coincidencia
	public Usuario findByCorreo(String correo);

	public Usuario findByNombre(String nombre);

	public Usuario findById(int id);

	public Usuario findByNombreAndPwd(String nombre,String pwd);
	
	public Usuario findByCorreoAndPwd(String correo,String pwd);
	/**
	 * Cambiar la contraseña del usuario
	 * @param pwd nueva contraseña que quiere usar el usuario
	 * @param id id del usuario
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE usuario u SET u.contrasena = :pwd_user  WHERE u.id_usuario = :id_user", nativeQuery = true)
	public void updatePwd(@Param("pwd_user") String pwd, @Param("id_user") int id);

	/**
	 * Busqueda del id del usuario 
	 * @param correo correo del usaurio
	 * @return id numero que es el id del usuario, 0 si no existe
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "SELECT id_usuario FROM usuario WHERE correo = :correo", nativeQuery = true)
	public int findIdByCorreo(@Param("correo") String correo);

	public Usuario deleteById(int id);

}