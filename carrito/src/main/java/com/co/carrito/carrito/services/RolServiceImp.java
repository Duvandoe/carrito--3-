package com.co.carrito.carrito.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.carrito.carrito.models.Rol;
import com.co.carrito.carrito.models.RolEnum;
import com.co.carrito.carrito.repository.RolRepository;

@Service
public class RolServiceImp implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Rol obtenerRolPorNombre(RolEnum nombrerol) {
        return rolRepository.findByNombrerol(nombrerol);  // Método que busca el rol por nombre
    }
}
