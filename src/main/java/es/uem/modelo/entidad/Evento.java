package es.uem.modelo.entidad;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

public class Evento {
	private String titulo;
	private String color;
	private LocalDate mes1;/* Mes Inicial de creación de la planta */
	private HashSet<String> meses;

//	public static void main(String[] args) {
//		LocalDate d1 = LocalDate.parse("2022-01-01", DateTimeFormatter.ISO_LOCAL_DATE);
//		Evento e = new Evento();
//		e.setMes1(d1);
//		e.initHashSet();
//		e.actualizarMeses(1);
//		e.imprimirMeses();
//	}

	public void initHashSet() {
		meses = new HashSet<String>();
	}

	/**
	 * Calcula la diferencia entre la fecha de creación y la actual. Guarda las
	 * fechas de cada mes desde la fecha de creación hasta la actual más los 4 meses
	 * siguientes.
	 * 
	 * @param intervaloTiempo 0 una vez al dia, 1 una vez a la semana, 2 una vez al
	 *                        mes, 3 no regar
	 */
	public void actualizarMeses(int intervaloTiempo) {
		LocalDate actual = LocalDate.now();
		Period period = Period.between(mes1, actual);
		long months = 0;

		if (intervaloTiempo == 0) {//dia
			months = ChronoUnit.DAYS.between(mes1, actual.plusMonths(4));
			for (int i = 0; i < months + 4; i++) {
				LocalDate m = mes1.plusDays(i);
				String s = Integer.toString(m.getDayOfMonth()) + "/" + m.getMonthValue() + "/" + m.getYear();
				meses.add(s);
			}
		} else if (intervaloTiempo == 1) {//semana
			months = ChronoUnit.WEEKS.between(mes1, actual.plusMonths(4));
			for (int i = 0; i < months + 4; i++) {
				LocalDate m = mes1.plusWeeks(i);
				String s = Integer.toString(m.getDayOfMonth()) + "/" + m.getMonthValue() + "/" + m.getYear();
				meses.add(s);
			}
		} else if (intervaloTiempo == 2) {//mes
			months = Math.abs(period.getMonths());
			for (int i = 0; i < months + 4; i++) {
				LocalDate m = mes1.plusMonths(i);
				String s = Integer.toString(m.getDayOfMonth()) + "/" + m.getMonthValue() + "/" + m.getYear();
				meses.add(s);
			}

		}//3 nada

	}

	public void imprimirMeses() {
		for (String s : meses) {
			System.out.println(s);
		}
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public LocalDate getMes1() {
		return mes1;
	}

	public void setMes1(LocalDate mes1) {
		this.mes1 = mes1;
	}

	public HashSet<String> getMeses() {
		return meses;
	}

	public void setMeses(HashSet<String> meses) {
		this.meses = meses;
	}

}
