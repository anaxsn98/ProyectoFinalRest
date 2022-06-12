package es.uem.modelo.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uem.modelo.entidad.Planta;
import es.uem.modelo.entidad.Tiposplanta;
import es.uem.modelo.persistencia.DaoPlanta;
import es.uem.modelo.persistencia.DaoTiposplanta;
import es.uem.usuario.persistencia.DaoUsuario;

@Service
public class GestorPlanta {
	@Autowired
	private DaoPlanta daoPlanta;
	@Autowired
	private DaoUsuario daoUsuario;
	@Autowired
	private DaoTiposplanta daoTiposplanta;
	private List<Planta> plantasPorDefecto;// 0 Hierba buena, 1 Perejil, 2 Cherrys
	List<Tiposplanta> tipos;

	/**
	 * Método que inicializa la lista de tiposplanta con los valores por defecto
	 */
	public void inicializarListaPlantasPorDefecto() {
		tipos = daoTiposplanta.findAll();
		// 1 Hierba buena, 2 Perejil, 3 Cherrys, 4 Personalizada
		plantasPorDefecto = new ArrayList<>();
		Planta p = new Planta();
		Planta p2 = new Planta();
		Planta p3 = new Planta();

		p.setMl(1);
		p.setMinLuz(10);
		p.setMinVentilador(10);

		p.setRegar(1);
		p.setLuz(1);
		p.setVentilador(1);

		p.setIntervaloTiempoRiego(2);
		p.setIntervaloTiempoLuz(2);
		p.setIntervaloTiempoVentilador(1);

		p.setTiposplanta(tipos.get(0));

		p2.setMl(1);
		p2.setMinLuz(10);
		p2.setMinVentilador(10);

		p2.setRegar(1);
		p2.setLuz(1);
		p2.setVentilador(1);

		p2.setIntervaloTiempoRiego(2);
		p2.setIntervaloTiempoLuz(2);
		p2.setIntervaloTiempoVentilador(1);

		p2.setTiposplanta(tipos.get(1));

		p3.setMl(1);
		p3.setMinLuz(10);
		p3.setMinVentilador(10);

		p3.setRegar(1);
		p3.setLuz(1);
		p3.setVentilador(1);

		p3.setIntervaloTiempoRiego(2);
		p3.setIntervaloTiempoLuz(2);
		p3.setIntervaloTiempoVentilador(1);

		p3.setTiposplanta(tipos.get(2));

		plantasPorDefecto.add(p);// 1 hierba buena
		plantasPorDefecto.add(p2);// 2 Perejil
		plantasPorDefecto.add(p3);// 3 Cherrys
	}

	/**
	 * Búsqueda de la planta actual por id de usuario
	 * 
	 * @param id id de usuario
	 * @return la planta actual o null si no se ha encontrado
	 */
	public Planta buscarPlantaActual(int id) {
		Planta p = daoPlanta.buscarPlantaActual(id);
		return p;
	}

	/**
	 * Devuelve una lista de porcentajes para cada evento de la planta actual
	 * 
	 * @param id_user id del usuario
	 * @return lista de porcentajes o null si el usuario no tiene una planta actual
	 */
	public List<Integer> progressbar(int id_user) {
		List<Integer> listaNumProgressBar = new ArrayList<Integer>();
		Planta p = buscarPlantaActual(id_user);
		if (p != null) {
			listaNumProgressBar.add(p.generarNumProgressbar(p.getIntervaloTiempoLuz()));
			listaNumProgressBar.add(p.generarNumProgressbar(p.getIntervaloTiempoRiego()));
			listaNumProgressBar.add(p.generarNumProgressbar(p.getIntervaloTiempoVentilador()));
		}
		return listaNumProgressBar;
	}

	/**
	 * Buscar todas las plantas del usuario
	 * 
	 * @param id id de usuario
	 * @return lista de plantas o null si el ususairo no tiene ninguna planta
	 *         asociada
	 */
	public List<Planta> findAllByUsuario_id(int id) {
		List<Planta> p = daoPlanta.findAllByUsuario_id(id);
		return p;
	}

