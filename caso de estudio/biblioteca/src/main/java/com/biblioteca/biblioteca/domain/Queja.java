package com.biblioteca.biblioteca.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "queja")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Queja {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
    @Column(name = "nombre_cliente", length = 150)
    private String nombreCliente;
    
    @Email(message = "El formato del email no es válido")
    @Size(max = 200, message = "El email no puede exceder 200 caracteres")
    @Column(name = "email", length = 200)
    private String email;
    
    @Size(max = 30, message = "El teléfono no puede exceder 30 caracteres")
    @Column(name = "telefono", length = 30)
    private String telefono;
    
    @NotNull(message = "El tipo es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoQueja tipo = TipoQueja.CONSULTA;
    
    @Size(max = 200, message = "El asunto no puede exceder 200 caracteres")
    @Column(name = "asunto", length = 200)
    private String asunto;
    
    @NotBlank(message = "El mensaje es obligatorio")
    @Column(name = "mensaje", nullable = false, columnDefinition = "TEXT")
    private String mensaje;
    
    @NotNull(message = "El estado de tratamiento es obligatorio")
    @Column(name = "tratado", nullable = false)
    private Boolean tratado = false;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    public enum TipoQueja {
        QUEJA, SUGERENCIA, CONSULTA
    }
}
