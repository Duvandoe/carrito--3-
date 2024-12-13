package com.co.carrito.carrito.services;

import java.io.IOException;

import net.sf.jasperreports.engine.JRException;

public interface ReporteService {
    void generarReporte() throws JRException, IOException;
}