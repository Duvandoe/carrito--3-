package com.co.carrito.carrito.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.carrito.carrito.models.Persona;
import com.co.carrito.carrito.repository.PersonasRepository;


@Service
public class PersonaServiceImp implements PersonaService {

    @Autowired
    private PersonasRepository personaRepository;

    @Override
    public List<Persona> listarPersonas() {
        return personaRepository.findAll();
    }

    @Override
    public void crearPersona(Persona persona) {
        
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

}
    
    


