package com.example.alohame.service;

import com.example.alohame.dao.ComentarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioDAO comentarioDAO;

    public List<Map<String, Object>> listarComentarios() {
        return comentarioDAO.listarComentarios();
    }

    public List<Map<String, Object>> obtenerComentariosPorPropiedad(int idPropiedad) {
        return comentarioDAO.obtenerPorPropiedad(idPropiedad);
    }

    public void guardarComentario(int idUsuario, int idPropiedad, String comentario, int puntuacion) {
        comentarioDAO.guardarComentario(idUsuario, idPropiedad, comentario, puntuacion);
    }
}

