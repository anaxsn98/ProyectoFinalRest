package es.uem.modelo.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

//Anotaciones Spring
@Component
@Scope("prototype")
//Anotaciones de JPA
@Entity
public class Tiposplanta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_tipoplanta;
	@Column(name = "nombre_tipo_planta")
	private String nombre;
	private String img_url;
	@JsonIgnore
	@OneToMany(mappedBy = "tiposplanta")
	private List<Planta> planta;

	public Tiposplanta(int id_tipoplanta, String nombre, String img_url) {
		super();
		this.id_tipoplanta = id_tipoplanta;
		this.nombre = nombre;
		this.img_url = img_url;
	}

	public Tiposplanta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId_tipoplanta() {
		return id_tipoplanta;
	}

	public void setId_tipoplanta(int id_tipoplanta) {
		this.id_tipoplanta = id_tipoplanta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public List<Planta> getPlanta() {
		return planta;
	}

	public void setPlanta(List<Planta> planta) {
		this.planta = planta;
	}
	
	public void addPlanta(Planta p) {
		this.planta.add(p);
	}

}
