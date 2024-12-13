package com.co.carrito.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.co.carrito.carrito.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
    @Override
    @Query("SELECT COUNT(p) FROM Producto p")
    long count();
}
