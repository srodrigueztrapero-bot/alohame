package com.example.alohame.controller;

import com.example.alohame.dao.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
public class AlohameController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private PropiedadDAO propiedadDAO;

    @Autowired
    private ImagenDAO imagenDAO;

    // 👉 HOME
    @GetMapping("/")
    public String index(Model model) {

        List<Map<String, Object>> propiedades = propiedadDAO.listarPropiedadesConImagen();

        model.addAttribute("propiedades", propiedades);

        return "index";
    }

    // 👉 LOGIN
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

        if (tipo.equals("admin")) {
            return "redirect:/admin";
        } else if (tipo.equals("propietario")) {
            return "redirect:/propietario";
        } else {
            return "redirect:/cliente";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // borra la sesión
        return "redirect:/login";
    }

    // PROTEGER ADMIN
    @GetMapping("/admin")
    public String admin(HttpSession session) {

        Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.get("tipo_usuario").toString().equals("admin")) {
            return "redirect:/login";
        }

        return "admin";
    }

    // PROTEGER PROPIETARIO
    @GetMapping("/propietario")
    public String propietario(HttpSession session, Model model) {

        Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.get("tipo_usuario").toString().equals("propietario")) {
            return "redirect:/login";
        }

        int idUsuario = (int) usuario.get("id");

        model.addAttribute("propiedades", propiedadDAO.obtenerPorUsuario(idUsuario));

        return "propietario";
    }

    // PROTEGER CLIENTE
    @GetMapping("/cliente")
    public String cliente(HttpSession session, Model model) {

        Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        System.out.println("USUARIO COMPLETO: " + usuario);

        Object idObj = usuario.get("id");
        System.out.println("TIPO ID: " + idObj.getClass().getName());
        System.out.println("VALOR ID: " + idObj);

        int idUsuario = Integer.parseInt(idObj.toString());

        System.out.println("ID FINAL: " + idUsuario);

        model.addAttribute("reservas", reservaDAO.obtenerPorUsuario(idUsuario));

        return "cliente";
    }

    // 👉 USUARIOS
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioDAO.listarUsuarios());
        return "usuarios";
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
    public String mostrarFormularioPropiedad(Model model) {
        model.addAttribute("propietarios", usuarioDAO.listarPropietarios());
        return "crearPropiedad";
    }
    // EDITAR PROPIEDAD

    @GetMapping("/editarPropiedad/{id}")
    public String editarPropiedad(@PathVariable int id, Model model) {

        model.addAttribute("propiedad", propiedadDAO.obtenerPorId(id));

        return "editarPropiedad";
    }
    // ACTUALIZAR PROPIEDAD

    @PostMapping("/actualizarPropiedad")
    public String actualizarPropiedad(
            @RequestParam int id,
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam double precio,
            @RequestParam String ubicacion,
            @RequestParam int capacidad,
            @RequestParam("imagenes") MultipartFile[] imagenes) {

        // ✏️ actualizar datos
        propiedadDAO.actualizarPropiedad(id, titulo, descripcion, precio, ubicacion, capacidad);

        try {
            for (MultipartFile imagen : imagenes) {

                if (!imagen.isEmpty()) {

                    String nombreArchivo = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();

                    String ruta = "src/main/resources/static/images/";
                    File archivo = new File(ruta + nombreArchivo);

                    imagen.transferTo(archivo);

                    String urlImagen = "images/" + nombreArchivo;

                    // 🔥 AQUÍ USAMOS id
                    imagenDAO.guardarImagen(id, urlImagen);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/propietario";
    }

    @PostMapping("/guardarPropiedad")
    public String guardarPropiedad(
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam double precio,
            @RequestParam String ubicacion,
            @RequestParam int capacidad,
            @RequestParam("imagen") MultipartFile imagen,
            HttpSession session) {

        try {
            // 🔐 usuario sesión
            Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuario");

            if (usuario == null) {
                return "redirect:/login";
            }

            int idUsuario = Integer.parseInt(usuario.get("id").toString());

            // 💾 guardar propiedad y obtener ID
            int idPropiedad = propiedadDAO.guardarPropiedadYDevolverId(
                    titulo, descripcion, precio, ubicacion, capacidad, idUsuario
            );

            // 📸 guardar imagen
            if (!imagen.isEmpty()) {

                String nombreArchivo = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();

                String ruta = "src/main/resources/static/images/";
                File carpeta = new File(ruta);

                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }

                File archivo = new File(ruta + nombreArchivo);
                imagen.transferTo(archivo);

                String urlImagen = "images/" + nombreArchivo;

                // 💾 guardar en BD
                imagenDAO.guardarImagen(idPropiedad, urlImagen);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/propietario";
    }
    // ELIMINAR PROPIEDAD
    @GetMapping("/eliminarPropiedad/{id}")
    public String eliminarPropiedad(@PathVariable int id) {

        propiedadDAO.eliminarPropiedad(id);

        return "redirect:/propietario";
    }

        // AÑADIR USUARIO
        @GetMapping("/crearUsuario")
        public String mostrarFormularioUsuario () {
            return "crearUsuario";
        }

        @PostMapping("guardarUsuario")
        public String guardarUsuario (
                @RequestParam String nombre,
                @RequestParam String email,
                @RequestParam String password,
                @RequestParam String telefono,
        @RequestParam int id_tipo){

            usuarioDAO.guardarUsuario(nombre, email, password, telefono, id_tipo);
            return "redirect:/usuarios";
        }

        // AÑADIR RESERVA
        @GetMapping("/crearReserva")
        public String mostrarFormularioReserva () {
            return "crearReserva";
        }

        @PostMapping("/guardarReserva")
        public String guardarReserva (
        @RequestParam int id_usuario,
        @RequestParam int id_propiedad,
        @RequestParam String fecha_inicio,
        @RequestParam String fecha_fin){

            reservaDAO.guardarReserva(id_usuario, id_propiedad, fecha_inicio, fecha_fin);

            return "redirect:/propiedad/" + id_propiedad; // 👈 MEJOR
        }

        // AÑADIR COMENTARIO
        @GetMapping("/crearComentario")
        public String mostrarFormularioComentario () {
            return "crearComentario";
        }

        @PostMapping("/guardarComentario")
        public String guardarComentario (
        @RequestParam int id_usuario,
        @RequestParam int id_propiedad,
        @RequestParam String comentario,
        @RequestParam int puntuacion){

            comentarioDAO.guardarComentario(id_usuario, id_propiedad, comentario, puntuacion);
            return "redirect:/comentarios";
        }
        // VISTA DETALLE PROPIEDAD
        @GetMapping("/propiedad/{id}")
        public String verPropiedad ( @PathVariable int id, Model model){

            model.addAttribute("propiedad", propiedadDAO.obtenerPorId(id));
            model.addAttribute("imagenes", imagenDAO.obtenerPorPropiedad(id));
            model.addAttribute("comentarios", comentarioDAO.obtenerPorPropiedad(id));

            return "detallePropiedad";
        }
        // BUSQUEDA POR CIUDAD
        @GetMapping("/propiedades")
        public String listarPropiedades (@RequestParam(required = false) String ciudad, Model model){

            if (ciudad != null && !ciudad.isEmpty()) {
                model.addAttribute("propiedades", propiedadDAO.buscarPorCiudad(ciudad));
            } else {
                model.addAttribute("propiedades", propiedadDAO.listarPropiedadesConImagen());
            }

            return "propiedades";
        }

    }

