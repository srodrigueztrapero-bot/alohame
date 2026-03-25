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

    public List<Map<String, Object>> listarReservas() {
        String sql = "SELECT * FROM reservas";
        return jdbcTemplate.queryForList(sql);
    }
    public void guardarReserva(int idUsuario, int idPropiedad, String fechaInicio, String fechaFin) {
        String sql = "INSERT INTO reservas (id_usuario, id_propiedad, fecha_inicio, fecha_fin, precio_total, estado) VALUES (?, ?, ?, ?, 0, 'confirmada')";
        jdbcTemplate.update(sql, idUsuario, idPropiedad, fechaInicio, fechaFin);
    }
}