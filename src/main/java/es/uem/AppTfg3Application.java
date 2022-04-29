package es.uem;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.uem.modelo.entidad.Planta;
import es.uem.modelo.entidad.Usuario;
import es.uem.modelo.persistencia.DaoPlanta;
import es.uem.modelo.persistencia.DaoUsuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AppTfg3Application  implements CommandLineRunner{
	@Autowired
	private Usuario usuario;
	@Autowired
	private Planta planta;
	@Autowired
	private DaoPlanta daoPlanta;
	@Autowired
	private DaoUsuario daoUsuario;
	
	public static void main(String[] args) {
		ApplicationContext context = 
				SpringApplication.run(AppTfg3Application.class, args);
		
		//pelicula = context.getBean("pelicula",Pelicula.class);
		System.out.println("Main acaba");
	}

	@Override
	public void run(String... args) throws Exception {
		//usuario = new Usuario();
		usuario.setNombre("Ana");
		usuario.setCorreo("lola@kdlf");
		usuario.setPwd("99384");
		usuario.setPlantas(new ArrayList<Planta>());
		
		planta.setNombre("Aloe");
		planta.setUsuarios(new ArrayList<Usuario>());
		
		
		usuario.addPlanta(planta);
		planta.addUsuario(usuario);
		
		daoPlanta.save(planta);
		//daoUsuario.save(usuario);
		

		Usuario user = daoUsuario.findByNombre("Ana");
		
		System.out.println("Listado las usuarios "+ user);
		
	//	daoPlanta.deleteById(1);
	}
	

}
