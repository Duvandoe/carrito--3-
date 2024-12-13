package com.co.carrito.carrito.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.carrito.carrito.UsuarioYaExisteException;
import com.co.carrito.carrito.models.Persona;
import com.co.carrito.carrito.models.Rol;
import com.co.carrito.carrito.models.RolEnum;
import com.co.carrito.carrito.repository.PersonasRepository;
import com.co.carrito.carrito.repository.RolRepository;


@Service
public class PersonaServiceImp implements PersonaService {

    @Autowired
    private PersonasRepository personaRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RolService rolService;

    @Override
    public List<Persona> listarPersonas() {
        return personaRepository.findAll();
    }

    @Override
    public void crearPersona(Persona persona) {
    // Verificar si el nombre de usuario ya existe
    if (personaRepository.existsByUsuario(persona.getUsuario())) {
        throw new UsuarioYaExisteException("El nombre de usuario ya está registrado");
    }

    // Obtener el rol de "Cliente" usando el RolEnum
    Rol rolCliente = rolRepository.findByNombrerol(RolEnum.CLIENTE);
    if (rolCliente == null) {
        // Crear el rol CLIENTE si no existe
        rolCliente = new Rol();
        rolCliente.setNombrerol(RolEnum.CLIENTE);
        rolCliente = rolRepository.save(rolCliente); // Guardar en la base de datos
    }

    // Asignar el rol al usuario
    persona.setRol(rolCliente);

    // Guardar la persona con el rol asignado
    personaRepository.save(persona);
}

    

    @Override
    public void borrarPersona(int id) {
        personaRepository.deleteById(id);
    }

    @Override
    public Persona encontrarPersona(int id){
        Optional<Persona> persona = personaRepository.findById(id);
        return persona.orElse(null);
    }
    

    @Override
    public Persona actualizarPersona(Persona persona) {
        // Busca si existe la persona con ese ID
        Optional<Persona> personaOpt = personaRepository.findById(persona.getId());
        
        if (personaOpt.isPresent()) {
            Persona personaExistente = personaOpt.get();
            // Actualiza solo los campos necesarios
            personaExistente.setNombre(persona.getNombre());
            personaExistente.setApellido(persona.getApellido());
            personaExistente.setCorreo(persona.getCorreo());
            personaExistente.setTelefono(persona.getTelefono());
            return personaRepository.save(personaExistente);
        }
        return null; // Maneja si la persona no se encuentra
    }
    
    @Override
    public List<Persona> buscarPorNombre(String nombre) {
        return personaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public Persona buscarPorUsuarioYContrasena(String usuario, String contrasena) {
        return personaRepository.findByUsuarioAndContrasena(usuario, contrasena);
    }

    @Override
    public Persona findByUsuario(String usuario) {
        return personaRepository.findByUsuario(usuario);
    }
}
    
    


