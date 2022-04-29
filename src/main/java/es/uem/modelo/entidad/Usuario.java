package es.uem.modelo.entidad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//Anotaciones Spring
@Component
@Scope("prototype")
//Anotaciones de JPA
@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // valor generado por defecto
	private int id;
	private String correo;
	private String nombre;
	private String pwd;
	
	@ManyToMany(mappedBy = "usuarios",cascade=CascadeType.PERSIST)
	private List<Planta> plantas;

	public Usuario() {
		super();
	}

	public Usuario(int id, String correo, String nombre, String pwd) {
		super();
		this.id = id;
		this.correo = correo;
		this.nombre = nombre;
		this.pwd = pwd;
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
	
	public void addPlanta(Planta p) {
		this.plantas.add(p);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", correo=" + correo + ", nombre=" + nombre + ", pwd=" + pwd + "]";
	}

}
