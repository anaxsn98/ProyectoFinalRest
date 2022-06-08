package es.uem.modelo.entidad;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import es.uem.usuario.modelo.Usuario;

import javax.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

//Anotaciones Spring
@Component
@Scope("prototype")
//Anotaciones de JPA
@Entity
@Table(name = "personalizarplanta")
public class Planta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_planta")
	private Integer id;
	@Column(name = "nombre_planta")
	private String nombre;

	@ManyToOne
	@JoinColumn(name = "id_tipoplanta", referencedColumnName = "id_tipoplanta")
	private Tiposplanta tiposplanta;
	private String fechaIni;
	private String fechaFin;
	@Column(name = "querer_regar")
	private Integer regar;// 1 sí 0 no
	@Column(name = "info_regar")
	private Integer intervaloTiempoRiego;// 0 una vez al dia, 1 una vez a la semana, 2 una vez al mes, 3 no regar
	@Column(name = "ml_regar")
	private Integer ml;
	@Column(name = "querer_luz")
	private Integer luz; // 1 sí 0 no
	@Column(name = "info_luz")
	private Integer intervaloTiempoLuz;// 0 una vez al dia, 1 una vez a la semana, 2 una vez al mes
	@Column(name = "min_luz")
	private Integer minLuz;
	@Column(name = "querer_ventilar")
	private Integer ventilador; // 1 sí 0 no
	@Column(name = "info_ventilar")
	private Integer intervaloTiempoVentilador;// 0 una vez al dia, 1 una vez a la semana, 2 una vez al mes
	@Column(name = "min_ventilar")
	private Integer minVentilador;
	@Column(name = "cantidad_amor")
	private String amor;

	@ManyToOne()
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
	private Usuario usuario;

	@Transient
	private List<Evento> eventos;

	private String img;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Planta() {
		super();
	}

	@Override
	public String toString() {
		return "Planta [id=" + id + ", nombre=" + nombre + ", tiposplanta=" + tiposplanta + ", fechaIni=" + fechaIni
				+ ", fechaFin=" + fechaFin + ", regar=" + regar + ", intervaloTiempoRiego=" + intervaloTiempoRiego
				+ ", ml=" + ml + ", luz=" + luz + ", intervaloTiempoLuz=" + intervaloTiempoLuz + ", minLuz=" + minLuz
				+ ", ventilador=" + ventilador + ", intervaloTiempoVentilador=" + intervaloTiempoVentilador
				+ ", minVentilador=" + minVentilador + ", amor=" + amor + ", usuario=" + usuario + ", eventos="
				+ eventos + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tiposplanta getTiposplanta() {
		return tiposplanta;
	}

	public void setTiposplanta(Tiposplanta tiposplanta) {
		this.tiposplanta = tiposplanta;
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

	public Integer getRegar() {
		return regar;
	}

	public void setRegar(Integer regar) {
		this.regar = regar;
	}

	public Integer getIntervaloTiempoRiego() {
		return intervaloTiempoRiego;
	}

	public void setIntervaloTiempoRiego(Integer intervaloTiempoRiego) {
		this.intervaloTiempoRiego = intervaloTiempoRiego;
	}

	public Integer getMl() {
		return ml;
	}

	public void setMl(Integer ml) {
		this.ml = ml;
	}

	public Integer getLuz() {
		return luz;
	}

	public void setLuz(Integer luz) {
		this.luz = luz;
	}

	public Integer getIntervaloTiempoLuz() {
		return intervaloTiempoLuz;
	}

	public void setIntervaloTiempoLuz(Integer intervaloTiempoLuz) {
		this.intervaloTiempoLuz = intervaloTiempoLuz;
	}

	public Integer getMinLuz() {
		return minLuz;
	}

	public void setMinLuz(Integer minLuz) {
		this.minLuz = minLuz;
	}

	public Integer getVentilador() {
		return ventilador;
	}

	public void setVentilador(Integer ventilador) {
		this.ventilador = ventilador;
	}

	public Integer getIntervaloTiempoVentilador() {
		return intervaloTiempoVentilador;
	}

	public void setIntervaloTiempoVentilador(Integer intervaloTiempoVentilador) {
		this.intervaloTiempoVentilador = intervaloTiempoVentilador;
	}

	public Integer getMinVentilador() {
		return minVentilador;
	}

	public void setMinVentilador(Integer minVentilador) {
		this.minVentilador = minVentilador;
	}

	public String getAmor() {
		return amor;
	}

	public void setAmor(String amor) {
		this.amor = amor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public void inicializarEventos() {
		Evento regar, luz, ventilar;
		String dia, mes, anio;
		String[] partes;
		this.eventos = new ArrayList<Evento>();
		regar = new Evento();
		luz = new Evento();
		ventilar = new Evento();
		partes = this.fechaIni.split("/");

		LocalDate d1 = LocalDate.parse(partes[2] + "-" + partes[1] + "-" + partes[0], DateTimeFormatter.ISO_LOCAL_DATE);

		regar.setTitulo("Regar");
		regar.setColor("#1e90ff");
		regar.setMes1(d1);
		regar.initHashSet();
		regar.actualizarMeses(this.intervaloTiempoRiego);

		luz.setTitulo("Luz");
		luz.setColor("#e3bc08");
		luz.setMes1(d1);
		luz.initHashSet();
		luz.actualizarMeses(this.intervaloTiempoLuz);

		ventilar.setTitulo("Ventilacion");
		ventilar.setColor("#b80900");
		ventilar.setMes1(d1);
		ventilar.initHashSet();
		ventilar.actualizarMeses(this.intervaloTiempoVentilador);

		this.eventos.add(ventilar);
		this.eventos.add(luz);
		this.eventos.add(regar);

	}

	public int generarNumProgressbar(int intervaloTiempo) {
		String[] partes;
		LocalDate mes1;
		LocalDate actual = LocalDate.now();
		partes = this.fechaIni.split("/");

		mes1 = LocalDate.parse(partes[2] + "-" + partes[1] + "-" + partes[0], DateTimeFormatter.ISO_LOCAL_DATE);

		Period period = Period.between(mes1, actual);
		double tiempo = 0;
		int porcentaje=0;long semanas = 0;

		if (intervaloTiempo == 0) {//dia
			//horas que quedan hasta el día siguiente
			LocalDateTime now = LocalDateTime.now();
			porcentaje = 24 - now.getHour();
			porcentaje = (int) Math.round((porcentaje * 100) / 24);
		} else if (intervaloTiempo == 1) {//semana
			//semanas que han pasado entre la semana de inicio de la planta y la semana actual
			semanas = ChronoUnit.WEEKS.between(mes1, actual);
			//obtener fecha de la siguiente semana
			LocalDate next = mes1.plusWeeks(semanas+1);
			//calcular los días que quedan desde la fecha actual hasta la siguiente fecha
			tiempo = ChronoUnit.DAYS.between(actual, next);
			//si 7 días está al 100% en i días estará a x%
			porcentaje =  (int) Math.round((tiempo * 100) / 7);
			System.out.println("SEMANA. "+mes1+" "+next+" "+tiempo);
		} else if (intervaloTiempo == 2) {//mes
			//meses que han pasado entre la semana de inicio de la planta y la semana actual
			semanas = ChronoUnit.MONTHS.between(mes1, actual);
			//obtener fecha del siguiente mese
			LocalDate next = mes1.plusMonths(semanas+1);
			//calcular los días que quedan desde la fecha actual hasta la siguiente fecha
			tiempo = ChronoUnit.DAYS.between(actual, next);
			porcentaje =  (int) Math.round((tiempo * 100) / actual.lengthOfMonth());
			System.out.println("SEMANA. "+mes1+" "+next+" "+tiempo);
		}//3 nada
		return porcentaje;
	}
}
