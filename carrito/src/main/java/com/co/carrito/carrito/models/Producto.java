package com.co.carrito.carrito.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto")
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProducto;
	
	@Column(name = "Nombre_Producto")
	private String Nombres;
	
	@Column(name = "foto")
    private String Foto;
	
	@Column(name = "Descripcion")
	private String Descripcion;
	
	@Column(name = "Precio")
	private Double Precio;
	
	@Column(name = "Stock")
	private int Stock;

	@ManyToMany(mappedBy = "productos", cascade = CascadeType.ALL) // Cascade all
	private List<Comprar> compras = new ArrayList<>();

	public Producto(int idProducto, String Nombres, String Foto, String Descripcion, Double Precio, int Stock) {
		this.idProducto = idProducto;
		this.Nombres = Nombres;
		this.Foto = Foto;
		this.Descripcion = Descripcion;
		this.Precio = Precio;
		this.Stock = Stock;
		
	}

	public Producto(String Nombres, String Foto, String Descripcion, Double Precio, int Stock) {
		this.Nombres = Nombres;
		this.Foto = Foto;
		this.Descripcion = Descripcion;
		this.Precio = Precio;
		this.Stock = Stock;
	}


	public Producto() {
	}


	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombres() {
		return Nombres;
	}

	public void setNombres(String Nombres) {
		this.Nombres = Nombres;
	}

	public String getFoto() {
		return Foto;
	}

	public void setFoto(String Foto) {
		this.Foto = Foto;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String Descripcion) {
		this.Descripcion = Descripcion;
	}

	public Double getPrecio() {
		return Precio;
	}

	public void setPrecio(Double Precio) {
		this.Precio = Precio;
	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int Stock) {
		this.Stock = Stock;
	}



	public List<Comprar> getCompras() {
		return this.compras;
	}

	public void setCompras(List<Comprar> compras) {
		this.compras = compras;
	}

}
