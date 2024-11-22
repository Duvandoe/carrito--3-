package com.co.carrito.carrito.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServiceImp implements FotoService {

    @Value("${app.fotoService.dir}")
    private String uploadDir;


    @Override
    public String unaFoto(MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")) {
                throw new Exception("Nombre de archivo inválido: " + fileName);
            }
    
            String newFileName = UUID.randomUUID().toString() + "_" + fileName;
            Path dirPath = Paths.get(uploadDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
    
            Path filePath = dirPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath);
    
            // Verifica si el archivo fue guardado correctamente
            System.out.println("Imagen guardada en: " + filePath);
    
            return "/FotoServicio/" + newFileName; // Retorna la URL de la imagen
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
