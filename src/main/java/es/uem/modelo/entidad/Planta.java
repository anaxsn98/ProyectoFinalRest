package es.uem.modelo.entidad;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.JoinColumn;


//Anotaciones Spring
@Component
@Scope("prototype")
//Anotaciones de JPA
@Entity
@Table(name = "planta")
public class Planta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	private String tipo;
	private String fechaIni;
	private String fechaFin;
	private String Observaciones;
	private int regar;// 0 sí 1 no
	private int intervaloTiempoRiego;// 0 una vez al dia, 1 una vez a la semana, 2 una vez al mes
	private int ml;
	private int luz; // 0 sí 1 no
	private int intervaloTiempoLuz;// 0 una vez al dia, 1 una vez a la semana, 2 una vez al mes
	private int minLuz;
	private int ventilador; // 0 sí 1 no
	private int intervaloTiempoVentilador;// 0 una vez al dia, 1 una vez a la semana, 2 una vez al mes
	private int minVentilador;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "Plantas_por_usuario", joinColumns = {
			@JoinColumn(name = "id_planta", referencedColumnName = "id") }, 
			inverseJoinColumns = { @JoinColumn(name = "id_usuario", referencedColumnName = "id") }) 
	private List<Usuario> usuarios;
	

	public Planta() {
		super();
	}

	public Planta(int id, String nombre, String tipo, String fechaIni, String fechaFin, String observaciones, int regar,
			int intervaloTiempoRiego, int ml, int luz, int intervaloTiempoLuz, int minLuz, int ventilador,
			int intervaloTiempoVentilador, int minVentilador) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		Observaciones = observaciones;
		this.regar = regar;
		this.intervaloTiempoRiego = intervaloTiempoRiego;
		this.ml = ml;
		this.luz = luz;
		this.intervaloTiempoLuz = intervaloTiempoLuz;
		this.minLuz = minLuz;
		this.ventilador = ventilador;
		this.intervaloTiempoVentilador = intervaloTiempoVentilador;
		this.minVentilador = minVentilador;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getObservaciones() {
		return Observaciones;
	}

	public void setObservaciones(String observaciones) {
		Observaciones = observaciones;
	}

	public int getRegar() {
		return regar;
	}

	public void setRegar(int regar) {
		this.regar = regar;
	}

	public int getIntervaloTiempoRiego() {
		return intervaloTiempoRiego;
	}

	public void setIntervaloTiempoRiego(int intervaloTiempoRiego) {
		this.intervaloTiempoRiego = intervaloTiempoRiego;
	}

	public int getMl() {
		return ml;
	}

	public void setMl(int ml) {
		this.ml = ml;
	}

	public int getLuz() {
		return luz;
	}

	public void setLuz(int luz) {
		this.luz = luz;
	}

	public int getIntervaloTiempoLuz() {
		return intervaloTiempoLuz;
	}

	public void setIntervaloTiempoLuz(int intervaloTiempoLuz) {
		this.intervaloTiempoLuz = intervaloTiempoLuz;
	}

	public int getMinLuz() {
		return minLuz;
	}

	public void setMinLuz(int minLuz) {
		this.minLuz = minLuz;
	}

	public int getVentilador() {
		return ventilador;
	}

	public void setVentilador(int ventilador) {
		this.ventilador = ventilador;
	}

	public int getIntervaloTiempoVentilador() {
		return intervaloTiempoVentilador;
	}

	public void setIntervaloTiempoVentilador(int intervaloTiempoVentilador) {
		this.intervaloTiempoVentilador = intervaloTiempoVentilador;
	}

	public int getMinVentilador() {
		return minVentilador;
	}

	public void setMinVentilador(int minVentilador) {
		this.minVentilador = minVentilador;
	}

	public void addUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}


}
