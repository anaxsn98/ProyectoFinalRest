package es.uem.seguridad.jwt.modelo;

import es.uem.usuario.dto.GetUsuarioDto;

public class JwtUserResponse extends GetUsuarioDto {
	
	private String token;
	
	public JwtUserResponse(int id, String correo, String nombre, String codigo_invernadero, String token) {
		super(id, correo, nombre, codigo_invernadero);
		this.token = token;
	}

	public JwtUserResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtUserResponse(int id, String correo, String nombre, String codigo_invernadero) {
		super(id, correo, nombre, codigo_invernadero);
		// TODO Auto-generated constructor stub
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	

}