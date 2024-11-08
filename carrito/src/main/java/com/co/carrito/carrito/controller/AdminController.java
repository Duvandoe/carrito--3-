package com.co.carrito.carrito.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseBody;
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



@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CarritoService compraService;
    private final PersonasRepository personaRepository;
    private final ProductoRepository productoRepository;
    private final ProductoService productoService;
    private final PersonaService personaService;


    public AdminController(CarritoService compraService, PersonasRepository personaRepository, ProductoRepository productoRepository, ProductoService productoService, PersonaService personaService) {
        this.compraService = compraService;
        this.personaRepository = personaRepository;
        this.productoRepository = productoRepository;
        this.productoService = productoService;
        this.personaService = personaService;
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
                                 Model model) {
        Persona persona = personaRepository.findById(personaId).orElse(null);
        Producto producto = productoRepository.findById(productoId).orElse(null);
    
        if (persona != null && producto != null) {
            compraService.crearCompra(persona, producto, cantidad);
            model.addAttribute("mensaje", "Compra realizada con éxito");
        } else {
            model.addAttribute("mensaje", "Error al realizar la compra");
        }
    
        return "redirect:/admin/compras";
    }

    @GetMapping("/productos")
    public String listarProductos(Model model) {
        List<Producto> productos = productoRepository.findAll();
    
        // Convertir la foto a Base64 para cada producto
        for (Producto producto : productos) {
            byte[] foto = producto.getFoto();
            String fotoBase64 = foto != null ? Base64.getEncoder().encodeToString(foto) : null;
            producto.setFotoBase64(fotoBase64); // Establecer el atributo fotoBase64
        }
    
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
    public String CrearPersona(@ModelAttribute Persona persona, Model modelo){
        personaService.crearPersona(persona);
        return "redirect:/admin/cliente";
    }

    @SuppressWarnings("null")
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto, 
                             @RequestParam("file") MultipartFile file, 
                             RedirectAttributes redirectAttributes) {
    try {
        // Validar que el archivo sea una imagen
        if (file.isEmpty() || !file.getContentType().startsWith("image/")) {
            redirectAttributes.addFlashAttribute("error", "Por favor, selecciona una imagen válida.");
            return "redirect:/admin/productos";
        }

        // Convertir la imagen a un byte[]
        byte[] foto = file.getBytes();
        System.out.println("Tamaño de foto: " + foto.length);

        // Llamar al servicio para agregar el producto con la foto
        productoService.agregarProducto(producto, foto);
        
        // Agregar un mensaje de éxito
        redirectAttributes.addFlashAttribute("mensaje", "Producto guardado exitosamente!");
    } catch (IOException e) {
        e.printStackTrace();
        // Manejo de errores
        redirectAttributes.addFlashAttribute("error", "Ocurrió un error al guardar el producto.");
    }
    return "redirect:/admin/productos"; 
}

@GetMapping("/productos/{id}/foto")
@ResponseBody
public ResponseEntity<byte[]> getFoto(@PathVariable int id) {
    Producto producto = productoService.obtenerProductoPorId(id);
    if (producto == null) {
        return ResponseEntity.notFound().build();
    }
    
    byte[] foto = producto.getFoto();
    
    if (foto != null) {
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG) // Asegúrate de que sea IMAGE_PNG
            .body(foto);
    } else {
        // Se puede retornar una imagen por defecto si no hay foto
        return ResponseEntity.notFound().build();
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


    @GetMapping("/editar/{id}")
    public String EditarPersona(@PathVariable int id, Model modelo) {
        Persona persona = personaService.encontrarPersona(id);
        modelo.addAttribute("persona", persona); // Añade persona al modelo para el formulario
        return "Editarpersona";
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCompra(@PathVariable int id) {
        compraService.borrarCompra(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/buscar")
    @ResponseBody
    public List<Persona> buscarPersonas(@RequestParam("nombre") String nombre) {
        return personaService.buscarPorNombre(nombre);
    }

}


