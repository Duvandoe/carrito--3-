package com.co.carrito.carrito.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.co.carrito.carrito.models.Rol;
import com.co.carrito.carrito.models.RolEnum;

public interface RolRepository extends JpaRepository<Rol, Integer> {
// Buscar rol por nombre usando el RolEnum
 // Cambiar el parámetro para que reciba un RolEnum
    Rol findByNombrerol(RolEnum nombrerol);
}