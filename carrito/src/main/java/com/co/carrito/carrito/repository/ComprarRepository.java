package com.co.carrito.carrito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.co.carrito.carrito.models.Comprar;
import com.co.carrito.carrito.models.Persona;
import com.co.carrito.carrito.models.Producto;


@Repository
public interface ComprarRepository extends JpaRepository<Comprar, Integer> {
    List<Comprar> findByPersona(Persona persona);
    List<Comprar> findByProductos(Producto producto);
}
