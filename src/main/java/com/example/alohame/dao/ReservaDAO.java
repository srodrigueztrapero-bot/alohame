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
        String sql = "SELECT * FROM reservas";
        return jdbcTemplate.queryForList(sql);
    }
    // GUARDAR RESERVA
    public void guardarReserva(int idUsuario, int idPropiedad, String fechaInicio, String fechaFin) {
        String sql = "INSERT INTO reservas (id_usuario, id_propiedad, fecha_inicio, fecha_fin, precio_total, estado) VALUES (?, ?, ?, ?, 0, 'confirmada')";
        jdbcTemplate.update(sql, idUsuario, idPropiedad, fechaInicio, fechaFin);
    }
    // RESERVA POR USUARIO
    public List<Map<String, Object>> obtenerPorUsuario(int idUsuario) {

        String sql = "SELECT r.id, r.fecha_inicio, r.fecha_fin, r.estado, " +
                "p.titulo, p.ubicacion " +
                "FROM reservas r " +
                "JOIN propiedades p ON r.id_propiedad = p.id " +
                "WHERE r.id_usuario = ?";

        return jdbcTemplate.queryForList(sql, idUsuario);
    }
}