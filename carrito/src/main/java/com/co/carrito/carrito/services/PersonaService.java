package com.co.carrito.carrito.services;

import java.util.List;

import com.co.carrito.carrito.models.Persona;

public interface PersonaService {

    public List<Persona> listarPersonas();

    void crearPersona(Persona persona);

    public void borrarPersona(int id);
    
    public List<Persona> buscarPorNombre(String nombre);

    // Método para obtener una persona por ID
    public Persona encontrarPersona(int id);

    public Persona actualizarPersona(Persona persona);

    public Persona buscarPorUsuarioYContrasena(String usuario, String contrasena);

    public Persona findByUsuario(String usuario);

    public long obtenerCantidadDePersonas();

}
