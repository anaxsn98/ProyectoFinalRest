package es.uem.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
@Configuration
@EnableWebSecurity
//va a permitir trabajar sobre un determinado controlador (añadiendo una anotacion) 
//para indicar quien puede acceder si es necesario estar autenticado o tener algun rol
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private  JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	/**
	 * Va a exponer nuestro mecanismos de autenticación como un bean para poder usarlo en el filtro
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	
	/**
	 * Configurar el control de acceso
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.csrf()
				.disable()
			.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint) // Lo modificamos más adelante
			.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)//sin estado para no utilizar sesiones
			.and()
			.authorizeRequests()//autorizar peticiones/identificar roles de acceso
				.antMatchers(HttpMethod.GET, "/producto/**", "/lote/**").hasRole("USER")
				.antMatchers(HttpMethod.POST, "/producto/**", "/lote/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/producto/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/producto/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/pedido/**").hasAnyRole("USER","ADMIN")
				.anyRequest().authenticated();

		// Añadimos el filtro (lo hacemos más adelante).
		http.addFilterBefore(null, UsernamePasswordAuthenticationFilter.class);
		
		
	}
}
