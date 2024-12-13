package com.co.carrito.carrito.services;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.co.carrito.carrito.models.Comprar;
import com.co.carrito.carrito.models.Persona;
import com.co.carrito.carrito.models.Producto;
import com.co.carrito.carrito.repository.ComprarRepository;
import com.co.carrito.carrito.repository.PersonasRepository;
import com.co.carrito.carrito.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class CarritoServiceImp implements CarritoService {

    private final ComprarRepository comprarRepository;

    private final PersonasRepository personaRepository;

    private final ProductoRepository productoRepository;


    public CarritoServiceImp(ComprarRepository comprarRepository, PersonasRepository personaRepository, ProductoRepository productoRepository) {
        this.comprarRepository = comprarRepository;
        this.personaRepository = personaRepository;
        this.productoRepository = productoRepository;
    }


    @Transactional
@Override
public void crearCompra(Persona persona, Producto producto, int cantidad) {
    // Verificar si hay suficiente stock
    if (producto.getStock() < cantidad) {
        System.out.println("No hay stock por el momento");
        return; // Salir del método si no hay stock suficiente
    }

    // Crear una nueva compra
    Comprar compra = new Comprar();
    compra.setCantidad(cantidad);
    compra.setPersona(persona);
    compra.setProductos(new ArrayList<>()); // Inicializar la lista de productos

    // Añadir el producto a la compra
    compra.getProductos().add(producto); // Agregar el producto a la compra

    // Calcular el total de la compra
    double totalCompra = producto.getPrecio() * cantidad;
    compra.setTotal(totalCompra);

    compra.setFecha(new Date());
    
    // Persistir la compra
    comprarRepository.save(compra);

    // Añadir la compra a la lista de compras de la persona
    persona.getCompras().add(compra);
    
    // Persistir la persona y el producto actualizados
    personaRepository.save(persona);

    // Actualizar el stock del producto
    producto.setStock(producto.getStock() - cantidad);
    productoRepository.save(producto);

    System.out.println("Compra exitosa");
}

    @Transactional
    @Override
    public void borrarCompra(int id) {
        if (comprarRepository.existsById(id)) {
            comprarRepository.deleteById(id);
        } else {
            throw new RuntimeException("Compra no encontrada con ID: " + id);
        }
    }

    @Override
    public Comprar encontrarCompra(int id) {
        
        return comprarRepository.findById(id).orElse(null);
    }


    @Override
    public List<Comprar> listarCompras() {
        return comprarRepository.findAll();
    }

   @Override
   public List<Comprar> obtenerComprasPorPersona(Persona persona) {
    return comprarRepository.findByPersona(persona);
    }

    @Override
    public List<Comprar> obtenerComprasPorProducto(Producto producto) {
        // Método para obtener compras de un producto
        return comprarRepository.findByProductos(producto);
    }

    @Override
    public void actualizarCompra(Comprar compra) {
        // Actualizar la compra existente en la base de datos
        comprarRepository.save(compra);
    }
}