package com.cordillera.usuario_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombreUsuario; 

    private String email;
    
    @Column(nullable = false)
    private String contrasena; 

    private String rol;
}