package com.cafetapp.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Acudiente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(length = 20)
	private String cedula;

	@NotEmpty
	@Column(length = 70)
	private String nombres;

	@NotEmpty
	@Column(length = 70)
	private String apellidos;

	@NotEmpty
	@Column(length = 50)
	private String contrasena;

	@ManyToOne
	private Colegio colegio;

	public Acudiente(Long id, @NotEmpty String cedula, @NotEmpty String nombres, @NotEmpty String apellidos,
			@NotEmpty String contrasena, Colegio colegio) {
		super();
		this.id = id;
		this.cedula = cedula;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.contrasena = contrasena;
		this.colegio = colegio;
	}

	public Acudiente() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
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

	public Colegio getColegio() {
		return colegio;
	}

	public void setColegio(Colegio colegio) {
		this.colegio = colegio;
	}

}
