package com.example.alohame.controller;

import com.example.alohame.dao.ComentarioDAO;
import com.example.alohame.dao.ReservaDAO;
import com.example.alohame.dao.UsuarioDAO;
import com.example.alohame.dao.PropiedadDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlohameController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private PropiedadDAO propiedadDAO;

    // 👉 HOME
    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    // 👉 USUARIOS
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioDAO.listarUsuarios());
        return "usuarios";
    }

    // 👉 PROPIEDADES
    @GetMapping("/propiedades")
    public String listarPropiedades(Model model) {
        model.addAttribute("propiedades", propiedadDAO.listarPropiedades());
        return "propiedades";
    }

    // 👉 RESERVAS
    @Autowired
    private ReservaDAO reservaDAO;

    @GetMapping("/reservas")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaDAO.listarReservas());
        return "reservas";
    }

    // 👉 COMENTARIOS
    @Autowired
    private ComentarioDAO comentarioDAO;

    @GetMapping("/comentarios")
    public String listarComentarios(Model model) {
        model.addAttribute("comentarios", comentarioDAO.listarComentarios());
        return "comentarios";
    }

    // AÑADIR PROPIEDAD
    @GetMapping("/crearPropiedad")
    public String mostrarFormulario() {
        return "crearPropiedad";
    }

    @PostMapping("/guardarPropiedad")
    public String guardarPropiedad(
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam double precio,
            @RequestParam String ubicacion,
            @RequestParam int capacidad,
            @RequestParam int id_usuario) {

        propiedadDAO.guardarPropiedad(titulo, descripcion, precio, ubicacion, capacidad, id_usuario);
        return "redirect:/propiedades";
    }

    // AÑADIR USUARIO
    @GetMapping("/crearUsuario")
    public String mostrarFormularioUsuario() {
        return "crearUsuario";
    }

    @PostMapping("guardarUsuario")
    public String guardarUsuario(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam int password,
            @RequestParam int telefono,
            @RequestParam String tipo_usuario) {

        usuarioDAO.guardarUsuario(nombre, email, password, telefono, tipo_usuario);
        return "redirect:/usuarios";
    }

    // AÑADIR RESERVA
    @GetMapping("/crearReserva")
    public String mostrarFormularioReserva() {
        return "crearReserva";
    }

    @PostMapping("/guardarReserva")
    public String guardarReserva(
            @RequestParam int id_usuario,
            @RequestParam int id_propiedad,
            @RequestParam String fecha_inicio,
            @RequestParam String fecha_fin) {

        reservaDAO.guardarReserva(id_usuario, id_propiedad, fecha_inicio, fecha_fin);
        return "redirect:/reservas";
    }

    // AÑADIR COMENTARIO
    @GetMapping("/crearComentario")
    public String mostrarFormularioComentario() {
        return "crearComentario";
    }

    @PostMapping("/guardarComentario")
    public String guardarComentario(
            @RequestParam int id_usuario,
            @RequestParam int id_propiedad,
            @RequestParam String comentario,
            @RequestParam int puntuacion) {

        comentarioDAO.guardarComentario(id_usuario, id_propiedad, comentario, puntuacion);
        return "redirect:/comentarios";
    }

    }
