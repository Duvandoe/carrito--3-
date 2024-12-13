package com.co.carrito.carrito;

public class UsuarioYaExisteException extends RuntimeException {
    
    // Constructor que acepta un mensaje de error
    public UsuarioYaExisteException(String mensaje) {
        super(mensaje);
    }
}
