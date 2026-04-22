package com.example.alohame.dao;

import com.example.alohame.model.Mensaje;
import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void inicializarTablaMensaje() {
        String sql = """
                CREATE TABLE IF NOT EXISTS mensaje (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    contenido TEXT NOT NULL,
                    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    propiedad_id INT NOT NULL,
                    id_usuario INT DEFAULT NULL,
                    id_mensaje_padre BIGINT DEFAULT NULL,
                    PRIMARY KEY (id),
                    KEY idx_mensaje_propiedad (propiedad_id),
                    CONSTRAINT fk_mensaje_propiedad
                        FOREIGN KEY (propiedad_id) REFERENCES propiedades(id)
                        ON DELETE CASCADE,
                    CONSTRAINT fk_mensaje_usuario
                        FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
                    CONSTRAINT fk_mensaje_padre
                        FOREIGN KEY (id_mensaje_padre) REFERENCES mensaje(id)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
                """;
        jdbcTemplate.execute(sql);

        // Migrar columnas si la tabla ya existía con la estructura antigua
        try {
            jdbcTemplate.execute("ALTER TABLE mensaje ADD COLUMN id_usuario INT DEFAULT NULL");
        } catch (Exception ignored) {}
        try {
            jdbcTemplate.execute("ALTER TABLE mensaje ADD COLUMN id_mensaje_padre BIGINT DEFAULT NULL");
        } catch (Exception ignored) {}
        try {
            jdbcTemplate.execute("ALTER TABLE mensaje DROP COLUMN respuesta");
        } catch (Exception ignored) {}
        try {
            jdbcTemplate.execute("ALTER TABLE mensaje DROP COLUMN fecha_respuesta");
        } catch (Exception ignored) {}
        try {
            jdbcTemplate.execute("ALTER TABLE mensaje ADD CONSTRAINT fk_mensaje_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id)");
        } catch (Exception ignored) {}
        try {
            jdbcTemplate.execute("ALTER TABLE mensaje ADD CONSTRAINT fk_mensaje_padre FOREIGN KEY (id_mensaje_padre) REFERENCES mensaje(id)");
        } catch (Exception ignored) {}
    }

    public void guardar(Mensaje mensaje) {
        String sql = "INSERT INTO mensaje (contenido, fecha, propiedad_id, id_usuario, id_mensaje_padre) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                mensaje.getContenido(),
                Timestamp.valueOf(mensaje.getFecha()),
                mensaje.getPropiedadId(),
                mensaje.getIdUsuario(),
                mensaje.getIdMensajePadre()
        );
    }

    public void responder(Long mensajePadreId, String contenido, Integer idUsuario) {
        Long propiedadId = jdbcTemplate.queryForObject(
                "SELECT propiedad_id FROM mensaje WHERE id = ?", Long.class, mensajePadreId);
        String sql = "INSERT INTO mensaje (contenido, fecha, propiedad_id, id_usuario, id_mensaje_padre) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, contenido, Timestamp.valueOf(java.time.LocalDateTime.now()),
                propiedadId, idUsuario, mensajePadreId);
    }

    public List<Mensaje> obtenerPorPropiedad(Long propiedadId) {
        String sql = "SELECT id, contenido, fecha, propiedad_id, id_usuario, id_mensaje_padre FROM mensaje WHERE propiedad_id = ? ORDER BY fecha DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapMensajeBase(rs), propiedadId);
    }

    public List<Mensaje> obtenerPorPropietario(Long idPropietario) {
        String sql = """
                SELECT m.id,
                       m.contenido,
                       m.fecha,
                       m.propiedad_id,
                       m.id_usuario,
                       m.id_mensaje_padre,
                       p.titulo AS propiedad_titulo
                FROM mensaje m
                INNER JOIN propiedades p ON p.id = m.propiedad_id
                WHERE p.id_usuario = ?
                ORDER BY m.fecha DESC
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapMensajeConPropiedad(rs), idPropietario);
    }

    public List<Mensaje> obtenerPorPropietarioYPropiedad(Long idPropietario, Long idPropiedad) {
        String sql = """
                SELECT m.id,
                       m.contenido,
                       m.fecha,
                       m.propiedad_id,
                       m.id_usuario,
                       m.id_mensaje_padre,
                       p.titulo AS propiedad_titulo
                FROM mensaje m
                INNER JOIN propiedades p ON p.id = m.propiedad_id
                WHERE p.id_usuario = ? AND p.id = ?
                ORDER BY m.fecha DESC
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapMensajeConPropiedad(rs), idPropietario, idPropiedad);
    }

    private Mensaje mapMensajeConPropiedad(java.sql.ResultSet rs) throws java.sql.SQLException {
        Mensaje m = mapMensajeBase(rs);
        m.setPropiedadTitulo(rs.getString("propiedad_titulo"));
        return m;
    }

    private Mensaje mapMensajeBase(java.sql.ResultSet rs) throws java.sql.SQLException {
        Mensaje m = new Mensaje();
        m.setId(rs.getLong("id"));
        m.setContenido(rs.getString("contenido"));
        m.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        m.setPropiedadId(rs.getLong("propiedad_id"));
        int idUsuario = rs.getInt("id_usuario");
        if (!rs.wasNull()) m.setIdUsuario(idUsuario);
        long idPadre = rs.getLong("id_mensaje_padre");
        if (!rs.wasNull()) m.setIdMensajePadre(idPadre);
        return m;
    }
}