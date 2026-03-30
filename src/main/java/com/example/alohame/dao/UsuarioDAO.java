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

    public Map<String, Object> login(String email, String password) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";

        List<Map<String, Object>> lista = jdbcTemplate.queryForList(sql, email, password);

        if(lista.isEmpty()){
            return null;
        } else {
            return lista.get(0);
        }
    }

    public List<Map<String, Object>> listarUsuarios() {
        String sql = "SELECT u.*, t.nombre AS tipo " +
                "FROM usuarios u " +
                "JOIN tipos_usuario t ON u.id_tipo = t.id";;
        return jdbcTemplate.queryForList(sql);
    }
    public void guardarUsuario(String nombre, String email, String password, String telefono, int idTipo) {
        String sql = "INSERT INTO usuarios (nombre, email, password, telefono, id_tipo) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, nombre, email, password, telefono, idTipo);
    }

    public List<Map<String, Object>> listarPropietarios() {

        String sql = "SELECT * FROM usuarios WHERE idtipo = 2";
        return jdbcTemplate.queryForList(sql);
    }

    }

