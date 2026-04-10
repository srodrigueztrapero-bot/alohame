package com.example.alohame.controller;

import com.example.alohame.dao.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
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
    @Autowired private FavoritoDAO favoritoDAO;

    @Value("${app.images.dir:${user.dir}/src/main/resources/static/images}")
    private String imagesDir;


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

    private int obtenerContadorFavoritosSesion(HttpSession session) {
        if (session == null) {
            return 0;
        }
        Map<String, Object> usuario = getUsuarioSesion(session);
        Integer idUsuario = obtenerIdUsuario(usuario);
        if (idUsuario == null) {
            return 0;
        }
        return favoritoDAO.contarFavoritos(idUsuario);
    }

    private void agregarContadorFavoritos(Model model, HttpSession session) {
        model.addAttribute("favoritosCount", obtenerContadorFavoritosSesion(session));
    }

    private String normalizarNombreArchivo(String originalFilename) {
        if (originalFilename == null) {
            return "";
        }

        String nombre = originalFilename.trim().replace("\\", "/");
        int lastSlash = nombre.lastIndexOf('/');
        if (lastSlash >= 0 && lastSlash < nombre.length() - 1) {
            nombre = nombre.substring(lastSlash + 1);
        }
        return nombre.trim();
    }

    private boolean esFormatoUrl(String valor) {
        if (valor == null) {
            return false;
        }
        String texto = valor.trim().toLowerCase(Locale.ROOT);
        return texto.startsWith("http://") || texto.startsWith("https://") || texto.startsWith("www.");
    }

    private boolean existeNombreEnDisco(String nombreArchivo) {
        String nombreLower = nombreArchivo.toLowerCase(Locale.ROOT);
        File carpeta = new File(imagesDir);
        File[] archivos = carpeta.listFiles();
        if (archivos == null) {
            return false;
        }

        for (File archivo : archivos) {
            if (!archivo.isFile()) {
                continue;
            }
            String existente = archivo.getName().toLowerCase(Locale.ROOT);
            if (existente.equals(nombreLower) || existente.endsWith("_" + nombreLower)) {
                return true;
            }
        }
        return false;
    }

    private String validarImagenSubida(MultipartFile imagen) throws Exception {
        if (imagen == null || imagen.isEmpty()) {
            return "";
        }

        if (esFormatoUrl(imagen.getOriginalFilename())) {
            throw new Exception("No se permiten URLs como imagen. Debes subir un archivo desde tu equipo.");
        }

        String nombreOriginal = normalizarNombreArchivo(imagen.getOriginalFilename());
        if (nombreOriginal.isEmpty()) {
            throw new Exception("No se pudo leer el nombre del archivo de imagen.");
        }

        if (imagenDAO.existeImagenPorNombre(nombreOriginal) || existeNombreEnDisco(nombreOriginal)) {
            throw new Exception("La imagen '" + nombreOriginal + "' ya existe en el sistema. Por favor, renombrala y vuelve a intentar.");
        }

        return nombreOriginal;
    }

    private void guardarImagenEnDisco(MultipartFile imagen, int idPropiedad) throws Exception {
        if (imagen == null || imagen.isEmpty()) {
            return;
        }

        String nombreOriginal = validarImagenSubida(imagen);

        String nombre = nombreOriginal;

        File rutaDir = new File(imagesDir);

        if (!rutaDir.exists()) {
            rutaDir.mkdirs();
        }

        File archivo = new File(rutaDir, nombre);
        imagen.transferTo(archivo);
        
        System.out.println("✅ Imagen guardada en: " + archivo.getAbsolutePath());

        imagenDAO.guardarImagen(idPropiedad, nombre);
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

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "crearusuario";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@RequestParam String nombre,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam String telefono,
                                 @RequestParam(required = false, defaultValue = "cliente") String tipo_usuario) {
        try {
            usuarioDAO.guardarUsuario(nombre, email, password, telefono, tipo_usuario);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/registro?error=true";
        }
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
        if (usuario == null) return "redirect:/login";

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
        agregarContadorFavoritos(model, session);
        return "cliente";
    }

    /* =========================
       USUARIOS
    ========================= */

    @GetMapping("/usuarios")
    public String listarUsuarios(HttpSession session, Model model) {
        if (!esTipoUsuario(session, "admin")) {
            return "redirect:/login";
        }

        model.addAttribute("usuarios", usuarioDAO.listarUsuarios());
        return "usuarios";
    }

    

    @GetMapping("/propiedades")
    public String listarPropiedades(@RequestParam(required = false) String ciudad, Model model, HttpSession session) {

        if (ciudad != null && !ciudad.isEmpty()) {
            model.addAttribute("propiedades", propiedadDAO.buscarPorCiudad(ciudad));
        } else {
            model.addAttribute("propiedades", propiedadDAO.listarPropiedadesConImagen());
        }

        agregarContadorFavoritos(model, session);

        // Verificar si es admin
        boolean esAdmin = esTipoUsuario(session, "admin");
        model.addAttribute("esAdmin", esAdmin);

        return "propiedades";
    }

    @GetMapping("/mapa")
    public String mostrarMapa(Model model, HttpSession session) {
        model.addAttribute("propiedades", propiedadDAO.listarPropiedadesConCoordenadas());
        agregarContadorFavoritos(model, session);
        return "mapa";
    }

    @GetMapping("/propiedad/{id}")
    public String verPropiedad(@PathVariable int id, Model model, HttpSession session) {
        model.addAttribute("propiedad", propiedadDAO.obtenerPorId(id));
        model.addAttribute("imagenes", imagenDAO.obtenerPorPropiedad(id));
        model.addAttribute("comentarios", comentarioDAO.obtenerPorPropiedad(id));
        model.addAttribute("propiedades", propiedadDAO.listarPropiedadesConMedia());
        agregarContadorFavoritos(model, session);

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
                                   @RequestParam("imagenes") MultipartFile[] imagenes,
                                   HttpSession session,
                                   Model model) {

        try {
            Map<String, Object> usuario = getUsuarioSesion(session);
            if (usuario == null) return "redirect:/login";

            Integer idUsuario = obtenerIdUsuario(usuario);
            if (idUsuario == null) return "redirect:/login";

            // Si alguna imagen es invalida/duplicada, se bloquea toda la creacion.
            if (imagenes != null) {
                for (MultipartFile img : imagenes) {
                    if (img != null && !img.isEmpty()) {
                        validarImagenSubida(img);
                    }
                }
            }

            int idPropiedad = propiedadDAO.guardarPropiedadYDevolverId(
                    titulo, descripcion, precio, ubicacion, capacidad, idUsuario
            );

            for (MultipartFile img : imagenes) {
                if (img != null && !img.isEmpty()) {
                    guardarImagenEnDisco(img, idPropiedad);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            try {
                return "redirect:/crearPropiedad?error=" + URLEncoder.encode(e.getMessage(), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                return "redirect:/crearPropiedad";
            }
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
                                      @RequestParam("imagenes") MultipartFile[] imagenes,
                                      Model model) {

        propiedadDAO.actualizarPropiedad(id, titulo, descripcion, precio, ubicacion, capacidad);

        try {
            // Conservar imágenes actuales y añadir solo las nuevas que se suban en la edición.
            if (imagenes != null) {
                for (MultipartFile img : imagenes) {
                    if (img != null && !img.isEmpty()) {
                        guardarImagenEnDisco(img, id);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            try {
                return "redirect:/editarPropiedad/" + id + "?error=" + URLEncoder.encode(e.getMessage(), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                return "redirect:/editarPropiedad/" + id;
            }
        }

        return "redirect:/propietario";
    }

    @GetMapping("/eliminarPropiedad/{id}")
    public String eliminarPropiedad(@PathVariable int id) {
        propiedadDAO.eliminarPropiedad(id);
        return "redirect:/propietario";
    }

    @GetMapping("/reserva/cancelar/{id}")
    public String cancelarReserva(@PathVariable int id, HttpSession session) {
        if (!esTipoUsuario(session, "admin")) {
            return "redirect:/login";
        }

        reservaDAO.cancelarReserva(id);
        return "redirect:/reservas";
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

        if (id_propiedad == null) {
            return "redirect:/propiedades";
        }

        model.addAttribute("id_propiedad", id_propiedad);
        return "crearReserva";
    }


    @PostMapping("/guardarReserva")
    public String guardarReserva(@RequestParam int id_propiedad,
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

        if (!reservaDAO.estaDisponible(id_propiedad, fecha_inicio, fecha_fin)) {
            model.addAttribute("error", "❌ Ya está reservada en esas fechas");
            model.addAttribute("id_propiedad", id_propiedad);
            return "crearReserva";
        }

        reservaDAO.guardarReserva(idUsuarioSesion, id_propiedad, fecha_inicio, fecha_fin);
        return "redirect:/cliente";
    }


    /* =========================
       FAVORITOS
    ========================= */

    @GetMapping("/favoritos")
    public String listarFavoritos(HttpSession session, Model model) {
        Map<String, Object> usuario = getUsuarioSesion(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        Integer idUsuario = obtenerIdUsuario(usuario);
        if (idUsuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("propiedades",
                favoritoDAO.obtenerFavoritosPorUsuario(idUsuario));
        agregarContadorFavoritos(model, session);
        return "favoritos";
    }

    @PostMapping("/favorito/agregar")
    @ResponseBody
    public Map<String, Object> agregarFavorito(@RequestParam int idPropiedad, HttpSession session) {
        Map<String, Object> usuario = getUsuarioSesion(session);
        if (usuario == null) {
            return Map.of("success", false, "message", "Debes iniciar sesion");
        }

        Integer idUsuario = obtenerIdUsuario(usuario);
        if (idUsuario == null) {
            return Map.of("success", false, "message", "Error en la sesion");
        }

        favoritoDAO.agregarFavorito(idUsuario, idPropiedad);
        int favoritosCount = favoritoDAO.contarFavoritos(idUsuario);
        return Map.of("success", true, "message", "Agregado a favoritos", "favoritosCount", favoritosCount);
    }

    @PostMapping("/favorito/eliminar")
    @ResponseBody
    public Map<String, Object> eliminarFavorito(@RequestParam int idPropiedad, HttpSession session) {
        Map<String, Object> usuario = getUsuarioSesion(session);
        if (usuario == null) {
            return Map.of("success", false, "message", "Debes iniciar sesion");
        }

        Integer idUsuario = obtenerIdUsuario(usuario);
        if (idUsuario == null) {
            return Map.of("success", false, "message", "Error en la sesion");
        }

        favoritoDAO.eliminarFavorito(idUsuario, idPropiedad);
        int favoritosCount = favoritoDAO.contarFavoritos(idUsuario);
        return Map.of("success", true, "message", "Eliminado de favoritos", "favoritosCount", favoritosCount);
    }

    @GetMapping("/favorito/verificar/{idPropiedad}")
    @ResponseBody
    public Map<String, Object> verificarFavorito(@PathVariable int idPropiedad, HttpSession session) {
        Map<String, Object> usuario = getUsuarioSesion(session);
        if (usuario == null) {
            return Map.of("esFavorito", false);
        }

        Integer idUsuario = obtenerIdUsuario(usuario);
        if (idUsuario == null) {
            return Map.of("esFavorito", false);
        }

        boolean esFavorito = favoritoDAO.esFavorito(idUsuario, idPropiedad);
        return Map.of("esFavorito", esFavorito);
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

