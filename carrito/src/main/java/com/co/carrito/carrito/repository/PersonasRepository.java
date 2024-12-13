package com.co.carrito.carrito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.co.carrito.carrito.models.Persona;

@Repository
public interface PersonasRepository extends JpaRepository<Persona, Integer> {
    List<Persona> findByNombreContainingIgnoreCase(String nombre);
    Persona findByUsuarioAndContrasena(String usuario, String contrasena);
    boolean existsByUsuario(String usuario);
    Persona findByUsuario(String usuario);
}
