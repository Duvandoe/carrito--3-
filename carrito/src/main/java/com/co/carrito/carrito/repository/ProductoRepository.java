package com.co.carrito.carrito.repository;

import org.springframework.stereotype.Repository;

import com.co.carrito.carrito.models.Producto;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{

}
