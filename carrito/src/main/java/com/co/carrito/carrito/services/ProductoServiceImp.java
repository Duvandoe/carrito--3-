package com.co.carrito.carrito.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.co.carrito.carrito.models.Producto;
import com.co.carrito.carrito.repository.ProductoRepository;



@Service
public class ProductoServiceImp implements ProductoService {
    
	
	@Autowired
	private final ProductoRepository productoRepository;
    @Autowired
    private FotoService fotoService;

    @Autowired
    public ProductoServiceImp(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }



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
    
    // Luego eliminar el producto
    productoRepository.deleteById(idProducto);
}


	@Override
	public Producto obtenerProductoPorId(int idProducto) {
        Optional<Producto> producto = productoRepository.findById(idProducto);
        return producto.orElse(null);
    }

	@Override
    public Producto ActualizarProducto(Producto producto, MultipartFile file) {
        // Busca si existe la persona con ese ID
        Optional<Producto> productoOpt = productoRepository.findById(producto.getIdProducto());
        
        if (productoOpt.isPresent()) {
            Producto productoExistente = productoOpt.get();
            // Actualiza solo los campos necesarios
            productoExistente.setNombres(producto.getNombres());
            productoExistente.setPrecio(producto.getPrecio());
            productoExistente.setStock(producto.getStock());
            // Si el archivo no es nulo, actualiza la foto
            if (file != null && !file.isEmpty()) {
            // Asumiendo que tienes un servicio que guarda la foto y devuelve la URL o el nombre del archivo
                String foto = fotoService.unaFoto(file);
                productoExistente.setFoto(foto);
            }
            // Guarda los cambios
            return productoRepository.save(productoExistente);
        }
        return null; // Maneja si la persona no se encuentra
    }

    @Override
    public long obtenerCantidadDeProductos() {
        return productoRepository.count();
    }

}
