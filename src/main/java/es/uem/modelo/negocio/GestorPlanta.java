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

	/**
	 * Método que inicializa los valores de las plantas por defecto
	 */
	public void inicializarListaPlantasPorDefecto() {
		List<Tiposplanta> tipos = daoTiposplanta.findAll();
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

	public Planta buscarPlantaActual(int id) {
		int id_tipoplanta;
		Tiposplanta t;
		Planta p = daoPlanta.buscarPlantaActual(id);
		if (p != null) {
			id_tipoplanta = daoPlanta.buscarIdTipoPlantaDePlanta(p.getId());
			t = daoTiposplanta.findById(id_tipoplanta);

			p.setImg(t.getImg_url());
			return p;
		}
		return null;
	}

	public List<Planta> findAllByUsuario_id(int id) {
		int id_tipoplanta;

		List<Planta> p = daoPlanta.findAllByUsuario_id(id);
		for (Planta planta : p) {
			id_tipoplanta = daoPlanta.buscarIdTipoPlantaDePlanta(planta.getId());
			Tiposplanta t = daoTiposplanta.findById(id_tipoplanta);
			planta.setImg(t.getImg_url());
		}
		return p;
	}

	public Planta guardar(Planta p, int id_user) {
		finalizarPlantaActual(id_user);

		if (plantasPorDefecto == null) {
			System.out.println("inicializado");
			inicializarListaPlantasPorDefecto();
		}
		// miramos el tipo de planta que es
		if (p.getTiposplanta() != null && p.getTiposplanta().getId_tipoplanta() < 4) {// si es una por defecto
			Planta planta = plantasPorDefecto.get(p.getTiposplanta().getId_tipoplanta()-1);
			p.setMl(planta.getMl());
			p.setMinLuz(planta.getMinLuz());
			p.setMinVentilador(planta.getMinVentilador());

			p.setRegar(planta.getRegar());
			p.setLuz(planta.getLuz());
			p.setVentilador(planta.getVentilador());

			p.setIntervaloTiempoRiego(planta.getIntervaloTiempoRiego());
			p.setIntervaloTiempoLuz(planta.getIntervaloTiempoLuz());
			p.setIntervaloTiempoVentilador(planta.getIntervaloTiempoVentilador());

			// p.setTiposplanta(planta.getTiposplanta());

			p.setNombre(p.getNombre());
			p.setFechaIni(p.getFechaIni());
			p.setUsuario(p.getUsuario());
			System.out.println(planta);
		} else {
			p.setRegar(0);
			p.setLuz(0);
			p.setVentilador(0);
		}

		p.setUsuario(daoUsuario.findById(id_user));

		daoPlanta.save(p);

		return p;
	}

	public void actualizar(Planta p, int id_user) {
		p.setUsuario(daoUsuario.getById(id_user));

		daoPlanta.save(p);
	}

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

	public int deleteByIdUser(int id) {
		return daoPlanta.deleteByIdUser(id);
	}

	public Planta deleteById(int id) {
		daoPlanta.deleteById(id);
		return daoPlanta.findById(id);
	}
}
