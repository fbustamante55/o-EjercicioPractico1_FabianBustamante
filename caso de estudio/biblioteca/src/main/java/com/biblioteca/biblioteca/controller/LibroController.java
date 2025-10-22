package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.domain.Categoria;
import com.biblioteca.biblioteca.domain.Libro;
import com.biblioteca.biblioteca.service.CategoriaService;
import com.biblioteca.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/libros")
@RequiredArgsConstructor
public class LibroController {
    
    private final LibroService libroService;
    private final CategoriaService categoriaService;
    
    @GetMapping
    public String listarLibros(Model model) {
        List<Libro> libros = libroService.findAll();
        model.addAttribute("libros", libros);
        return "libros/list";
    }
    
    @GetMapping("/disponibles")
    public String listarLibrosDisponibles(Model model) {
        List<Libro> libros = libroService.findDisponibles();
        model.addAttribute("libros", libros);
        model.addAttribute("soloDisponibles", true);
        return "libros/list";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoLibro(Model model) {
        List<Categoria> categorias = categoriaService.findAll();
        model.addAttribute("libro", new Libro());
        model.addAttribute("categorias", categorias);
        return "libros/form";
    }
    
    @PostMapping("/guardar")
    public String guardarLibro(@Valid @ModelAttribute Libro libro, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            List<Categoria> categorias = categoriaService.findAll();
            model.addAttribute("categorias", categorias);
            return "libros/form";
        }
        
        try {
            libroService.save(libro);
            redirectAttributes.addFlashAttribute("success", "Libro guardado exitosamente");
            return "redirect:/libros";
        } catch (IllegalArgumentException e) {
            result.rejectValue("isbn", "error.libro", e.getMessage());
            List<Categoria> categorias = categoriaService.findAll();
            model.addAttribute("categorias", categorias);
            return "libros/form";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarLibro(@PathVariable Long id, Model model) {
        Libro libro = libroService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        List<Categoria> categorias = categoriaService.findAll();
        model.addAttribute("libro", libro);
        model.addAttribute("categorias", categorias);
        return "libros/form";
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizarLibro(@PathVariable Long id, 
                                 @Valid @ModelAttribute Libro libro, 
                                 BindingResult result, 
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (result.hasErrors()) {
            List<Categoria> categorias = categoriaService.findAll();
            model.addAttribute("categorias", categorias);
            return "libros/form";
        }
        
        try {
            libroService.update(id, libro);
            redirectAttributes.addFlashAttribute("success", "Libro actualizado exitosamente");
            return "redirect:/libros";
        } catch (IllegalArgumentException e) {
            result.rejectValue("isbn", "error.libro", e.getMessage());
            List<Categoria> categorias = categoriaService.findAll();
            model.addAttribute("categorias", categorias);
            return "libros/form";
        }
    }
    
    @PostMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            libroService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Libro eliminado exitosamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/libros";
    }
    
    @PostMapping("/toggle-disponibilidad/{id}")
    public String toggleDisponibilidad(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Libro libro = libroService.toggleDisponibilidad(id);
            String mensaje = libro.getDisponible() ? "Libro marcado como disponible" : "Libro marcado como no disponible";
            redirectAttributes.addFlashAttribute("success", mensaje);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/libros";
    }
    
    @GetMapping("/buscar")
    public String buscarLibros(@RequestParam(required = false) String titulo,
                              @RequestParam(required = false) String autor,
                              @RequestParam(required = false) String categoria,
                              Model model) {
        List<Libro> libros;
        
        if (titulo != null && !titulo.trim().isEmpty()) {
            libros = libroService.searchByTitulo(titulo);
            model.addAttribute("busquedaTitulo", titulo);
        } else if (autor != null && !autor.trim().isEmpty()) {
            libros = libroService.searchByAutor(autor);
            model.addAttribute("busquedaAutor", autor);
        } else if (categoria != null && !categoria.trim().isEmpty()) {
            libros = libroService.findByCategoriaNombre(categoria);
            model.addAttribute("busquedaCategoria", categoria);
        } else {
            libros = libroService.findAll();
        }
        
        model.addAttribute("libros", libros);
        return "libros/list";
    }
    
    @GetMapping("/detalle/{id}")
    public String verDetalleLibro(@PathVariable Long id, Model model) {
        Libro libro = libroService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        model.addAttribute("libro", libro);
        return "libros/detail";
    }
}
