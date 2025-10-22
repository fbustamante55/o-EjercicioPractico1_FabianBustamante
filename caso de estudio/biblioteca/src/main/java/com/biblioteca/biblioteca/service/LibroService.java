package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.domain.Libro;
import com.biblioteca.biblioteca.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LibroService {
    
    private final LibroRepository libroRepository;
    
    public List<Libro> findAll() {
        return libroRepository.findAllOrderByTitulo();
    }
    
    public List<Libro> findDisponibles() {
        return libroRepository.findDisponiblesOrderByTitulo();
    }
    
    public Optional<Libro> findById(Long id) {
        return libroRepository.findById(id);
    }
    
    public List<Libro> findByCategoriaId(Long categoriaId) {
        return libroRepository.findByCategoriaId(categoriaId);
    }
    
    public List<Libro> findByCategoriaNombre(String categoriaNombre) {
        return libroRepository.findByCategoriaNombre(categoriaNombre);
    }
    
    public Libro save(Libro libro) {
        // Verificar si ya existe un libro con el mismo ISBN
        if (libro.getIsbn() != null && !libro.getIsbn().trim().isEmpty()) {
            Optional<Libro> libroExistente = libroRepository.findByIsbn(libro.getIsbn());
            if (libroExistente.isPresent() && !libroExistente.get().getId().equals(libro.getId())) {
                throw new IllegalArgumentException("Ya existe un libro con el ISBN: " + libro.getIsbn());
            }
        }
        
        return libroRepository.save(libro);
    }
    
    public Libro update(Long id, Libro libroActualizado) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
        
        // Verificar ISBN duplicado
        if (libroActualizado.getIsbn() != null && !libroActualizado.getIsbn().trim().isEmpty()) {
            Optional<Libro> libroExistente = libroRepository.findByIsbn(libroActualizado.getIsbn());
            if (libroExistente.isPresent() && !libroExistente.get().getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe un libro con el ISBN: " + libroActualizado.getIsbn());
            }
        }
        
        libro.setTitulo(libroActualizado.getTitulo());
        libro.setAutor(libroActualizado.getAutor());
        libro.setIsbn(libroActualizado.getIsbn());
        libro.setDescripcion(libroActualizado.getDescripcion());
        libro.setCategoria(libroActualizado.getCategoria());
        libro.setFechaPublicacion(libroActualizado.getFechaPublicacion());
        libro.setDisponible(libroActualizado.getDisponible());
        libro.setPrecio(libroActualizado.getPrecio());
        
        return libroRepository.save(libro);
    }
    
    public void deleteById(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new IllegalArgumentException("Libro no encontrado con ID: " + id);
        }
        libroRepository.deleteById(id);
    }
    
    public List<Libro> searchByTitulo(String titulo) {
        return libroRepository.findByTituloContaining(titulo);
    }
    
    public List<Libro> searchByAutor(String autor) {
        return libroRepository.findByAutorContaining(autor);
    }
    
    public Libro toggleDisponibilidad(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
        
        libro.setDisponible(!libro.getDisponible());
        return libroRepository.save(libro);
    }
    
    public Optional<Libro> findByIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn);
    }
}
