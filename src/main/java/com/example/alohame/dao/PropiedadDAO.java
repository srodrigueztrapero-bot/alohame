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
                "(SELECT url FROM imagenes i WHERE i.id_propiedad = p.id LIMIT 1) as url " +
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
                "(SELECT url FROM imagenes i WHERE i.id_propiedad = p.id LIMIT 1) as url " +
                "FROM propiedades p " +
                "WHERE p.ubicacion LIKE ?";

        return jdbcTemplate.queryForList(sql, "%" + ciudad + "%");
    }
    // FILTRAR POR PROPIETARIO
    public List<Map<String, Object>> obtenerPorUsuario(int idUsuario) {

        String sql = "SELECT p.id, p.titulo, p.ubicacion, p.precio_noche, " +
                "(SELECT url FROM imagenes i WHERE i.id_propiedad = p.id LIMIT 1) as url " +
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
}
