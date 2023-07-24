package com.cafetapp.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class SuperAdministrador {

	@Id
	@NotEmpty
	@Column(length = 20)
	private String id;

	@NotEmpty
	@Column(length = 70)
	private String nombres;

	@NotEmpty
	@Column(length = 70)
	private String apellidos;

	@NotEmpty
	@Column(length = 50)
	private String contrasena;

	public SuperAdministrador(@NotEmpty String id, @NotEmpty String nombres, @NotEmpty String apellidos,
			@NotEmpty String contrasena) {
		super();
		this.id = id;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.contrasena = contrasena;
	}

	public SuperAdministrador() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
