package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.domain.Queja;
import com.biblioteca.biblioteca.repository.QuejaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuejaService {
    
    private final QuejaRepository quejaRepository;
    
    public List<Queja> findAll() {
        return quejaRepository.findAllOrderByFechaCreacionDesc();
    }
    
    public List<Queja> findNoTratadas() {
        return quejaRepository.findNoTratadasOrderByFechaCreacionAsc();
    }
    
    public List<Queja> findByTipo(Queja.TipoQueja tipo) {
        return quejaRepository.findByTipo(tipo);
    }
    
    public List<Queja> findByEmail(String email) {
        return quejaRepository.findByEmail(email);
    }
    
    public Queja save(Queja queja) {
        return quejaRepository.save(queja);
    }
    
    public Queja marcarComoTratada(Long id) {
        Queja queja = quejaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Queja no encontrada con ID: " + id));
        
        queja.setTratado(true);
        return quejaRepository.save(queja);
    }
    
    public Queja marcarComoNoTratada(Long id) {
        Queja queja = quejaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Queja no encontrada con ID: " + id));
        
        queja.setTratado(false);
        return quejaRepository.save(queja);
    }
    
    public void deleteById(Long id) {
        if (!quejaRepository.existsById(id)) {
            throw new IllegalArgumentException("Queja no encontrada con ID: " + id);
        }
        quejaRepository.deleteById(id);
    }
    
    public Long countNoTratadas() {
        return quejaRepository.countNoTratadas();
    }
    
    public List<Queja> findByTratado(boolean tratado) {
        return tratado ? quejaRepository.findByTratadoTrue() : quejaRepository.findByTratadoFalse();
    }
}
