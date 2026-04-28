package com.cordillera.inventario_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(length = 500)
    private String descripcion;
}