package com.co.carrito.carrito.services;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void generarReporte() throws JRException, IOException {
        // Obtener el archivo del reporte Jasper
        InputStream jasperStream = getClass().getResourceAsStream("../reportes/ReportePersona2.jasper");

        // Rellenar el reporte con los datos
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, null, new JRBeanCollectionDataSource(personasRepository.findAll()));

        String outputPath = "C:/Users/USUARIO/Desktop/reportes/ReportePersonas.pdf"; // Ruta absoluta
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
    }
}



