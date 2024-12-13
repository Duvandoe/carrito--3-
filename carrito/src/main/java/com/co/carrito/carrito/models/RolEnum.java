package com.co.carrito.carrito.models;

public enum RolEnum {
    ADMIN,
    CLIENTE;

    public static RolEnum fromString(String role) {
        for (RolEnum r : RolEnum.values()) {
            if (r.name().equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("No se encuentra el rol: " + role);
    }
}
