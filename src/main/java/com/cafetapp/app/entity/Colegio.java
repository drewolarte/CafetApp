package com.cafetapp.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Colegio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(length = 70)
	private String nombre;

	@NotEmpty
	@Column(length = 70)
	private String direccion;

	@NotEmpty
	@Column(length = 20)
	private String telefono;

	@Column(length = 10)
	private double minRecarga;

	@Column(length = 10)
	private double maxRecarga;

	@Column(length = 10)
	private double minCompra;

	@Column(length = 10)
	private double maxCompra;

	public Colegio(Long id, @NotEmpty String nombre, @NotEmpty String direccion, @NotEmpty String telefono,
			double minRecarga, double maxRecarga, double minCompra, double maxCompra) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.minRecarga = 0;
		this.maxRecarga = 0;
		this.minCompra = 0;
		this.maxCompra = 0;
	}

	public Colegio() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public double getMinRecarga() {
		return minRecarga;
	}

	public void setMinRecarga(double minRecarga) {
		this.minRecarga = minRecarga;
	}

	public double getMaxRecarga() {
		return maxRecarga;
	}

	public void setMaxRecarga(double maxRecarga) {
		this.maxRecarga = maxRecarga;
	}

	public double getMinCompra() {
		return minCompra;
	}

	public void setMinCompra(double minCompra) {
		this.minCompra = minCompra;
	}

	public double getMaxCompra() {
		return maxCompra;
	}

	public void setMaxCompra(double maxCompra) {
		this.maxCompra = maxCompra;
	}
}
