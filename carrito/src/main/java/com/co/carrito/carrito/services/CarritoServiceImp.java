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

        // Actualizar el stock del producto
        producto.setStock(producto.getStock() - cantidad);
        
        // Calcular el total de la compra
        double totalCompra = producto.getPrecio() * cantidad;
        compra.setTotal(totalCompra);

        compra.setFecha(new Date());
        // Persistir la compra y las entidades relacionadas
        comprarRepository.save(compra);
        
        // Añadir la compra a la lista de compras de la persona
        List<Comprar> comprasPersona = persona.getCompras() == null ? new ArrayList<>() : new ArrayList<>(persona.getCompras());
        comprasPersona.add(compra);
        persona.setCompras(comprasPersona);
        
        // Persistir la persona y el producto actualizados
        personaRepository.save(persona);
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

    

    
}
