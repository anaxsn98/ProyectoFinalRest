package es.uem.usuario.dto;

import javax.persistence.Column;

/**
 * Clase usada para la petición al servicio dar de alta al usuario
 */
public class AltaUsuarioDto {
	private String correo;
	private String nombre;
	private String pwd;
	private String pwd2;
	private String codigo_invernadero;

	public AltaUsuarioDto() {
		super();
	}

	public AltaUsuarioDto(String correo, String nombre, String pwd, String pwd2, String codigo_invernadero) {
		super();
		this.correo = correo;
		this.nombre = nombre;
		this.pwd = pwd;
		this.pwd2 = pwd2;
		this.codigo_invernadero = codigo_invernadero;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd2() {
		return pwd2;
	}

	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}

	public String getCodigo_invernadero() {
		return codigo_invernadero;
	}

	public void setCodigo_invernadero(String codigo_invernadero) {
		this.codigo_invernadero = codigo_invernadero;
	}

}