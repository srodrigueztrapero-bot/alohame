package com.example.alohame.controller;

import com.example.alohame.dao.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
public class AlohameController {

    /* =========================
       DAOs
    ========================= */

    @Autowired private UsuarioDAO usuarioDAO;
    @Autowired private PropiedadDAO propiedadDAO;
    @Autowired private ImagenDAO imagenDAO;
    @Autowired private ReservaDAO reservaDAO;
    @Autowired private ComentarioDAO comentarioDAO;

    /* =========================
       HOME
    ========================= */

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("propiedades", propiedadDAO.listarPropiedadesConImagen());
        return "index";
    }

    /* =========================
       LOGIN / LOGOUT
    ========================= */

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Map<String, Object> usuario = usuarioDAO.login(email, password);

        if (usuario == null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }

        session.setAttribute("usuario", usuario);

        String tipo = usuario.get("tipo_usuario").toString();

        if (tipo.equals("admin")) return "redirect:/admin";
        if (tipo.equals("propietario")) return "redirect:/propietario";

        return "redirect:/cliente";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    /* =========================
       ZONAS PROTEGIDAS
    ========================= */

    @GetMapping("/admin")
    public String admin(HttpSession session) {
        Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuario");

        if (usuario == null || !usuario.get("tipo_usuario").toString().equals("admin")) {
            return "redirect:/login";
        }

        return "admin";
    }

    @GetMapping("/propietario")
    public String propietario(HttpSession session, Model model) {
        Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuario");

        if (usuario == null || !usuario.get("tipo_usuario").toString().equals("propietario")) {
            return "redirect:/login";
        }

        int idUsuario = (int) usuario.get("id");
        model.addAttribute("propiedades", propiedadDAO.obtenerPorUsuario(idUsuario));

        return "propietario";
    }

    @GetMapping("/cliente")
    public String cliente(HttpSession session, Model model) {
        Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuario");

        if (usuario == null) return "redirect:/login";

        int idUsuario = Integer.parseInt(usuario.get("id").toString());
        model.addAttribute("reservas", reservaDAO.obtenerPorUsuario(idUsuario));

        return "cliente";
    }

    /* =========================
       USUARIOS
    ========================= */

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioDAO.listarUsuarios());
        return "usuarios";
    }

    @GetMapping("/crearUsuario")
    public String mostrarFormularioUsuario() {
        return "crearUsuario";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@RequestParam String nombre,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam String telefono,
                                 @RequestParam int id_tipo) {

        usuarioDAO.guardarUsuario(nombre, email, password, telefono, id_tipo);
        return "redirect:/usuarios";
    }

    /* =========================
       PROPIEDADES
    ========================= */

    @GetMapping("/propiedades")
    public String listarPropiedades(@RequestParam(required = false) String ciudad, Model model) {

        if (ciudad != null && !ciudad.isEmpty()) {
            model.addAttribute("propiedades", propiedadDAO.buscarPorCiudad(ciudad));
        } else {
            model.addAttribute("propiedades", propiedadDAO.listarPropiedadesConImagen());
        }

        return "propiedades";
    }

    @GetMapping("/propiedad/{id}")
    public String verPropiedad(@PathVariable int id, Model model) {

        model.addAttribute("propiedad", propiedadDAO.obtenerPorId(id));
        model.addAttribute("imagenes", imagenDAO.obtenerPorPropiedad(id));
        model.addAttribute("comentarios", comentarioDAO.obtenerPorPropiedad(id));
        model.addAttribute("propiedades", propiedadDAO.listarPropiedadesConMedia());

        return "detallePropiedad";
    }

    @GetMapping("/crearPropiedad")
    public String mostrarFormularioPropiedad(Model model) {
        model.addAttribute("propietarios", usuarioDAO.listarPropietarios());
        return "crearPropiedad";
    }

    @PostMapping("/guardarPropiedad")
    public String guardarPropiedad(@RequestParam String titulo,
                                   @RequestParam String descripcion,
                                   @RequestParam double precio,
                                   @RequestParam String ubicacion,
                                   @RequestParam int capacidad,
                                   @RequestParam("imagen") MultipartFile imagen,
                                   HttpSession session) {

        try {
            Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuario");
            if (usuario == null) return "redirect:/login";

            int idUsuario = Integer.parseInt(usuario.get("id").toString());

            int idPropiedad = propiedadDAO.guardarPropiedadYDevolverId(
                    titulo, descripcion, precio, ubicacion, capacidad, idUsuario
            );

            if (!imagen.isEmpty()) {
                String nombre = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
                String ruta = "src/main/resources/static/images/";

                File archivo = new File(ruta + nombre);
                imagen.transferTo(archivo);

                imagenDAO.guardarImagen(idPropiedad, "images/" + nombre);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/propietario";
    }

    @GetMapping("/editarPropiedad/{id}")
    public String editarPropiedad(@PathVariable int id, Model model) {
        model.addAttribute("propiedad", propiedadDAO.obtenerPorId(id));
        return "editarPropiedad";
    }

    @PostMapping("/actualizarPropiedad")
    public String actualizarPropiedad(@RequestParam int id,
                                      @RequestParam String titulo,
                                      @RequestParam String descripcion,
                                      @RequestParam double precio,
                                      @RequestParam String ubicacion,
                                      @RequestParam int capacidad,
                                      @RequestParam("imagenes") MultipartFile[] imagenes) {

        propiedadDAO.actualizarPropiedad(id, titulo, descripcion, precio, ubicacion, capacidad);

        try {
            for (MultipartFile img : imagenes) {
                if (!img.isEmpty()) {
                    String nombre = System.currentTimeMillis() + "_" + img.getOriginalFilename();
                    File archivo = new File("src/main/resources/static/images/" + nombre);

                    img.transferTo(archivo);
                    imagenDAO.guardarImagen(id, "images/" + nombre);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/propietario";
    }

    @GetMapping("/eliminarPropiedad/{id}")
    public String eliminarPropiedad(@PathVariable int id) {
        propiedadDAO.eliminarPropiedad(id);
        return "redirect:/propietario";
    }

    /* =========================
       RESERVAS
    ========================= */

    @GetMapping("/reservas")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaDAO.listarReservas());
        return "reservas";
    }

    @GetMapping("/crearReserva")
    public String mostrarFormularioReserva() {
        return "crearReserva";
    }

    @PostMapping("/guardarReserva")
    public String guardarReserva(@RequestParam int id_usuario,
                                 @RequestParam int id_propiedad,
                                 @RequestParam String fecha_inicio,
                                 @RequestParam String fecha_fin,
                                 Model model) {

        // validar solapamiento
        if (!reservaDAO.estaDisponible(id_propiedad, fecha_inicio, fecha_fin)) {
            model.addAttribute("error", "❌ Ya está reservada en esas fechas");
            return "crearReserva";
        }

        reservaDAO.guardarReserva(id_usuario, id_propiedad, fecha_inicio, fecha_fin);
        return "redirect:/propiedad/" + id_propiedad;
    }

    /* =========================
       COMENTARIOS
    ========================= */

    @GetMapping("/comentarios")
    public String listarComentarios(Model model) {
        model.addAttribute("comentarios", comentarioDAO.listarComentarios());
        return "comentarios";
    }

    @GetMapping("/crearComentario")
    public String mostrarFormularioComentario() {
        return "crearComentario";
    }

    @PostMapping("/guardarComentario")
    public String guardarComentario(@RequestParam int id_usuario,
                                    @RequestParam int id_propiedad,
                                    @RequestParam String comentario,
                                    @RequestParam int puntuacion) {

        comentarioDAO.guardarComentario(id_usuario, id_propiedad, comentario, puntuacion);
        return "redirect:/comentarios";
    }
}