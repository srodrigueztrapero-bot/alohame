package com.example.alohame.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> listarUsuarios() {
        String sql = "SELECT * FROM usuarios";
        return jdbcTemplate.queryForList(sql);
    }
    public void guardarUsuario(String nombre, String email, int password, int telefono, String tipo) {
        String sql = "INSERT INTO usuarios (nombre, email, password, telefono, fecha_registro, tipo_usuario) VALUES (?, ?, ?, ?, NOW(), ?)";
        jdbcTemplate.update(sql, nombre, email, password, telefono, tipo);
    }

    }

