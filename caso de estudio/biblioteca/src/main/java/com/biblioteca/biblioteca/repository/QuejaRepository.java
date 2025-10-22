package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.domain.Queja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuejaRepository extends JpaRepository<Queja, Long> {
    
    List<Queja> findByTipo(Queja.TipoQueja tipo);
    
    List<Queja> findByTratadoTrue();
    
    List<Queja> findByTratadoFalse();
    
    @Query("SELECT q FROM Queja q WHERE q.email = :email")
    List<Queja> findByEmail(@Param("email") String email);
    
    @Query("SELECT q FROM Queja q ORDER BY q.createdAt DESC")
    List<Queja> findAllOrderByFechaCreacionDesc();
    
    @Query("SELECT q FROM Queja q WHERE q.tratado = false ORDER BY q.createdAt ASC")
    List<Queja> findNoTratadasOrderByFechaCreacionAsc();
    
    @Query("SELECT COUNT(q) FROM Queja q WHERE q.tratado = false")
    Long countNoTratadas();
}
