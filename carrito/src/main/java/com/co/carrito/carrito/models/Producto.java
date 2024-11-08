package com.co.carrito.carrito.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "producto")
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProducto;
	
	@Column(name = "Nombre_Producto")
	private String Nombres;
	
	@Lob
    private byte[] Foto;
	
	@Column(name = "Descripcion")
	private String Descripcion;
	
	@Column(name = "Precio")
	private Double Precio;
	
	@Column(name = "Stock")
	private int Stock;

	@Transient // Este campo no se almacena en la base de datos
    private String fotoBase64;

	@ManyToMany(mappedBy = "productos")
	private List<Comprar> compras = new ArrayList<>();

	public Producto(int idProducto, String Nombres, byte[] Foto, String Descripcion, Double Precio, int Stock, String fotoBase64) {
		this.idProducto = idProducto;
		this.Nombres = Nombres;
		this.Foto = Foto;
		this.Descripcion = Descripcion;
		this.Precio = Precio;
		this.Stock = Stock;
		this.fotoBase64 = fotoBase64;
	}

	public Producto(String Nombres, byte[] Foto, String Descripcion, Double Precio, int Stock, String fotoBase64) {
		this.Nombres = Nombres;
		this.Foto = Foto;
		this.Descripcion = Descripcion;
		this.Precio = Precio;
		this.Stock = Stock;
		this.fotoBase64 = fotoBase64;
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

	public byte[] getFoto() {
		return Foto;
	}

	public void setFoto(byte[] Foto) {
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


	public String getFotoBase64() {
		return fotoBase64;
	}
	
	public void setFotoBase64(String fotoBase64) {
		this.fotoBase64 = fotoBase64;
	}

	public List<Comprar> getCompras() {
		return this.compras;
	}

	public void setCompras(List<Comprar> compras) {
		this.compras = compras;
	}

}
