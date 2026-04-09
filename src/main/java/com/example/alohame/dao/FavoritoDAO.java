package com.example.alohame.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class FavoritoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // AGREGAR FAVORITO
    public void agregarFavorito(int idUsuario, int idPropiedad) {
        String sql = """
                INSERT INTO favoritos (id_usuario, id_propiedad)
                VALUES (?, ?)
                """;
        try {
            jdbcTemplate.update(sql, idUsuario, idPropiedad);
        } catch (Exception e) {
            // Si ya existe, no hacer nada
        }
    }

    // ELIMINAR FAVORITO
    public void eliminarFavorito(int idUsuario, int idPropiedad) {
        String sql = """
                DELETE FROM favoritos
                WHERE id_usuario = ? AND id_propiedad = ?
                """;
        jdbcTemplate.update(sql, idUsuario, idPropiedad);
    }

    // VERIFICAR SI ES FAVORITO
    public boolean esFavorito(int idUsuario, int idPropiedad) {
        String sql = """
                SELECT COUNT(*)
                FROM favoritos
                WHERE id_usuario = ? AND id_propiedad = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idUsuario, idPropiedad);
        return count != null && count > 0;
    }

    // LISTAR FAVORITOS DE UN USUARIO
    public List<Map<String, Object>> obtenerFavoritosPorUsuario(int idUsuario) {
        String sql = """
                SELECT p.id,
                       p.titulo,
                       p.ubicacion,
                       p.precio_noche,
                       p.capacidad,
                       (SELECT url FROM imagenes i WHERE i.id_propiedad = p.id LIMIT 1) as url
                FROM favoritos f
                JOIN propiedades p ON f.id_propiedad = p.id
                WHERE f.id_usuario = ?
                ORDER BY f.id DESC
                """;
        return jdbcTemplate.queryForList(sql, idUsuario);
    }

    // CONTAR FAVORITOS DE UN USUARIO
    public int contarFavoritos(int idUsuario) {
        String sql = """
                SELECT COUNT(*)
                FROM favoritos
                WHERE id_usuario = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idUsuario);
        return count != null ? count : 0;
    }
}
