package com.example.alohame.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ComentarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> listarComentarios() {
        String sql = "SELECT * FROM comentarios";
        return jdbcTemplate.queryForList(sql);
    }
}
