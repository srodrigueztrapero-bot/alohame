package com.example.alohame.controller;

import com.example.alohame.dao.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
       HELPERS
    ========================= */

    private Map<String, Object> getUsuarioSesion(HttpSession session) {
        return (Map<String, Object>) session.getAttribute("usuario");
    }

    private boolean esTipoUsuario(HttpSession session, String tipoEsperado) {
        Map<String, Object> usuario = getUsuarioSesion(session);
        return usuario != null
                && usuario.get("tipo_usuario") != null
                && tipoEsperado.equals(usuario.get("tipo_usuario").toString());
    }

    private Integer obtenerIdUsuario(Map<String, Object> usuario) {
        if (usuario == null || usuario.get("id") == null) {
            return null;
        }
        return Integer.parseInt(usuario.get("id").toString());
    }

    private void guardarImagenEnDisco(MultipartFile imagen, int idPropiedad) throws Exception {
        if (imagen == null || imagen.isEmpty()) {
            return;
        }

        String nombre = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
        String ruta = "src/main/resources/static/images/";

        File archivo = new File(ruta + nombre);
        imagen.transferTo(archivo);

        imagenDAO.guardarImagen(idPropiedad, "images/" + nombre);
    }

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
        if (!esTipoUsuario(session, "admin")) {
            return "redirect:/login";
        }

        return "admin";
    }

    @GetMapping("/propietario")
    public String propietario(HttpSession session, Model model) {
        if (!esTipoUsuario(session, "propietario")) {
            return "redirect:/login";
        }

        Map<String, Object> usuario = getUsuarioSesion(session);
        Integer idUsuario = obtenerIdUsuario(usuario);

        if (idUsuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("propiedades", propiedadDAO.obtenerPorUsuario(idUsuario));
        return "propietario";
    }

    @GetMapping("/cliente")
    public String cliente(HttpSession session, Model model) {
        Map<String, Object> usuario = getUsuarioSesion(session);

        if (usuario == null) {
            return "redirect:/login";
        }

        Integer idUsuario = obtenerIdUsuario(usuario);
        if (idUsuario == null) {
            return "redirect:/login";
        }

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
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "crearUsuario";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@RequestParam String nombre,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam String telefono,
                                 @RequestParam String tipo_usuario) {

        usuarioDAO.guardarUsuario(nombre, email, password, telefono, tipo_usuario);
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
            Map<String, Object> usuario = getUsuarioSesion(session);
            if (usuario == null) return "redirect:/login";

            Integer idUsuario = obtenerIdUsuario(usuario);
            if (idUsuario == null) return "redirect:/login";

            int idPropiedad = propiedadDAO.guardarPropiedadYDevolverId(
                    titulo, descripcion, precio, ubicacion, capacidad, idUsuario
            );

            guardarImagenEnDisco(imagen, idPropiedad);

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
                guardarImagenEnDisco(img, id);
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
    public String mostrarFormularioReserva(@RequestParam(required = false) Integer id_propiedad,
                                           HttpSession session,
                                           Model model) {

        Map<String, Object> usuario = getUsuarioSesion(session);

        if (usuario == null) {
            return "redirect:/login";
        }

        Integer idUsuario = obtenerIdUsuario(usuario);
        if (idUsuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("id_usuario", idUsuario);
        model.addAttribute("id_propiedad", id_propiedad);

        return "crearReserva";
    }

    // ... existing code ...

    @PostMapping("/guardarReserva")
    public String guardarReserva(@RequestParam int id_usuario,
                                 @RequestParam int id_propiedad,
                                 @RequestParam String fecha_inicio,
                                 @RequestParam String fecha_fin,
                                 HttpSession session,
                                 Model model) {

        Map<String, Object> usuario = getUsuarioSesion(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        Integer idUsuarioSesion = obtenerIdUsuario(usuario);
        if (idUsuarioSesion == null) {
            return "redirect:/login";
        }

        if (idUsuarioSesion != id_usuario) {
            return "redirect:/login";
        }

        if (!reservaDAO.estaDisponible(id_propiedad, fecha_inicio, fecha_fin)) {
            model.addAttribute("error", "❌ Ya está reservada en esas fechas");
            model.addAttribute("id_usuario", id_usuario);
            model.addAttribute("id_propiedad", id_propiedad);
            return "crearReserva";
        }

        reservaDAO.guardarReserva(id_usuario, id_propiedad, fecha_inicio, fecha_fin);
        return "redirect:/propiedad/" + id_propiedad;
    }

// ... existing code ...

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