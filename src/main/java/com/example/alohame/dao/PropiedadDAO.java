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

    public List<Map<String, Object>> listarPropiedades() {
        String sql = "SELECT * FROM propiedades";
        return jdbcTemplate.queryForList(sql);
    }
}
