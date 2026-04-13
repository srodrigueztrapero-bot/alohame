package com.example.alohame.service;

import com.example.alohame.dao.ComentarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Servicio de lógica de negocio para comentarios y valoraciones.
 *
 * Gestiona las operaciones relacionadas con los comentarios que los clientes
 * pueden dejar en cada propiedad, incluyendo puntuación de 1 a 5 estrellas.
 */
@Service
public class ComentarioService {

    // Inyección del DAO que ejecuta las consultas SQL sobre la tabla comentarios
    @Autowired
    private ComentarioDAO comentarioDAO;

    /**
     * Devuelve todos los comentarios del sistema.
     * Usado en el panel de administración para moderar comentarios.
     */
    public List<Map<String, Object>> listarComentarios() {
        return comentarioDAO.listarComentarios();
    }

    /**
     * Devuelve los comentarios de una propiedad concreta ordenados por fecha.
     * Se muestran en la vista de detalle de propiedad.
     */
    public List<Map<String, Object>> obtenerComentariosPorPropiedad(int idPropiedad) {
        return comentarioDAO.obtenerPorPropiedad(idPropiedad);
    }

    /**
     * Guarda un nuevo comentario con su puntuación.
     * La fecha se asigna automáticamente en la base de datos (NOW()).
     */
    public void guardarComentario(int idUsuario, int idPropiedad, String comentario, int puntuacion) {
        comentarioDAO.guardarComentario(idUsuario, idPropiedad, comentario, puntuacion);
    }
}
