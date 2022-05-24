package es.uem.usuario.modelo;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.uem.modelo.entidad.Planta;
import es.uem.usuario.dto.AltaUsuarioDto;

@Lazy
//Anotaciones Spring
@Component
@Scope("prototype")
//Anotaciones de JPA
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 6189678452627071360L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // valor generado por defecto
	@Column(name = "id_usuario")
	private int id;
	@Column(unique = true)
	private String correo;
	@Column(unique = true,name = "nombre_usuario")
	private String nombre;
	@Column(name = "contrasena")
	private String pwd;
	private String codigo_invernadero;
	@JsonIgnore
//	@ManyToMany(mappedBy = "usuarios", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<Planta> plantas;
	public Usuario() {
		super();
	}

	public Usuario(int id, String correo, String nombre, String pwd, String codigo_invernadero) {
		super();
		this.id = id;
		this.correo = correo;
		this.nombre = nombre;
		this.pwd = pwd;
		this.codigo_invernadero = codigo_invernadero;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public List<Planta> getPlantas() {
		return plantas;
	}

	public void setPlantas(List<Planta> plantas) {
		this.plantas = plantas;
	}

	public String getCodigo_invernadero() {
		return codigo_invernadero;
	}

	public void setCodigo_invernadero(String codigo_invernadero) {
		this.codigo_invernadero = codigo_invernadero;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", correo=" + correo + ", nombre=" + nombre + ", pwd=" + pwd
				+ ", codigo_invernadero=" + codigo_invernadero + "]";
	}

	/**
	 * Añadir una planta a la lista del usuario
	 * 
	 * @param p planta que se quiere añadir a la lista de plantas
	 */
	public void addPlanta(Planta p) {
		this.plantas.add(p);
	}

	public void cambiosUsuairo(AltaUsuarioDto altaUser) {
		
		if(!this.getNombre().equals(altaUser.getNombre())) //si los nombres no coinciden
			this.setNombre(altaUser.getNombre());
		
		
		if(!this.getCorreo().equals(altaUser.getCorreo())) //si los Correos no coinciden
			this.setCorreo(altaUser.getCorreo());
		
		if(!this.getPwd().equals(altaUser.getPwd())) //si los pwd no coinciden
			this.setPwd(altaUser.getPwd());
		
		
		if(!this.getCodigo_invernadero().equals(altaUser.getCodigo_invernadero())) //si los codigos no coinciden
			this.setCodigo_invernadero(altaUser.getCodigo_invernadero());
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return this.pwd;
	}

	@Override
	public String getUsername() {
		return this.nombre;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
