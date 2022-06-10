package es.uem.seguridad.jwt.modelo;

import java.io.Serializable;

/**
 * Clase usada para la respuesta a la peticion al servicio de login 
 *
 */
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String token;
	private final String id_usario;
	private final String nombre;
	
	public JwtResponse(String token, String id_usario, String nombre) {
		super();
		this.token = token;
		this.id_usario = id_usario;
		this.nombre = nombre;
	}

	public String getToken() {
		return token;
	}

	public String getId_usario() {
		return id_usario;
	}

	public String getNombre() {
		return nombre;
	}


	
}