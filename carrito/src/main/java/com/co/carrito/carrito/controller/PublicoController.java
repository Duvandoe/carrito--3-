package com.co.carrito.carrito.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.carrito.carrito.models.Comprar;
import com.co.carrito.carrito.models.Persona;
import com.co.carrito.carrito.models.Producto;
import com.co.carrito.carrito.repository.PersonasRepository;
import com.co.carrito.carrito.repository.ProductoRepository;
import com.co.carrito.carrito.services.CarritoService;
import com.co.carrito.carrito.services.PersonaService;
import com.co.carrito.carrito.services.ProductoService;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/publico")
public class PublicoController {

    private final PersonaService personaService;
    private final ProductoService productoService;
    private final CarritoService carritoService;
    private final PersonasRepository personasRepository;
    private final ProductoRepository productoRepository;

    public PublicoController(PersonaService personaService, ProductoService productoService, PersonasRepository personasRepository, CarritoService carritoService, ProductoRepository productoRepository) {
        this.personaService = personaService;
        this.productoService = productoService;
        this.carritoService = carritoService;
        this.personasRepository = personasRepository;
        this.productoRepository = productoRepository;
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
    public String postMethodName(@RequestParam("usuario") String usuario, @RequestParam("contrasena") String contrasena, HttpSession session) {

        // Verificar si es el administrador
    if (usuario.equals("admin") && contrasena.equals("123456")) {
        session.setAttribute("usuarioLogueado", "admin");
        return "redirect:/admin/inicio";
    }
    // Verificar si es un usuario registrado (ejemplo con datos ficticios)
    Persona persona = personasRepository.findByUsuario(usuario);
    if (persona != null && persona.getContrasena().equals(contrasena)) {
        session.setAttribute("usuarioLogueado", persona.getNombre());
        return "redirect:/publico/inicio";
    }

    // Si no coincide, redirigir al login con un mensaje de error (puedes usar un modelo para pasar datos)
    return "login";
        
    }  

    @GetMapping("/logout")
    public String logout(HttpSession session) {
    session.invalidate(); // Invalidar la sesión
    return "redirect:/publico/login"; // Redirigir al login
    }

    @PostMapping("/compra")
public String agregarAlCarrito(@RequestParam("productoId") int productoId,
                               @RequestParam(value = "cantidad", defaultValue = "1") int cantidad,
                               HttpSession session) {

    // Obtener el usuario de la sesión
    Persona persona = (Persona) session.getAttribute("usuario");

    if (persona == null) {
        return "Por favor inicie sesión para realizar compras.";
    }

    // Buscar el producto por ID
    Producto producto = productoRepository.findById(productoId).orElse(null);

    if (producto == null) {
        return "Producto no encontrado.";
    }

    // Verificar si hay suficiente stock
    if (cantidad > producto.getStock()) {
        return "No hay suficiente stock disponible para esta cantidad.";
    }

    // Obtener compras existentes del usuario para el producto
    List<Comprar> comprasExistentes = carritoService.obtenerComprasPorProducto(producto);
    
    // Buscar si ya existe una compra del mismo producto para el usuario
    Comprar compraExistente = comprasExistentes.stream()
            .filter(compra -> compra.getPersona().equals(persona))
            .findFirst()
            .orElse(null);

    if (compraExistente != null) {
        // Si el producto ya está en el carrito, verificar que no se exceda el stock
        if (compraExistente.getCantidad() + cantidad > producto.getStock()) {
            return "No hay suficiente stock para esta cantidad en el carrito.";
        }
        // Actualizar la cantidad de la compra existente
        compraExistente.setCantidad(compraExistente.getCantidad() + cantidad);
        carritoService.actualizarCompra(compraExistente); // Método para actualizar la compra
    } else {
        // Si el producto no está en el carrito, crear una nueva compra
        carritoService.crearCompra(persona, producto, cantidad);
    }

    return "Producto agregado al carrito con éxito.";
}



    @GetMapping("/compras/productos/{productoId}")
@ResponseBody
public List<Comprar> obtenerComprasPorProducto(@PathVariable int productoId) {
    Producto producto = productoRepository.findById(productoId).orElse(null);
    if (producto == null) {
        return new ArrayList<>(); // Retorna una lista vacía si no se encuentra el producto
    }
    return carritoService.obtenerComprasPorProducto(producto);
}


    // Obtener las compras (productos) del carrito
    @GetMapping("/carrito")
    @ResponseBody
    public List<Comprar> obtenerCarrito(HttpSession session) {
    Persona persona = (Persona) session.getAttribute("usuario");
    if (persona == null) {
        return new ArrayList<>(); // Retorna una lista vacía si no hay persona en sesión
    }

    // Obtener las compras del carrito de la persona
    return carritoService.obtenerComprasPorPersona(persona);
}

    
}
