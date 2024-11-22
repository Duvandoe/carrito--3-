package com.co.carrito.carrito.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.co.carrito.carrito.models.Producto;
import com.co.carrito.carrito.repository.ProductoRepository;


@Service
public class ProductoServiceImp implements ProductoService {
	
	
	@Autowired
	private ProductoRepository productoRepository;
    @Autowired
    private FotoService fotoService;


	@Override
	public List<Producto> listarProductos(){
		return productoRepository.findAll();
	}

	@Override
	public void agregarProducto(Producto producto, MultipartFile file) {
        String foto = fotoService.unaFoto(file);
        if (foto != null) {
            producto.setFoto(foto);
        }
    
        productoRepository.save(producto);
        System.out.println("Producto guardado: " + producto);

	}

	@Override
	public void eliminarProducto(int idProducto) {
		productoRepository.deleteById(idProducto);
	}


	@Override
	public Producto obtenerProductoPorId(int id) {
        return productoRepository.findById(id).orElse(null);
    }

	//@Override
	//public String obtenerFotoBase64(int id) {
    //    Producto producto = obtenerProductoPorId(id);
    //    if (producto.getFoto() != null) {
    //        return Base64.getEncoder().encodeToString(producto.getFoto());
    //    }
    //    return null;
   // }

}
