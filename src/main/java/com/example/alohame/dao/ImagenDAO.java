package com.example.alohame.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ImagenDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> obtenerPorPropiedad(int idPropiedad) {

        String sql = "SELECT * FROM imagenes WHERE id_propiedad = ?";
        return jdbcTemplate.queryForList(sql, idPropiedad);
    }
}
