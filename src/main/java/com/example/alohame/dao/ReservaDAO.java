package com.example.alohame.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ReservaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // LISTAR RESERVAS
    public List<Map<String, Object>> listarReservas() {
        String sql = """
                SELECT r.id,
                       r.id_usuario,
                       r.id_propiedad,
                       r.fecha_inicio,
                       r.fecha_fin,
                       r.estado,
                       u.nombre as usuario_nombre,
                       u.email as usuario_email,
                       p.titulo as propiedad_titulo,
                       p.ubicacion,
                       p.precio_noche,
                       (SELECT CASE WHEN i.url LIKE 'images/%' THEN i.url ELSE CONCAT('images/', i.url) END FROM imagenes i WHERE i.id_propiedad = p.id LIMIT 1) as url
                FROM reservas r
                JOIN usuarios u ON r.id_usuario = u.id
                JOIN propiedades p ON r.id_propiedad = p.id
                ORDER BY r.fecha_inicio DESC
                """;
        return jdbcTemplate.queryForList(sql);
    }

    // GUARDAR RESERVA
    public void guardarReserva(int idUsuario, int idPropiedad, String fechaInicio, String fechaFin) {
        String sql = """
                INSERT INTO reservas
                    (id_usuario, id_propiedad, fecha_inicio, fecha_fin, estado)
                VALUES
                    (?, ?, ?, ?, 'confirmada')
                """;

        jdbcTemplate.update(sql, idUsuario, idPropiedad, fechaInicio, fechaFin);
    }

    // RESERVAS POR USUARIO
    public List<Map<String, Object>> obtenerPorUsuario(int idUsuario) {
        String sql = """
                SELECT r.id,
                       r.id_propiedad,
                       r.fecha_inicio,
                       r.fecha_fin,
                       r.estado,
                       p.id,
                       p.titulo,
                       p.descripcion,
                       p.ubicacion,
                       p.precio_noche,
                       p.capacidad,
                       (SELECT CASE WHEN i.url LIKE 'images/%' THEN i.url ELSE CONCAT('images/', i.url) END FROM imagenes i WHERE i.id_propiedad = p.id LIMIT 1) as url
                FROM reservas r
                JOIN propiedades p ON r.id_propiedad = p.id
                WHERE r.id_usuario = ?
                ORDER BY r.fecha_inicio DESC
                """;

        return jdbcTemplate.queryForList(sql, idUsuario);
    }

    // NO SOLAPAR RESERVAS
    public boolean estaDisponible(int idPropiedad, String inicio, String fin) {
        String sql = """
                SELECT COUNT(*)
                FROM reservas
                WHERE id_propiedad = ?
                  AND estado != 'cancelada'
                  AND (? <= fecha_fin AND ? >= fecha_inicio)
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                idPropiedad,
                inicio,
                fin
        );

        return count == null || count == 0;
    }

    // CANCELAR RESERVA
    public void cancelarReserva(int idReserva) {
        String sql = """
                UPDATE reservas
                SET estado = 'cancelada'
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, idReserva);
    }
}