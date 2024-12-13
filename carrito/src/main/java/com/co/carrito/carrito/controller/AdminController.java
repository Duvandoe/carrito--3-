package com.co.carrito.carrito.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.co.carrito.carrito.models.Comprar;
import com.co.carrito.carrito.models.Persona;
import com.co.carrito.carrito.models.Producto;
import com.co.carrito.carrito.repository.PersonasRepository;
import com.co.carrito.carrito.repository.ProductoRepository;
import com.co.carrito.carrito.services.CarritoService;
import com.co.carrito.carrito.services.PersonaService;
import com.co.carrito.carrito.services.ProductoService;
import com.co.carrito.carrito.services.ReporteService;

import net.sf.jasperreports.engine.JRException;



@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CarritoService compraService;
    private final PersonasRepository personaRepository;
    private final ProductoRepository productoRepository;
    private final ProductoService productoService;
    private final PersonaService personaService;
    private final ReporteService reporteService;


    public AdminController(CarritoService compraService, PersonasRepository personaRepository, ProductoRepository productoRepository
    , ProductoService productoService, PersonaService personaService, ReporteService reporteService) {
        this.compraService = compraService;
        this.personaRepository = personaRepository;
        this.productoRepository = productoRepository;
        this.productoService = productoService;
        this.personaService = personaService;
        this.reporteService = reporteService;
    }

    @GetMapping("/inicio")
    public String MostrarInicio() {
        return "admin";
    }

    @GetMapping("/clientes")
    public String clientes() {
        return "";
    }


    @GetMapping("/compras")
    public String productos(Model model) {

        model.addAttribute("compras", compraService.listarCompras());
        model.addAttribute("personas", personaRepository.findAll());
        model.addAttribute("productos", productoRepository.findAll());
        model.addAttribute("comprar", new Comprar());

        return "adminCompras";
    }
    
   @PostMapping("/realizar")
public String realizarCompra(@RequestParam("personaId") int personaId, 
                             @RequestParam("productoId") int productoId,
                             @RequestParam("cantidad") int cantidad, 
                             RedirectAttributes redirectAttributes) {
    Persona persona = personaRepository.findById(personaId).orElse(null);
    Producto producto = productoRepository.findById(productoId).orElse(null);

    if (persona != null && producto != null) {
        compraService.crearCompra(persona, producto, cantidad);
        redirectAttributes.addFlashAttribute("mensaje", "Compra realizada con éxito");
    } else {
        redirectAttributes.addFlashAttribute("mensaje", "Error al realizar la compra");
    }

    return "redirect:/admin/compras";
}


    @GetMapping("/productos")
    public String listarProductos(Model model) {
        List<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("producto", new Producto());
        return "adminProductos"; // Ajusta el nombre de la vista según corresponda
    }

    
    
    @GetMapping("/cliente")
    public String listarPersonas(Model modelo){
        modelo.addAttribute("personas", personaService.listarPersonas());
        modelo.addAttribute("persona", new Persona());
        return "adminCliente";
    }
    @PostMapping("/crearUsuario")
    public String CrearPersona(@ModelAttribute Persona persona) {
        personaService.crearPersona(persona);
        return "redirect:/admin/cliente";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto, @RequestParam("file") MultipartFile file) {
        try {
            System.out.println("Producto recibido: " + producto);
            System.out.println("Archivo recibido: " + file.getOriginalFilename());
        
            productoService.agregarProducto(producto, file);
            return "redirect:/admin/productos";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/productos?error=true";
        }
    }


    @GetMapping("/eliminar")
    public String eliminarProducto(@RequestParam("id") int id) {
        
        productoService.eliminarProducto(id);

        return "redirect:/admin/productos";
    }

    


    

    @GetMapping("/borrar/{id}")
    public String EliminarPersona(@PathVariable("id") int id){
        personaService.borrarPersona(id);
        return "redirect:/admin/cliente";
    }

    
    
    @PostMapping("/actualizar/{id}")
    public String actualizarPersona(@PathVariable("id") int id, @ModelAttribute("persona") Persona persona) {
        persona.setId(id); // Establece el ID para asegurar que se actualice el registro
        personaService.actualizarPersona(persona);
        return "redirect:/admin/cliente"; // Redirige a la lista después de actualizar
    }

    @PostMapping("/actualizarProducto/{idProducto}")
    public String actualizarProducto(@PathVariable("idProducto") int idProducto,
                                    @ModelAttribute("producto") Producto producto,
                                    @RequestParam("file") MultipartFile file) {
    producto.setIdProducto(idProducto); // Establece el ID del producto
    productoService.ActualizarProducto(producto, file);
    return "redirect:/admin/productos"; // Redirige a la lista de productos
}

    


@GetMapping("/editar/{id}")
public String EditarPersona(@PathVariable int id, Model modelo) {
    Persona persona = personaService.encontrarPersona(id);
    if (persona == null) {
        throw new IllegalArgumentException("Persona no encontrada con ID: " + id);
    }
    modelo.addAttribute("persona", persona);
    return "Editarpersona";
}


    @GetMapping("/editarProducto/{idProducto}")
    public String EditarProducto(@PathVariable int idProducto, Model modelo) {
        Producto producto = productoService.obtenerProductoPorId(idProducto);
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + idProducto);
        }
        modelo.addAttribute("producto", producto);
        return "EditarProducto"; // Nombre de la plantilla Thymeleaf
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCompra(@PathVariable int id) {
        compraService.borrarCompra(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/buscar")
    public ResponseEntity<List<Persona>> buscarPersonas(@RequestParam("nombre") String nombre) {
        List<Persona> personas = personaService.buscarPorNombre(nombre);
        if (personas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(personas);
    }

    @GetMapping("/generarReporte")
    public ResponseEntity<String> generarReporte() {
        try {
            reporteService.generarReporte();
            return ResponseEntity.ok("Reporte generado correctamente");
        } catch (IOException | JRException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar el reporte");
        }
    }

    
    
}


