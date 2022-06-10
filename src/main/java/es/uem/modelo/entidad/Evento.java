package es.uem.modelo.entidad;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;

public class Evento {
	private String titulo;
	private String color;
	private LocalDate mes1;/* Mes Inicial de creación de la planta yyyy-mm-dd */
	private HashSet<String> meses;

	/**
	 * Método que inicializa el hashset
	 */
	public void initHashSet() {
		meses = new HashSet<String>();
	}

	/**
	 * Dependiendo del intervalo de tiempo que se le pase calcula los
	 * días/meses/semanas que han pasado desde la creación dela planta (mes1) hasta
	 * el mes actual más los 4 siguientes meses. Una vez calculado el tiempo se
	 * guardan fechas en el hashset dependiendo del intervalo de tiempo si es 0 se
	 * guardan todos los dias que pasen, si es 1 se guarda un día a la semana y si
	 * es 2 se guarda un día al més
	 * 
	 * @param intervaloTiempo 0 una vez al dia, 1 una vez a la semana, 2 una vez al
	 *                        mes, 3 no regar
	 */
	public void actualizarMeses(int intervaloTiempo) {
		LocalDate actual = LocalDate.now();
		long months = 0;
		LocalDate m = mes1;
		if (intervaloTiempo == 0) {// dia
			months = ChronoUnit.DAYS.between(mes1, actual.plusMonths(4));
			for (int i = 0; i < months + 4; i++) {
				m = m.plusDays(1);
				String s = Integer.toString(m.getDayOfMonth()) + "/" + m.getMonthValue() + "/" + m.getYear();
				meses.add(s);
			}
		} else if (intervaloTiempo == 1) {// semana
			months = ChronoUnit.WEEKS.between(mes1, actual.plusMonths(4));
			for (int i = 0; i < months + 4; i++) {
				m = m.plusWeeks(1);
				String s = Integer.toString(m.getDayOfMonth()) + "/" + m.getMonthValue() + "/" + m.getYear();
				meses.add(s);
			}
		} else if (intervaloTiempo == 2) {// mes
			months = ChronoUnit.MONTHS.between(mes1, actual.plusMonths(4));
			for (int i = 0; i < months + 4; i++) {
				m = m.plusMonths(1);
				String s = Integer.toString(m.getDayOfMonth()) + "/" + m.getMonthValue() + "/" + m.getYear();
				meses.add(s);
			}

		} // 3 nada

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
