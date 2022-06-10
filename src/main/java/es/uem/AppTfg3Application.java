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
	}

	@Override
	public void run(String... args) throws Exception {
	}
	

}
