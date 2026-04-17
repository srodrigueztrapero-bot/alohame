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
                    respuesta TEXT DEFAULT NULL,
                    fecha_respuesta DATETIME DEFAULT NULL,
                    PRIMARY KEY (id),
                    KEY idx_mensaje_propiedad (propiedad_id),
                    CONSTRAINT fk_mensaje_propiedad
                        FOREIGN KEY (propiedad_id) REFERENCES propiedades(id)
                        ON DELETE CASCADE
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
                """;
        jdbcTemplate.execute(sql);

        // Añadir columnas si la tabla ya existía sin ellas
        try {
            jdbcTemplate.execute("ALTER TABLE mensaje ADD COLUMN respuesta TEXT DEFAULT NULL");
        } catch (Exception ignored) {}
        try {
            jdbcTemplate.execute("ALTER TABLE mensaje ADD COLUMN fecha_respuesta DATETIME DEFAULT NULL");
        } catch (Exception ignored) {}
    }

    public void guardar(Mensaje mensaje) {
        String sql = "INSERT INTO mensaje (contenido, fecha, propiedad_id) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                mensaje.getContenido(),
                Timestamp.valueOf(mensaje.getFecha()),
                mensaje.getPropiedadId()
        );
    }

    public void responder(Long id, String respuesta) {
        String sql = "UPDATE mensaje SET respuesta = ?, fecha_respuesta = ? WHERE id = ?";
        jdbcTemplate.update(sql, respuesta, Timestamp.valueOf(java.time.LocalDateTime.now()), id);
    }

    public List<Mensaje> obtenerPorPropiedad(Long propiedadId) {
        String sql = "SELECT id, contenido, fecha, propiedad_id, respuesta, fecha_respuesta FROM mensaje WHERE propiedad_id = ? ORDER BY fecha DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapMensajeBase(rs), propiedadId);
    }

    public List<Mensaje> obtenerPorPropietario(Long idPropietario) {
        String sql = """
                SELECT m.id,
                       m.contenido,
                       m.fecha,
                       m.propiedad_id,
                       m.respuesta,
                       m.fecha_respuesta,
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
                       m.respuesta,
                       m.fecha_respuesta,
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
        m.setRespuesta(rs.getString("respuesta"));
        Timestamp tr = rs.getTimestamp("fecha_respuesta");
        if (tr != null) m.setFechaRespuesta(tr.toLocalDateTime());
        return m;
    }
}