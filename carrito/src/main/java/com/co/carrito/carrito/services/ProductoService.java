package com.co.carrito.carrito.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.co.carrito.carrito.models.Producto;

public interface ProductoService {

	public List<Producto> listarProductos();
        
	public void eliminarProducto(int idProducto);
	
	public Producto obtenerProductoPorId(int id);  

	public void agregarProducto(Producto producto, MultipartFile file);

	public Producto ActualizarProducto(Producto producto, MultipartFile file);
}
