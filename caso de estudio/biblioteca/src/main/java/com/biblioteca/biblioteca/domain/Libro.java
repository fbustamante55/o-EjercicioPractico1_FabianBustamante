package com.biblioteca.biblioteca.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "libro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede exceder 255 caracteres")
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;
    
    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 200, message = "El autor no puede exceder 200 caracteres")
    @Column(name = "autor", nullable = false, length = 200)
    private String autor;
    
    @Size(max = 20, message = "El ISBN no puede exceder 20 caracteres")
    @Column(name = "isbn", length = 20)
    private String isbn;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @NotNull(message = "La categoría es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    
    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;
    
    @NotNull(message = "El estado de disponibilidad es obligatorio")
    @Column(name = "disponible", nullable = false)
    private Boolean disponible = true;
    
    @Column(name = "precio", precision = 10, scale = 2)
    private BigDecimal precio;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
