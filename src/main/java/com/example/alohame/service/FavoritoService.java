package com.example.alohame.service;

import com.example.alohame.dao.FavoritoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoDAO favoritoDAO;

    public int contarFavoritosUsuario(int idUsuario) {
        return favoritoDAO.contarFavoritos(idUsuario);
    }

    public List<Map<String, Object>> listarFavoritosUsuario(int idUsuario) {
        return favoritoDAO.obtenerFavoritosPorUsuario(idUsuario);
    }

    public void agregarFavorito(int idUsuario, int idPropiedad) {
        favoritoDAO.agregarFavorito(idUsuario, idPropiedad);
    }

    public void eliminarFavorito(int idUsuario, int idPropiedad) {
        favoritoDAO.eliminarFavorito(idUsuario, idPropiedad);
    }

    public boolean esFavorito(int idUsuario, int idPropiedad) {
        return favoritoDAO.esFavorito(idUsuario, idPropiedad);
    }
}

