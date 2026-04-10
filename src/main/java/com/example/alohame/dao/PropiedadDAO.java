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

    // 🔹 LISTAR TODAS LAS PROPIEDADES
    public List<Map<String, Object>> listarPropiedades() {
        String sql = "SELECT * FROM propiedades";
        return jdbcTemplate.queryForList(sql);
    }

    // 🔹 LISTAR CON IMAGEN
    public List<Map<String, Object>> listarPropiedadesConImagen() {

        String sql = "SELECT p.id, p.titulo, p.ubicacion, p.precio_noche, p.capacidad, " +
                "(SELECT CASE WHEN i.url LIKE 'images/%' THEN i.url ELSE CONCAT('images/', i.url) END FROM imagenes i WHERE i.id_propiedad = p.id LIMIT 1) as url " +
                "FROM propiedades p";

        return jdbcTemplate.queryForList(sql);
    }

    // 🔹 GUARDAR PROPIEDAD
    public void guardarPropiedad(String titulo, String descripcion, double precio, String ubicacion, int capacidad, int idUsuario) {
        String sql = "INSERT INTO propiedades (titulo, descripcion, precio_noche, ubicacion, capacidad, id_usuario, fecha_publicacion) VALUES (?, ?, ?, ?, ?, ?, NOW())";
        jdbcTemplate.update(sql, titulo, descripcion, precio, ubicacion, capacidad, idUsuario);
    }

    // 🔹 OBTENER POR ID
    public Map<String, Object> obtenerPorId(int id) {
        String sql = "SELECT * FROM propiedades WHERE id = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }
    // 🔹 BUSCAR POR CIUDAD
    public List<Map<String, Object>> buscarPorCiudad(String ciudad) {

        String sql = "SELECT p.id, p.titulo, p.ubicacion, p.precio_noche, " +
                "(SELECT CASE WHEN i.url LIKE 'images/%' THEN i.url ELSE CONCAT('images/', i.url) END FROM imagenes i WHERE i.id_propiedad = p.id LIMIT 1) as url " +
                "FROM propiedades p " +
                "WHERE p.ubicacion LIKE ?";

        return jdbcTemplate.queryForList(sql, "%" + ciudad + "%");
    }
    // FILTRAR POR PROPIETARIO
    public List<Map<String, Object>> obtenerPorUsuario(int idUsuario) {

        String sql = "SELECT p.id, p.titulo, p.ubicacion, p.precio_noche, " +
                "(SELECT CASE WHEN i.url LIKE 'images/%' THEN i.url ELSE CONCAT('images/', i.url) END FROM imagenes i WHERE i.id_propiedad = p.id LIMIT 1) as url " +
                "FROM propiedades p " +
                "WHERE p.id_usuario = ?";

        return jdbcTemplate.queryForList(sql, idUsuario);
    }
    public int guardarPropiedadYDevolverId(String titulo, String descripcion, double precio,
                                           String ubicacion, int capacidad, int idUsuario) {

        String sql = "INSERT INTO propiedades (titulo, descripcion, precio_noche, ubicacion, capacidad, id_usuario, fecha_publicacion) VALUES (?, ?, ?, ?, ?, ?, NOW())";

        jdbcTemplate.update(sql, titulo, descripcion, precio, ubicacion, capacidad, idUsuario);

        String sqlId = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(sqlId, Integer.class);
    }
    // ACTUALIZAR PROPIEDAD
    public void actualizarPropiedad(int id, String titulo, String descripcion,
                                    double precio, String ubicacion, int capacidad) {

        String sql = "UPDATE propiedades SET titulo=?, descripcion=?, precio_noche=?, ubicacion=?, capacidad=? WHERE id=?";

        jdbcTemplate.update(sql, titulo, descripcion, precio, ubicacion, capacidad, id);
    }
    // ELIMINAR PROPIEDAD
    public void eliminarPropiedad(int id) {

        // 🔥 primero imágenes
        String sqlImg = "DELETE FROM imagenes WHERE id_propiedad = ?";
        jdbcTemplate.update(sqlImg, id);

        // 🔥 luego propiedad
        String sqlProp = "DELETE FROM propiedades WHERE id = ?";
        jdbcTemplate.update(sqlProp, id);
    }
    // PUNTUACION
    public List<Map<String, Object>> listarPropiedadesConMedia() {
        String sql = "SELECT p.*, AVG(c.puntuacion) AS media_puntuacion " +
                "FROM propiedades p " +
                "LEFT JOIN comentarios c ON p.id = c.id_propiedad " +
                "GROUP BY p.id";

        return jdbcTemplate.queryForList(sql);
    }

    // 🗺️ LISTAR PROPIEDADES CON COORDENADAS (PARA MAPA)
    public List<Map<String, Object>> listarPropiedadesConCoordenadas() {
        String sql = """
                SELECT id,
                       titulo,
                       ubicacion,
                       precio_noche,
                       CASE LOWER(TRIM(ubicacion))
                           WHEN 'valencia' THEN 39.4699
                           WHEN 'asturias' THEN 43.3614
                           WHEN 'madrid' THEN 40.4168
                           WHEN 'barcelona' THEN 41.3851
                           WHEN 'salamanca' THEN 40.9658
                           WHEN 'malaga' THEN 36.7213
                           WHEN 'málaga' THEN 36.7213
                           WHEN 'badajoz' THEN 38.8794
                           WHEN 'galicia' THEN 42.8782
                           WHEN 'ávila' THEN 40.6565
                           WHEN 'avila' THEN 40.6565
                           WHEN 'segovia' THEN 40.9429
                           WHEN 'sevilla' THEN 37.3891
                           WHEN 'mallorca' THEN 39.6953
                           WHEN 'mallora' THEN 39.6953
                           WHEN 'tenerife' THEN 28.2916
                           WHEN 'vitoria' THEN 42.8467
                           WHEN 'vitoria-gasteiz' THEN 42.8467
                           WHEN 'gasteiz' THEN 42.8467
                           ELSE NULL
                       END AS latitud,
                       CASE LOWER(TRIM(ubicacion))
                           WHEN 'valencia' THEN -0.3763
                           WHEN 'asturias' THEN -5.8494
                           WHEN 'madrid' THEN -3.7038
                           WHEN 'barcelona' THEN 2.1734
                           WHEN 'salamanca' THEN -5.6640
                           WHEN 'malaga' THEN -4.4214
                           WHEN 'málaga' THEN -4.4214
                           WHEN 'badajoz' THEN -6.9707
                           WHEN 'galicia' THEN -8.5448
                           WHEN 'ávila' THEN -4.7000
                           WHEN 'avila' THEN -4.7000
                           WHEN 'segovia' THEN -4.1088
                           WHEN 'sevilla' THEN -5.9845
                           WHEN 'mallorca' THEN 2.9608
                           WHEN 'mallora' THEN 2.9608
                           WHEN 'tenerife' THEN -16.6291
                           WHEN 'vitoria' THEN -2.6727
                           WHEN 'vitoria-gasteiz' THEN -2.6727
                           WHEN 'gasteiz' THEN -2.6727
                           ELSE NULL
                       END AS longitud
                FROM propiedades
                ORDER BY id
                """;
        return jdbcTemplate.queryForList(sql);
    }
}
