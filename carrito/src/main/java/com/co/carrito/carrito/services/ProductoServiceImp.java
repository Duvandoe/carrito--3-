package com.co.carrito.carrito.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.co.carrito.carrito.models.Producto;
import com.co.carrito.carrito.repository.ProductoRepository;


@Service
public class ProductoServiceImp implements ProductoService {
	
	
	@Autowired
	private ProductoRepository productoRepository;


	@Override
	public List<Producto> listarProductos(){
		return productoRepository.findAll();
	}

	@Override
	public void agregarProducto(Producto producto, byte[] foto) {
			producto.setFoto(foto);
			productoRepository.save(producto);
	}

	@Override
	public void eliminarProducto(int idProducto) {
		productoRepository.deleteById(idProducto);
	}


	@Override
	public Producto obtenerProductoPorId(int id) {
        return productoRepository.findById(id).orElse(null);
    }

	@Override
	public String obtenerFotoBase64(int id) {
        Producto producto = obtenerProductoPorId(id);
        if (producto.getFoto() != null) {
            return Base64.getEncoder().encodeToString(producto.getFoto());
        }
        return null;
    }


	@Override
	public byte[] BuscarImagen(MultipartFile file) throws Exception {
    if (file == null || file.isEmpty()) {
        throw new Exception("Archivo no proporcionado o vacío.");
    }

    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    // Inicializa la imagen con un valor por defecto o maneja el caso donde el
    // nombre del archivo es inválido.
    if (fileName.contains("..")) {
        throw new Exception("Nombre de archivo inválido.");
    }

    // Intenta obtener la imagen como byte[]
    try {
        return file.getBytes(); // Retornar el arreglo de bytes de la imagen
    } catch (IOException e) {
        // Lanza una excepción si algo falla
        throw new Exception("Error al procesar el archivo.", e);
    }
	}
}
