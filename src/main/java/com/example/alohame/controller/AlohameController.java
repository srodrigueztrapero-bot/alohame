package com.example.alohame.controller;

import com.example.alohame.dao.ComentarioDAO;
import com.example.alohame.dao.ReservaDAO;
import com.example.alohame.dao.UsuarioDAO;
import com.example.alohame.dao.PropiedadDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}