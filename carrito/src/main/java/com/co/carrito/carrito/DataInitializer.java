package com.co.carrito.carrito;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.co.carrito.carrito.models.Rol;
import com.co.carrito.carrito.models.RolEnum;
import com.co.carrito.carrito.repository.RolRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;

    public DataInitializer(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica si el rol "CLIENTE" ya existe en la base de datos
        if (rolRepository.findByNombrerol(RolEnum.CLIENTE) == null) {
            // Si no existe, se crea e inserta el rol "CLIENTE"
            Rol rolCliente = new Rol();
            rolCliente.setNombrerol(RolEnum.CLIENTE);
            rolRepository.save(rolCliente);
            System.out.println("Rol CLIENTE insertado exitosamente.");
        }
        
        // Si deseas agregar el rol "ADMIN", puedes hacerlo de la misma manera
        if (rolRepository.findByNombrerol(RolEnum.ADMIN) == null) {
            Rol rolAdmin = new Rol();
            rolAdmin.setNombrerol(RolEnum.ADMIN);
            rolRepository.save(rolAdmin);
            System.out.println("Rol ADMIN insertado exitosamente.");
        }
    }
}
