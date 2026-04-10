package com.example.alohame.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ImagenDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> obtenerPorPropiedad(int idPropiedad) {

        String sql = """
                SELECT id,
                       id_propiedad,
                       CASE
                           WHEN url LIKE 'images/%' THEN url
                           ELSE CONCAT('images/', url)
                       END AS url
                FROM imagenes
                WHERE id_propiedad = ?
                """;
        return jdbcTemplate.queryForList(sql, idPropiedad);
    }
    
    public void guardarImagen(int idPropiedad, String url) {
        if (url == null || url.isBlank() || url.contains("://") || url.contains("/") || url.contains("\\")) {
            throw new IllegalArgumentException("La imagen debe guardarse solo como nombre de archivo");
        }
        String sql = "INSERT INTO imagenes (id_propiedad, url) VALUES (?, ?)";
        jdbcTemplate.update(sql, idPropiedad, url.trim());
    }
    
    public void eliminarPorPropiedad(int idPropiedad) {
        String sql = "DELETE FROM imagenes WHERE id_propiedad = ?";
        jdbcTemplate.update(sql, idPropiedad);
    }

    public boolean existeImagenPorNombre(String nombreArchivo) {
        String sql = """
                SELECT COUNT(*)
                FROM imagenes
                WHERE LOWER(SUBSTRING_INDEX(url, '/', -1)) = LOWER(?)
                   OR LOWER(SUBSTRING_INDEX(url, '/', -1)) LIKE LOWER(CONCAT('%_', ?))
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, nombreArchivo, nombreArchivo);
        return count != null && count > 0;
    }
}
