package com.cafetapp.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class HistorialCompras {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String productos;

	@Column(length = 10)
	private double valorPago;

	@NotEmpty
	@Column(length = 20)
	private String fechaCompra;

	@ManyToOne
	private Colegio colegio;

	@ManyToOne
	private Estudiante estudiante;

	public HistorialCompras(Long id, @NotEmpty String productos, double valorPago, @NotEmpty String fechaCompra,
			Colegio colegio, Estudiante estudiante) {
		super();
		this.id = id;
		this.productos = productos;
		this.valorPago = valorPago;
		this.fechaCompra = fechaCompra;
		this.colegio = colegio;
		this.estudiante = estudiante;
	}

	public HistorialCompras() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductos() {
		return productos;
	}

	public void setProductos(String productos) {
		this.productos = productos;
	}

	public double getValorPago() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	public String getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(String fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Colegio getColegio() {
		return colegio;
	}

	public void setColegio(Colegio colegio) {
		this.colegio = colegio;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

}
