package es.uem.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

	/**
	 * Bean que va a permitir que Spring Security habilite explícitamente la
	 * compatibilidad con CORS en el nivel de Spring. Sino Spring Security puede
	 * bloquear las solicitudes habilitadas para CORS antes de llegar a Spring MVC.
	 * 
	 * @return nueva configuración
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*")
						.allowedOrigins("*");
			}
		};
	}
}