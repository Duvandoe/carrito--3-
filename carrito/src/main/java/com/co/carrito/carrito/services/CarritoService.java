package com.co.carrito.carrito.services;

import java.util.List;

import com.co.carrito.carrito.models.Comprar;
import com.co.carrito.carrito.models.Persona;
import com.co.carrito.carrito.models.Producto;

public interface CarritoService {

    public void crearCompra(Persona persona, Producto producto, int cantidad);

    public void borrarCompra(int id);

    public Comprar encontrarCompra(int id);

    public List<Comprar> listarCompras();
    
    public List<Comprar> obtenerComprasPorPersona(Persona persona);

    public List<Comprar> obtenerComprasPorProducto(Producto producto);

    public void actualizarCompra(Comprar compra);
}
