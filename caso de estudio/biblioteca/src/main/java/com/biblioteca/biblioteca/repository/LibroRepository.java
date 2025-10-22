package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.domain.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    List<Libro> findByCategoriaId(Long categoriaId);
    
    List<Libro> findByDisponibleTrue();
    
    List<Libro> findByDisponibleFalse();
    
    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE %:titulo%")
    List<Libro> findByTituloContaining(@Param("titulo") String titulo);
    
    @Query("SELECT l FROM Libro l WHERE l.autor LIKE %:autor%")
    List<Libro> findByAutorContaining(@Param("autor") String autor);
    
    @Query("SELECT l FROM Libro l WHERE l.isbn = :isbn")
    Optional<Libro> findByIsbn(@Param("isbn") String isbn);
    
    @Query("SELECT l FROM Libro l WHERE l.categoria.nombre = :categoriaNombre")
    List<Libro> findByCategoriaNombre(@Param("categoriaNombre") String categoriaNombre);
    
    @Query("SELECT l FROM Libro l ORDER BY l.titulo ASC")
    List<Libro> findAllOrderByTitulo();
    
    @Query("SELECT l FROM Libro l WHERE l.disponible = true ORDER BY l.titulo ASC")
    List<Libro> findDisponiblesOrderByTitulo();
}
