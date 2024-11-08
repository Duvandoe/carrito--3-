package com.co.carrito.carrito.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.co.carrito.carrito.models.Producto;

public interface ProductoService {

	public List<Producto> listarProductos();

	public void agregarProducto(Producto producto, byte[] foto);
        
	public void eliminarProducto(int idProducto);
	
	public Producto obtenerProductoPorId(int id);

	public byte[] BuscarImagen(MultipartFile file) throws Exception;

	public String obtenerFotoBase64(int id);  
}
