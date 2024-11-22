package com.co.carrito.carrito.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FotoService {

    public String unaFoto(MultipartFile file);
}
