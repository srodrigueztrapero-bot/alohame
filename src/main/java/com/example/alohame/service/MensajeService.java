package com.example.alohame.service;

import com.example.alohame.dao.MensajeDAO;
import com.example.alohame.model.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensajeService {

    @Autowired
    private MensajeDAO mensajeDAO;

    public void guardar(Mensaje mensaje) {
        mensajeDAO.guardar(mensaje);
    }
}