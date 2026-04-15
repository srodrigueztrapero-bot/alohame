package com.example.alohame.service;

import com.example.alohame.dao.ImagenDAO;
import com.example.alohame.dao.PropiedadDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Locale;

/**
 * Servicio de lógica de negocio para propiedades e imágenes.
 *
 * Es el servicio más complejo del proyecto. Gestiona:
 * - Creación de propiedades con sus imágenes asociadas.
 * - Validación de las imágenes subidas (sin URLs, sin duplicados).
 * - Guardado físico del archivo en disco (carpeta static/images).
 * - Registro de la imagen en la base de datos.
 *
 * La ruta de la carpeta de imágenes se lee de application.properties
 * mediante la clave app.images.dir.
 */
@Service
public class PropiedadImagenService {

    // DAO para operaciones sobre la tabla propiedades
    @Autowired
    private PropiedadDAO propiedadDAO;

    // DAO para operaciones sobre la tabla imagenes
    @Autowired
    private ImagenDAO imagenDAO;

    // Ruta de la carpeta donde se guardan físicamente las imágenes
    @Value("${app.images.dir:${user.home}/alohame/images}")
    private String imagesDir;

    /**
     * Crea una propiedad completa con todas sus imágenes en un solo paso:
     * 1. Valida todas las imágenes antes de guardar nada (fail-fast).
     * 2. Inserta la propiedad en BD y obtiene su ID generado.
     * 3. Guarda cada imagen en disco y registra su nombre en la tabla imagenes.
     */
    public void guardarPropiedadConImagenes(String titulo,
                                            String descripcion,
                                            double precio,
                                            String ubicacion,
                                            int capacidad,
                                            int idUsuario,
                                            MultipartFile[] imagenes) throws Exception {
        // Validación previa: si alguna imagen es inválida/duplicada se cancela todo
        validarLoteImagenes(imagenes);

        int idPropiedad = propiedadDAO.guardarPropiedadYDevolverId(
                titulo, descripcion, precio, ubicacion, capacidad, idUsuario
        );

        guardarLoteImagenes(idPropiedad, imagenes);
    }

    /**
     * Añade nuevas imágenes a una propiedad ya existente.
     * Las imágenes actuales se conservan; solo se agregan las nuevas subidas.
     */
    public void actualizarImagenesPropiedad(int idPropiedad, MultipartFile[] imagenes) throws Exception {
        guardarLoteImagenes(idPropiedad, imagenes);
    }

    // --- MÉTODOS PRIVADOS DE VALIDACIÓN Y GUARDADO ---

    /**
     * Recorre el array de imágenes y valida cada una antes de guardar.
     * Si alguna falla, lanza excepción y bloquea toda la operación.
     */
    private void validarLoteImagenes(MultipartFile[] imagenes) throws Exception {
        if (imagenes == null) return;
        for (MultipartFile img : imagenes) {
            if (img != null && !img.isEmpty()) {
                validarImagenSubida(img);
            }
        }
    }

    /**
     * Recorre el array de imágenes y guarda cada una en disco y en BD.
     */
    private void guardarLoteImagenes(int idPropiedad, MultipartFile[] imagenes) throws Exception {
        if (imagenes == null) return;
        for (MultipartFile img : imagenes) {
            if (img != null && !img.isEmpty()) {
                guardarImagenEnDisco(img, idPropiedad);
            }
        }
    }

    /**
     * Extrae solo el nombre del archivo descartando posibles rutas completas
     * que algunos navegadores incluyen en el nombre original (ej: C:\ruta\foto.jpg).
     */
    private String normalizarNombreArchivo(String originalFilename) {
        if (originalFilename == null) return "";
        String nombre = originalFilename.trim().replace("\\", "/");
        int lastSlash = nombre.lastIndexOf('/');
        if (lastSlash >= 0 && lastSlash < nombre.length() - 1) {
            nombre = nombre.substring(lastSlash + 1);
        }
        return nombre.trim();
    }

    /**
     * Detecta si el valor recibido es una URL (http/https/www).
     * Las URLs no están permitidas: solo se aceptan archivos subidos desde el equipo.
     */
    private boolean esFormatoUrl(String valor) {
        if (valor == null) return false;
        String texto = valor.trim().toLowerCase(Locale.ROOT);
        return texto.startsWith("http://") || texto.startsWith("https://") || texto.startsWith("www.");
    }

    /**
     * Comprueba si ya existe en disco un archivo con el mismo nombre (o con prefijo numérico).
     * Evita duplicados en la carpeta static/images.
     */
    private boolean existeNombreEnDisco(String nombreArchivo) {
        String nombreLower = nombreArchivo.toLowerCase(Locale.ROOT);
        File carpeta = new File(imagesDir);
        File[] archivos = carpeta.listFiles();
        if (archivos == null) return false;
        for (File archivo : archivos) {
            if (!archivo.isFile()) continue;
            String existente = archivo.getName().toLowerCase(Locale.ROOT);
            if (existente.equals(nombreLower) || existente.endsWith("_" + nombreLower)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Valida una imagen antes de guardarla aplicando tres reglas:
     * 1. No puede ser una URL (debe ser un archivo real).
     * 2. Debe tener un nombre de archivo válido.
     * 3. No puede existir ya en la BD ni en disco (no se permiten duplicados).
     */
    private String validarImagenSubida(MultipartFile imagen) throws Exception {
        if (imagen == null || imagen.isEmpty()) return "";

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

    /**
     * Guarda físicamente el archivo en la carpeta de imágenes y
     * registra su nombre en la tabla imagenes de la base de datos.
     * La carpeta se crea automáticamente si no existe.
     */
    private void guardarImagenEnDisco(MultipartFile imagen, int idPropiedad) throws Exception {
        if (imagen == null || imagen.isEmpty()) return;

        String nombreOriginal = validarImagenSubida(imagen);

        File rutaDir = new File(imagesDir);
        if (!rutaDir.exists()) {
            rutaDir.mkdirs();
        }

        File archivo = new File(rutaDir, nombreOriginal);
        imagen.transferTo(archivo);

        System.out.println("Imagen guardada en: " + archivo.getAbsolutePath());
        imagenDAO.guardarImagen(idPropiedad, nombreOriginal);
    }
}
