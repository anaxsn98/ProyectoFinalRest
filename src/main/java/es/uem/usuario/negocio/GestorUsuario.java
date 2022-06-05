package es.uem.usuario.negocio;

import java.util.regex.Pattern;
import java.util.List;
import java.util.regex.Matcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.uem.modelo.entidad.Planta;
import es.uem.modelo.negocio.GestorPlanta;
import es.uem.usuario.dto.AltaUsuarioDto;
import es.uem.usuario.modelo.Usuario;
import es.uem.usuario.persistencia.DaoUsuario;

@Service
public class GestorUsuario implements UserDetailsService{
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private DaoUsuario daoUsuario;
	private static final String PATRON_VALIDACION_EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	@Autowired
	private GestorPlanta gestorPlanta;

	/**
	 * Método que comprueba que las reglas de negocio. Comprueba que el correo sea
	 * válido, que el nombre, el codigo de invernadero y el correo sean más de 4
	 * caracteres y que las contraseñas coincidan
	 * 
	 * @param nombre del usuairo
	 * @param correo del usuairo
	 * @param codigo del usuairo
	 * @param pwd1   del usuairo
	 * @param pwd2   del usuairo
	 * @return true si se cumplen las reglas de negocio false en caso contrario
	 */
	public boolean comprobarDatos(String nombre, String correo, String codigo, String pwd1, String pwd2) {
		Matcher matcher;
		Pattern pattern = Pattern.compile(PATRON_VALIDACION_EMAIL);

		if (nombre.length() < 6 || !pattern.matcher(correo).matches() || correo.length() < 6 || codigo.length() < 6)
			return false;

		if (!pwd1.equals(pwd2))
			return false;

		return true;
	}

	/**
	 * Da de alta un usuario. Comprueba que el correo sea válido, que el nombre, el
	 * codigo de invernadero y el correo sean más de 4 caracteres y que las
	 * contraseñas coincidan
	 * 
	 * @param usuario usuairo que se quiere dar de alta
	 * @return false si no se ha podido dar de alta el usuario, true en caso
	 *         contrario
	 */
	public boolean altaUsuairo(AltaUsuarioDto altaUsuarioDto) {
		Usuario usuario;

		if (comprobarDatos(altaUsuarioDto.getNombre(), altaUsuarioDto.getCorreo(),
				altaUsuarioDto.getCodigo_invernadero(), altaUsuarioDto.getPwd(), altaUsuarioDto.getPwd2())) {
			usuario = new Usuario();
			usuario.setCodigo_invernadero(altaUsuarioDto.getCodigo_invernadero());
			usuario.setCorreo(altaUsuarioDto.getCorreo());
			usuario.setNombre(altaUsuarioDto.getNombre());
			usuario.setPwd(passwordEncoder.encode(altaUsuarioDto.getPwd()));

			daoUsuario.save(usuario);
			return true;
		}

		return false;
	}

	/**
	 * Guardar modificaciones del usuario. Primero comprueba que el usuario existe en la bbdd.
	 * Luego comprueba que se siguen las reglas de negocio. Y por último guarda el usuario en la bbdd.
	 * @param altaUsuarioDto usuario con los datos nuevos
	 * @return true si se han guardado los cambios, false en caso contrario
	 */
	public boolean guardarCambiosUsuairo(AltaUsuarioDto altaUsuarioDto, int id) {
		Usuario user;
		user = daoUsuario.findById(id);
		
		if (user == null)
			return false;

		if (comprobarDatos(altaUsuarioDto.getNombre(), altaUsuarioDto.getCorreo(),
				altaUsuarioDto.getCodigo_invernadero(), altaUsuarioDto.getPwd(), altaUsuarioDto.getPwd2())) {

			user.cambiosUsuairo(altaUsuarioDto);
			System.out.println(user);
			daoUsuario.save(user);
			return true;
		}
		return false;
	}
	
	/**
	 * Buscar un usuario por nombre y contraseña
	 * 
	 * @param nombre nombre del usuario que quieres buscar
	 * @param pwd    contraseña del usuario que quieres buscar
	 * @return null o usuario encontrado
	 */
	public Usuario findByNombreAndPwd(String nombre, String pwd) {
		return daoUsuario.findByNombreAndPwd(nombre, pwd);
	}

	/**
	 * Buscar un usuario por correo y contraseña
	 * 
	 * @param correo correo del usuario que quieres buscar
	 * @param pwd    contraseña del usuario que quieres buscar
	 * @return null o usuario encontrado
	 */
	public Usuario findByCorreoAndPwd(String correo, String pwd) {
		return daoUsuario.findByCorreoAndPwd(correo, pwd);
	}

	/**
	 * Buscar un usuario por nombre
	 * 
	 * @param nombre nombre del usuario que quieres buscar
	 * @return null o usuario encontrado
	 */
	public Usuario findUsuarioByNombre(String nombre) {
		return daoUsuario.findByNombre(nombre);
	}

	/**
	 * Buscar un usuario por correo
	 * 
	 * @param correo correo del usuario que quieres buscar
	 * @return null o usuario encontrado
	 */
	public Usuario findUsuarioByCorreo(String correo) {
		return daoUsuario.findByCorreo(correo);
	}
	
	/**
	 * Eliminar usuario
	 * 
	 * @param id id del usuario que se quiere eliminar
	 * @return true si se ha eliminado, false en caso contrario
	 */
	public boolean bajaUsurio(int id) {
		//borrar plantas del usuario
		List<Planta> plantas =  gestorPlanta.findAllByUsuario_id(id);
		if(plantas != null) {
			for (Planta planta : plantas) {
				gestorPlanta.deleteById(planta.getId());
			}
		}
		
		daoUsuario.deleteById(id);
		return true;
	}
	/**
	 * Buscar un usuario por id
	 * 
	 * @param id id del usuario que quieres buscar
	 * @return null o usuario encontrado
	 */
	public Usuario findUsuarioById(int id) {
		return daoUsuario.findById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = daoUsuario.findByNombre(username);
		//Búsqueda por correo
		if(user == null)
			user = daoUsuario.findByCorreo(username);
		
		System.out.println(user);
		return user;
	}
}
