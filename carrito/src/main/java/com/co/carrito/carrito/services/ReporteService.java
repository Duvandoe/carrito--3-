package com.co.carrito.carrito.services;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;

public interface ReporteService {
    void generarReporte(HttpServletResponse response) throws JRException, IOException;
}