package com.example.alohame.dao;

import com.example.alohame.model.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class MensajeDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MensajeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public  void guardar(Mensaje mensaje) {
        String sql = "INSERT INTO mensaje (contenido, fecha, propiedad_id) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                mensaje.getContenido(),
                Timestamp.valueOf(mensaje.getFecha())
        );
    }
    public List<Mensaje> obtenerPorPropiedad(Long propiedadId) {
        String sql = "SELECT * FROM mensaje WHERE propiedad_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Mensaje m = new Mensaje();
            m.setId(rs.getLong("id"));
            m.setContenido(rs.getString("contenido"));
            m.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
            m.setPropiedadId(rs.getLong("propiedad_id"));
            return m;
        }, propiedadId);
    }
}