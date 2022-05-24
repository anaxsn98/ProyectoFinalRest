package es.uem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;

import es.uem.modelo.entidad.Planta;
import es.uem.modelo.persistencia.DaoPlanta;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class AppTfg3Application  implements CommandLineRunner{

	
	public static void main(String[] args) {
		ApplicationContext context = 
				SpringApplication.run(AppTfg3Application.class, args);
		
		//pelicula = context.getBean("pelicula",Pelicula.class);
//		System.out.println("Main acaba");
	}

	@Override
	public void run(String... args) throws Exception {
//		//usuario = new Usuario();
//		usuario.setNombre("Ana");
//		usuario.setCorreo("lola@kdlf");
//		usuario.setPwd("99384");
//		usuario.setPlantas(new ArrayList<Planta>());
//		
//		Planta planta = new Planta();
//		planta.setNombre("Aloe");
//		planta.setFechaIni("10/9/2002");
//		planta.setAmor("10");
//		
////		planta.setUsuario(usuario);
////		planta2.setUsuario(usuario);
//		planta2.setNombre("Nub");
//		planta2.setFechaIni("10/9/2002");
//		planta2.setAmor("10");
//		planta2.setUsuarios(new ArrayList<Usuario>());
//		
//		
//		usuario.addPlanta(planta);
//		planta.addUsuario(usuario);
//		
//		usuario.addPlanta(planta2);
//		planta2.addUsuario(usuario);
//		
//		daoPlanta.save(planta);
//		daoUsuario.save(usuario);
//		
//		System.out.println(daoUsuario.findByNombreAndPwd("Ana","99384"));
//		System.out.println(daoUsuario.findByCorreoAndPwd("lola@kdlf","99384"));
//
//		Usuario user = daoUsuario.findByNombre("Ana");
//		
//		System.out.println("Listado las usuarios "+ user);
//		
//		daoUsuario.updatePwd("siso", usuario.getId());
//		
//		System.out.println("Listado las usuarios "+ user);
//		
//		List<Planta>lista=daoPlanta.findAllByUsuario_id(usuario.getId());
//		List<Planta>lista=daoPlanta.findAllByUsuario(usuario);
//		for (Planta integer : lista) {
//			System.out.println(integer);
//		}
	//	daoPlanta.deleteById(1);
	}
	

}
