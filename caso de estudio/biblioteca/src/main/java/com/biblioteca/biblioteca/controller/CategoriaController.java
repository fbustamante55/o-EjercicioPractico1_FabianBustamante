package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.domain.Categoria;
import com.biblioteca.biblioteca.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    
    private final CategoriaService categoriaService;
    
    @GetMapping
    public String listarCategorias(Model model) {
        List<Categoria> categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);
        return "categorias/list";
    }
    
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }
    
    @PostMapping("/guardar")
    public String guardarCategoria(@Valid @ModelAttribute Categoria categoria, 
                                   BindingResult result, 
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "categorias/form";
        }
        
        try {
            categoriaService.save(categoria);
            redirectAttributes.addFlashAttribute("success", "Categoría guardada exitosamente");
            return "redirect:/categorias";
        } catch (IllegalArgumentException e) {
            result.rejectValue("nombre", "error.categoria", e.getMessage());
            return "categorias/form";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCategoria(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        model.addAttribute("categoria", categoria);
        return "categorias/form";
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizarCategoria(@PathVariable Long id, 
                                     @Valid @ModelAttribute Categoria categoria, 
                                     BindingResult result, 
                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "categorias/form";
        }
        
        try {
            categoriaService.update(id, categoria);
            redirectAttributes.addFlashAttribute("success", "Categoría actualizada exitosamente");
            return "redirect:/categorias";
        } catch (IllegalArgumentException e) {
            result.rejectValue("nombre", "error.categoria", e.getMessage());
            return "categorias/form";
        }
    }
    
    @PostMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoriaService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Categoría eliminada exitosamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/categorias";
    }
    
    @GetMapping("/buscar")
    public String buscarCategorias(@RequestParam String nombre, Model model) {
        List<Categoria> categorias = categoriaService.searchByNombre(nombre);
        model.addAttribute("categorias", categorias);
        model.addAttribute("busqueda", nombre);
        return "categorias/list";
    }
}
