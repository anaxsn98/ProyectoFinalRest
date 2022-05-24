package es.uem.usuario.dto;

public class GetUsuarioDto {
	private int id;
	private String correo;
	private String nombre;
	private String codigo_invernadero;

	public GetUsuarioDto(int id, String correo, String nombre, String codigo_invernadero) {
		super();
		this.id = id;
		this.correo = correo;
		this.nombre = nombre;
		this.codigo_invernadero = codigo_invernadero;
	}

	public GetUsuarioDto() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCodigo_invernadero() {
		return codigo_invernadero;
	}

	public void setCodigo_invernadero(String codigo_invernadero) {
		this.codigo_invernadero = codigo_invernadero;
	}

}