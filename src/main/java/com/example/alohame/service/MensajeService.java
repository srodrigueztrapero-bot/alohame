package com.example.alohame.service;

import com.example.alohame.dao.MensajeDAO;
import com.example.alohame.model.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensajeService {

    @Autowired
    private MensajeDAO mensajeDAO;

    public void guardar(Mensaje mensaje) {
        mensajeDAO.guardar(mensaje);
    }

    public List<Mensaje> obtenerPorPropietario(Long idPropietario) {
        return mensajeDAO.obtenerPorPropietario(idPropietario);
    }

    public List<Mensaje> obtenerPorPropietarioYPropiedad(Long idPropietario, Long idPropiedad) {
        return mensajeDAO.obtenerPorPropietarioYPropiedad(idPropietario, idPropiedad);
    }

    public void responder(Long mensajePadreId, String contenido, Integer idUsuario) {
        mensajeDAO.responder(mensajePadreId, contenido, idUsuario);
    }
}