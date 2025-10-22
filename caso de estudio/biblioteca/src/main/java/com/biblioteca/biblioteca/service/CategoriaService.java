package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.domain.Categoria;
import com.biblioteca.biblioteca.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;
    
    public List<Categoria> findAll() {
        return categoriaRepository.findAllOrderByNombre();
    }
    
    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }
    
    public Optional<Categoria> findByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }
    
    public Categoria save(Categoria categoria) {
        if (categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + categoria.getNombre());
        }
        return categoriaRepository.save(categoria);
    }
    
    public Categoria update(Long id, Categoria categoriaActualizada) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + id));
        
        // Verificar si el nuevo nombre ya existe en otra categoría
        if (!categoria.getNombre().equals(categoriaActualizada.getNombre()) && 
            categoriaRepository.existsByNombre(categoriaActualizada.getNombre())) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + categoriaActualizada.getNombre());
        }
        
        categoria.setNombre(categoriaActualizada.getNombre());
        categoria.setDescripcion(categoriaActualizada.getDescripcion());
        
        return categoriaRepository.save(categoria);
    }
    
    public void deleteById(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoría no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
    
    public List<Categoria> searchByNombre(String nombre) {
        return categoriaRepository.findByNombreContaining(nombre);
    }
    
    public boolean existsByNombre(String nombre) {
        return categoriaRepository.existsByNombre(nombre);
    }
}
