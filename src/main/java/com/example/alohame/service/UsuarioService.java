package com.example.alohame.service;

import com.example.alohame.dao.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    public Map<String, Object> autenticar(String email, String password) {
        return usuarioDAO.login(email, password);
    }

    public void registrarUsuario(String nombre,
                                 String email,
                                 String password,
                                 String telefono,
                                 String tipoUsuario) {
        usuarioDAO.guardarUsuario(nombre, email, password, telefono, tipoUsuario);
    }

    public List<Map<String, Object>> listarUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    public List<Map<String, Object>> listarPropietarios() {
        return usuarioDAO.listarPropietarios();
    }
}

