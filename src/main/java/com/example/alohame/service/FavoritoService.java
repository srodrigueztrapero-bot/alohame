package com.example.alohame.service;

import com.example.alohame.dao.FavoritoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Servicio de lógica de negocio para la gestión de favoritos.
 *
 * Encapsula todas las operaciones relacionadas con el sistema de favoritos:
 * agregar, eliminar, verificar y contar propiedades guardadas por un usuario.
 * El controlador delega aquí para no acceder directamente al DAO.
 */
@Service
public class FavoritoService {

    // Inyección del DAO que ejecuta las consultas SQL sobre la tabla favoritos
    @Autowired
    private FavoritoDAO favoritoDAO;

    /**
     * Cuenta cuántas propiedades tiene un usuario en su lista de favoritos.
     * Se usa para mostrar el contador en el navbar.
     */
    public int contarFavoritosUsuario(int idUsuario) {
        return favoritoDAO.contarFavoritos(idUsuario);
    }

    /**
     * Devuelve las propiedades favoritas de un usuario con su imagen principal.
     * Se muestra en la página "Mis favoritos".
     */
    public List<Map<String, Object>> listarFavoritosUsuario(int idUsuario) {
        return favoritoDAO.obtenerFavoritosPorUsuario(idUsuario);
    }

    /**
     * Añade una propiedad a la lista de favoritos del usuario.
     */
    public void agregarFavorito(int idUsuario, int idPropiedad) {
        favoritoDAO.agregarFavorito(idUsuario, idPropiedad);
    }

    /**
     * Elimina una propiedad de la lista de favoritos del usuario.
     */
    public void eliminarFavorito(int idUsuario, int idPropiedad) {
        favoritoDAO.eliminarFavorito(idUsuario, idPropiedad);
    }

    /**
     * Comprueba si una propiedad ya está en favoritos del usuario.
     * Se usa para pintar el icono de corazón relleno o vacío en la vista.
     */
    public boolean esFavorito(int idUsuario, int idPropiedad) {
        return favoritoDAO.esFavorito(idUsuario, idPropiedad);
    }
}
