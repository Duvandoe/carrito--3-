package com.co.carrito.carrito.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.co.carrito.carrito.models.Persona;
import com.co.carrito.carrito.models.Producto;
import com.co.carrito.carrito.services.PersonaService;
import com.co.carrito.carrito.services.ProductoService;



@Controller
@RequestMapping("/publico")
public class PublicoController {

    private final PersonaService personaService;
    private final ProductoService productoService;

    public PublicoController(PersonaService personaService, ProductoService productoService) {
        this.personaService = personaService;
        this.productoService = productoService;
    }

    @GetMapping("/inicio")
    public String index() {
        
        return "index";
    }

     @GetMapping("/productos")
	public String listarProductos(Model modelo) {
		List<Producto> productos = productoService.listarProductos(); // Asegúrate de que este método existe en tu servicio
        modelo.addAttribute("productos", productos);
		return "productosCliente";
	}

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registrarse")
    public String registrarPersonas(Model modelo) {

        modelo.addAttribute("persona", new Persona());

        return "peronasRegistro";
    }


    @PostMapping("/registroPerosnas")
    public String recibirDatosPersonas(@ModelAttribute("persona") Persona persona) {

        personaService.crearPersona(persona);
        
        return "redirect:/publico/registrarse";
    }
    
    @PostMapping("/iniciarSesion")
    public String postMethodName(@RequestParam("usuario") String usuario, @RequestParam("contrasena") String contrasena) {

        if (usuario.equals("admin") && contrasena.equals("123456")) {
            return "redirect:/admin/inicio";
        }
        return "Login";

    }
    
    


    
}
