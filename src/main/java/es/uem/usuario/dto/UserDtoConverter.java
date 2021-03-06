package es.uem.usuario.dto;

import org.springframework.stereotype.Component;

import es.uem.usuario.modelo.Usuario;

@Component
public class UserDtoConverter {

	public GetUsuarioDto convertUserEntityToGetUserDto(Usuario usuario) {
		return new GetUsuarioDto(usuario.getId(), usuario.getCorreo(), usuario.getNombre(),
				usuario.getCodigo_invernadero());
	}

}