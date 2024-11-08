package com.co.carrito.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.co.carrito.carrito.models.Comprar;

@Repository
public interface ComprarRepository extends JpaRepository<Comprar, Integer> {
    
}
