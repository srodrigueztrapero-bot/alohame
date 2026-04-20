package com.example.alohame.dao;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ComodidadDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void inicializarTablas() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS comodidad (
                    id   BIGINT NOT NULL AUTO_INCREMENT,
                    nombre VARCHAR(100) NOT NULL,
                    icono  VARCHAR(80)  NOT NULL,
                    PRIMARY KEY (id)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS propiedad_comodidad (
                    propiedad_id INT    NOT NULL,
                    comodidad_id BIGINT NOT NULL,
                    PRIMARY KEY (propiedad_id, comodidad_id),
                    CONSTRAINT fk_pc_propiedad FOREIGN KEY (propiedad_id) REFERENCES propiedades(id) ON DELETE CASCADE,
                    CONSTRAINT fk_pc_comodidad FOREIGN KEY (comodidad_id) REFERENCES comodidad(id)   ON DELETE CASCADE
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
                """);

        // Insertar catálogo inicial solo si la tabla está vacía
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM comodidad", Integer.class);
        if (count != null && count == 0) {
            String sql = "INSERT INTO comodidad (nombre, icono) VALUES (?, ?)";
            jdbcTemplate.update(sql, "WiFi",                "bi-wifi");
            jdbcTemplate.update(sql, "TV",                  "bi-tv");
            jdbcTemplate.update(sql, "Aire acondicionado",  "bi-thermometer-snow");
            jdbcTemplate.update(sql, "Calefacción",         "bi-fire");
            jdbcTemplate.update(sql, "Cocina equipada",     "bi-egg-fried");
            jdbcTemplate.update(sql, "Lavadora",            "bi-recycle");
            jdbcTemplate.update(sql, "Secador de pelo",     "bi-wind");
            jdbcTemplate.update(sql, "Lavavajillas",        "bi-droplet-half");
            jdbcTemplate.update(sql, "Parking",             "bi-p-square-fill");
            jdbcTemplate.update(sql, "Piscina",             "bi-water");
            jdbcTemplate.update(sql, "Terraza",             "bi-tree");
            jdbcTemplate.update(sql, "Ascensor",            "bi-arrow-up-circle");
            jdbcTemplate.update(sql, "Mascotas permitidas", "bi-heart");
            jdbcTemplate.update(sql, "Bicicletas",          "bi-bicycle");
            jdbcTemplate.update(sql, "Caja fuerte",         "bi-shield-lock");
            jdbcTemplate.update(sql, "Cafetera",            "bi-cup-hot");
        }
    }

    public List<Map<String, Object>> obtenerTodas() {
        return jdbcTemplate.queryForList("SELECT id, nombre, icono FROM comodidad ORDER BY id");
    }

    public List<Map<String, Object>> obtenerPorPropiedad(int propiedadId) {
        String sql = """
                SELECT c.id, c.nombre, c.icono
                FROM comodidad c
                INNER JOIN propiedad_comodidad pc ON pc.comodidad_id = c.id
                WHERE pc.propiedad_id = ?
                ORDER BY c.id
                """;
        return jdbcTemplate.queryForList(sql, propiedadId);
    }

    public void guardarParaPropiedad(int propiedadId, List<Long> comodidadIds) {
        jdbcTemplate.update("DELETE FROM propiedad_comodidad WHERE propiedad_id = ?", propiedadId);
        if (comodidadIds == null || comodidadIds.isEmpty()) return;
        for (Long cid : comodidadIds) {
            jdbcTemplate.update("INSERT INTO propiedad_comodidad (propiedad_id, comodidad_id) VALUES (?, ?)",
                    propiedadId, cid);
        }
    }
}