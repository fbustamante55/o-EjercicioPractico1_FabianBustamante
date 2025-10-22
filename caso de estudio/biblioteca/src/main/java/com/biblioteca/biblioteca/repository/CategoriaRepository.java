package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    Optional<Categoria> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);
    
    @Query("SELECT c FROM Categoria c WHERE c.nombre LIKE %:nombre%")
    List<Categoria> findByNombreContaining(@Param("nombre") String nombre);
    
    @Query("SELECT c FROM Categoria c ORDER BY c.nombre ASC")
    List<Categoria> findAllOrderByNombre();
}
