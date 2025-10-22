package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.domain.Libro;
import com.biblioteca.biblioteca.service.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    
    private final LibroService libroService;
    
    public HomeController(LibroService libroService) {
        this.libroService = libroService;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        // Obtener algunos libros destacados para mostrar en la página de inicio
        List<Libro> librosDestacados = libroService.findDisponibles();
        if (librosDestacados.size() > 3) {
            librosDestacados = librosDestacados.subList(0, 3);
        }
        
        model.addAttribute("librosDestacados", librosDestacados);
        return "home";
    }
    
    @GetMapping("/acerca")
    public String acerca() {
        return "acerca";
    }
    
    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }
}
