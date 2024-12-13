package com.co.carrito.carrito.services;

import com.co.carrito.carrito.models.Rol;
import com.co.carrito.carrito.models.RolEnum;

public interface RolService {

    public Rol obtenerRolPorNombre(RolEnum nombrerol);  // Método para obtener rol por nombre
}
