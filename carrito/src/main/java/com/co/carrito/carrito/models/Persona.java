package com.co.carrito.carrito.models;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "personas")
public class Persona {


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private String apellido;

    private String correo;

    private String telefono;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comprar> compras;

    public Persona(String nombre, String apellido, String correo, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
    }


    public Persona() {
    }


    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Comprar> getCompras() {
        return this.compras;
    }

    public void setCompras(List<Comprar> compras) {
        this.compras = compras;
    }

    
}
