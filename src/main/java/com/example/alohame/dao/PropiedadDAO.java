package com.example.alohame.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PropiedadDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> listarPropiedades() {
        String sql = "SELECT * FROM propiedades";
        return jdbcTemplate.queryForList(sql);
    }
    public void guardarPropiedad(String titulo, String descripcion, double precio, String ubicacion, int capacidad, int idUsuario) {
        String sql = "INSERT INTO propiedades (titulo, descripcion, precio_noche, ubicacion, capacidad, id_usuario, fecha_publicacion) VALUES (?, ?, ?, ?, ?, ?, NOW())";
        jdbcTemplate.update(sql, titulo, descripcion, precio, ubicacion, capacidad, idUsuario);
    }
    public List<Map<String, Object>> listarPropiedadesConImagen() {

        String sql = "SELECT p.id, p.titulo, p.ubicacion, p.precio_noche, p.capacidad, i.url " +
                "FROM propiedades p " +
                "LEFT JOIN imagenes i ON p.id = i.id_propiedad " +
                "GROUP BY p.id";

        return jdbcTemplate.queryForList(sql);
    }
    public Map<String, Object> obtenerPorId(int id) {

        String sql = "SELECT * FROM propiedades WHERE id = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }
}
