package com.co.carrito.carrito.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.carrito.carrito.models.Persona;
import com.co.carrito.carrito.repository.PersonasRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReporteServiceImp implements ReporteService {

    @Autowired
    private PersonasRepository personasRepository;

    @Override
    public void generarReporte(HttpServletResponse response) throws JRException, IOException {
        try {
            // Obtener el archivo del reporte Jasper
            InputStream jasperStream = getClass().getResourceAsStream("/reportes/ReportePersona.jasper");

            if (jasperStream == null) {
                throw new IOException("No se encontró el archivo .jasper");
            }

            List<Persona> personas = personasRepository.findAll();
            System.out.println(personas);
            // Rellenar el reporte con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, null, new JRBeanCollectionDataSource(personas));

            // Establecer la respuesta HTTP para enviar el reporte como un archivo PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=ReportePersonas.pdf");

            // Exportar el reporte a PDF
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        } catch (JRException | IOException e) {
            // Manejo de excepciones
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar el reporte: " + e.getMessage());
        }
    }
}