	/**
	 * Guarda en la base de datos una planta asociada a un usuario. Si es una planta
	 * por defecto se le asignan los datos por defecto. Los campos que interactuan
	 * con el invernadero se ponen a 0 para que no riegue o ventile o de luz En caso
	 * de que el usuario no haya asignado una fotografía a la planta se le asigna el
	 * del tipo de planta correspondiente
	 * La planta actual no puede tener fecha final
	 * En caso de que haya una planta actual se da de baja y se pone la nueva
	 * 
	 * @param p planta que se quiere dar de alta
	 * @param id_user id del usuario al que se quiere asignar la panta
	 * @return la planta que se ha dado de alta en la base de datos
	 */
	public Planta guardar(Planta p, int id_user) {
		finalizarPlantaActual(id_user);
		
		if (plantasPorDefecto == null) {
			System.out.println("inicializado");
			inicializarListaPlantasPorDefecto();
		}
		// miramos el tipo de planta que es
		if (p.getTiposplanta() != null && p.getTiposplanta().getId_tipoplanta() < 4) {// si es una por defecto
			Planta planta = plantasPorDefecto.get(p.getTiposplanta().getId_tipoplanta() - 1);
			p.setMl(planta.getMl());
			p.setMinLuz(planta.getMinLuz());
			p.setMinVentilador(planta.getMinVentilador());

			p.setRegar(planta.getRegar());
			p.setLuz(planta.getLuz());
			p.setVentilador(planta.getVentilador());

			p.setIntervaloTiempoRiego(planta.getIntervaloTiempoRiego());
			p.setIntervaloTiempoLuz(planta.getIntervaloTiempoLuz());
			p.setIntervaloTiempoVentilador(planta.getIntervaloTiempoVentilador());

		}

		// campos que interactuan con el invernadero 1 sí 0 no
		p.setRegar(0);
		p.setLuz(0);
		p.setVentilador(0);

		// cuando se da de alta una planta no puede haber fecha final
		p.setFechaFin(null);

		if (p.getImg() == null || (p.getImg() != null && p.getImg().equals(""))) {
			Tiposplanta t = daoTiposplanta.findById(p.getTiposplanta().getId_tipoplanta());
			p.setImg(t.getImg_url());
		}

		p.setUsuario(daoUsuario.findById(id_user));

		daoPlanta.save(p);
		return p;
	}

	/**
	 * Actualizar los datos de la planta
	 * @param p planta con los nuevos datos
	 * @param id_user id del usuario al que pertenece la planta
	 */
	public void actualizar(Planta p, int id_user) {
		p.setUsuario(daoUsuario.getById(id_user));
		daoPlanta.save(p);
	}

	/**
	 * Finalizar la planta actual, es decir, poner fecha final a la planta actual. 
	 * @param id_user id del usuairo
	 * @return la planta actualizada o null si el usuario no tiene planta actual
	 */
	public Planta finalizarPlantaActual(int id_user) {
		String[] partes;
		Planta p = buscarPlantaActual(id_user);
		if (p != null) {
			partes = LocalDate.now().toString().split("-");
			p.setFechaFin(partes[2] + "/" + partes[1] + "/" + partes[0]);
			return daoPlanta.save(p);
		}
		return null;
	}

	/**
	 * Eliminar todas las plantas del usuario
	 * @param id id del usuario
	 * @return 0 si no se ha encontrado 1 en caso contrario
	 */
	public int deleteByIdUser(int id) {
		return daoPlanta.deleteByIdUser(id);
	}

	/**
	 * Eliminar planta por id
	 * @param id id de la planta
	 * @return planta que ha sido eliminada o null
	 */
	public Planta deleteById(int id) {
		daoPlanta.deleteById(id);
		return daoPlanta.findById(id);
	}
}
