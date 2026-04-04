package com.example.alohame.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Repository
public class ComentarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> listarComentarios() {
        String sql = "SELECT * FROM comentarios";
        return jdbcTemplate.queryForList(sql);
    }
    public void guardarComentario(int idUsuario, int idPropiedad, String comentario, int puntuacion) {
        String sql = "INSERT INTO comentarios (id_usuario, id_propiedad, comentario, puntuacion, fecha) VALUES (?, ?, ?, ?, NOW())";
        jdbcTemplate.update(sql, idUsuario, idPropiedad, comentario, puntuacion);
    }
    public List<Map<String, Object>> obtenerPorPropiedad(int idPropiedad) {
        String sql = "SELECT * FROM comentarios WHERE id_propiedad = ?";
        return jdbcTemplate.queryForList(sql, idPropiedad);
    }
}
