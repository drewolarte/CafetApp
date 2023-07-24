package com.cafetapp.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Estudiante {

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
	@Column(length = 20)
	private String fechaNacimiento;

	@Column(length = 10)
	private double saldo;

	@Column(length = 10)
	private double topeDiario;

	private String restricciones;

	@ManyToOne
	private Colegio colegio;

	@ManyToOne
	private Acudiente acudiente;

	public Estudiante(@NotEmpty String id, @NotEmpty String nombres, @NotEmpty String apellidos,
			@NotEmpty String fechaNacimiento, double saldo, String restricciones, Colegio colegio, Acudiente acudiente,
			double topeDiario) {
		super();
		this.id = id;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.saldo = 0;
		this.restricciones = "Ninguna";
		this.colegio = colegio;
		this.acudiente = acudiente;
		this.topeDiario = 0;
	}

	public Estudiante() {
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

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getRestricciones() {
		return restricciones;
	}

	public void setRestricciones(String restricciones) {
		this.restricciones = restricciones;
	}

	public Colegio getColegio() {
		return colegio;
	}

	public void setColegio(Colegio colegio) {
		this.colegio = colegio;
	}

	public Acudiente getAcudiente() {
		return acudiente;
	}

	public void setAcudiente(Acudiente acudiente) {
		this.acudiente = acudiente;
	}

	public double getTopeDiario() {
		return topeDiario;
	}

	public void setTopeDiario(double topeDiario) {
		this.topeDiario = topeDiario;
	}

}
