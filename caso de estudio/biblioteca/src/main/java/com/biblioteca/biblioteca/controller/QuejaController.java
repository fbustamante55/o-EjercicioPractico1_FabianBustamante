package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.domain.Queja;
import com.biblioteca.biblioteca.service.QuejaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/quejas")
@RequiredArgsConstructor
public class QuejaController {
    
    private final QuejaService quejaService;
    
    @GetMapping
    public String listarQuejas(Model model) {
        List<Queja> quejas = quejaService.findAll();
        Long noTratadas = quejaService.countNoTratadas();
        model.addAttribute("quejas", quejas);
        model.addAttribute("noTratadas", noTratadas);
        return "quejas/list";
    }
    
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaQueja(Model model) {
        model.addAttribute("queja", new Queja());
        return "quejas/form";
    }
    
    @PostMapping("/guardar")
    public String guardarQueja(@Valid @ModelAttribute Queja queja, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "quejas/form";
        }
        
        quejaService.save(queja);
        redirectAttributes.addFlashAttribute("success", "Su mensaje ha sido enviado exitosamente. Gracias por su feedback.");
        return "redirect:/quejas/nueva";
    }
    
    @PostMapping("/marcar-tratada/{id}")
    public String marcarComoTratada(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            quejaService.marcarComoTratada(id);
            redirectAttributes.addFlashAttribute("success", "Queja marcada como tratada");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/quejas";
    }
    
    @PostMapping("/marcar-no-tratada/{id}")
    public String marcarComoNoTratada(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            quejaService.marcarComoNoTratada(id);
            redirectAttributes.addFlashAttribute("success", "Queja marcada como no tratada");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/quejas";
    }
    
    @PostMapping("/eliminar/{id}")
    public String eliminarQueja(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            quejaService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Queja eliminada exitosamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/quejas";
    }
    
    @GetMapping("/filtrar")
    public String filtrarQuejas(@RequestParam(required = false) Queja.TipoQueja tipo,
                                @RequestParam(required = false) Boolean tratado,
                                Model model) {
        List<Queja> quejas;
        
        if (tipo != null) {
            quejas = quejaService.findByTipo(tipo);
            model.addAttribute("filtroTipo", tipo);
        } else if (tratado != null) {
            quejas = quejaService.findByTratado(tratado);
            model.addAttribute("filtroTratado", tratado);
        } else {
            quejas = quejaService.findAll();
        }
        
        Long noTratadas = quejaService.countNoTratadas();
        model.addAttribute("quejas", quejas);
        model.addAttribute("noTratadas", noTratadas);
        return "quejas/list";
    }
    
    @GetMapping("/no-tratadas")
    public String listarNoTratadas(Model model) {
        List<Queja> quejas = quejaService.findNoTratadas();
        Long noTratadas = quejaService.countNoTratadas();
        model.addAttribute("quejas", quejas);
        model.addAttribute("noTratadas", noTratadas);
        model.addAttribute("soloNoTratadas", true);
        return "quejas/list";
    }
}
