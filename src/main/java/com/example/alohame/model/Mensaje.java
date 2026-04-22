package com.example.alohame.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Mensaje {
    private Long id;
    private String contenido;
    private LocalDateTime fecha;
    private Long propiedadId;
    private String propiedadTitulo;
    private Integer idUsuario;
    private Long idMensajePadre;
    private List<Mensaje> respuestas = new ArrayList<>();

    public Mensaje() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getPropiedadId() {
        return propiedadId;
    }

    public void setPropiedadId(Long propiedadId) {
        this.propiedadId = propiedadId;
    }

    public String getPropiedadTitulo() {
        return propiedadTitulo;
    }

    public void setPropiedadTitulo(String propiedadTitulo) {
        this.propiedadTitulo = propiedadTitulo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdMensajePadre() {
        return idMensajePadre;
    }

    public void setIdMensajePadre(Long idMensajePadre) {
        this.idMensajePadre = idMensajePadre;
    }

    public List<Mensaje> getRespuestas() {
        return respuestas;
    }

    public void addRespuesta(Mensaje respuesta) {
        this.respuestas.add(respuesta);
    }
}