package com.example.alohame.service;

import com.example.alohame.dao.ImagenDAO;
import com.example.alohame.dao.PropiedadDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Locale;

@Service
public class PropiedadImagenService {

    @Autowired
    private PropiedadDAO propiedadDAO;

    @Autowired
    private ImagenDAO imagenDAO;

    @Value("${app.images.dir:${user.dir}/src/main/resources/static/images}")
    private String imagesDir;

    public void guardarPropiedadConImagenes(String titulo,
                                            String descripcion,
                                            double precio,
                                            String ubicacion,
                                            int capacidad,
                                            int idUsuario,
                                            MultipartFile[] imagenes) throws Exception {

        // Si alguna imagen es invalida/duplicada, se bloquea toda la creacion.
        validarLoteImagenes(imagenes);

        int idPropiedad = propiedadDAO.guardarPropiedadYDevolverId(
                titulo, descripcion, precio, ubicacion, capacidad, idUsuario
        );

        guardarLoteImagenes(idPropiedad, imagenes);
    }

    public void actualizarImagenesPropiedad(int idPropiedad, MultipartFile[] imagenes) throws Exception {
        // Conservar imágenes actuales y añadir solo las nuevas que se suban en la edición.
        guardarLoteImagenes(idPropiedad, imagenes);
    }

    private void validarLoteImagenes(MultipartFile[] imagenes) throws Exception {
        if (imagenes == null) {
            return;
        }
        for (MultipartFile img : imagenes) {
            if (img != null && !img.isEmpty()) {
                validarImagenSubida(img);
            }
        }
    }

    private void guardarLoteImagenes(int idPropiedad, MultipartFile[] imagenes) throws Exception {
        if (imagenes == null) {
            return;
        }
        for (MultipartFile img : imagenes) {
            if (img != null && !img.isEmpty()) {
                guardarImagenEnDisco(img, idPropiedad);
            }
        }
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

