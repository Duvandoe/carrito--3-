package com.co.carrito.carrito.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombrerol")
    private RolEnum nombrerol;  // Aquí cambiamos a RolEnum

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RolEnum getNombrerol() {
        return nombrerol;
    }

    public void setNombrerol(RolEnum nombrerol) {
        this.nombrerol = nombrerol;
    }
}

