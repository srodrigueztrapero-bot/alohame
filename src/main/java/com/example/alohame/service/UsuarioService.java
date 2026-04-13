package com.example.alohame.service;

import com.example.alohame.dao.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Servicio de lógica de negocio para usuarios.
 *
 * Actúa como intermediario entre el controlador y UsuarioDAO,
 * centralizando las operaciones de autenticación, registro y consultas
 * de usuarios para mantener el controlador limpio de lógica de datos.
 */
@Service
public class UsuarioService {

    // Inyección del DAO que ejecuta las consultas SQL sobre la tabla usuarios
    @Autowired
    private UsuarioDAO usuarioDAO;

    /**
     * Autentica a un usuario buscando su email y contraseña en la base de datos.
     * Devuelve el mapa con todos los datos del usuario si coinciden, o null si no existe.
     */
    public Map<String, Object> autenticar(String email, String password) {
        return usuarioDAO.login(email, password);
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     * Lanza excepción si el email ya existe (constraint único en BD).
     */
    public void registrarUsuario(String nombre,
                                 String email,
                                 String password,
                                 String telefono,
                                 String tipoUsuario) {
        usuarioDAO.guardarUsuario(nombre, email, password, telefono, tipoUsuario);
    }

    /**
     * Devuelve la lista de todos los usuarios registrados.
     * Solo accesible desde el panel de administración.
     */
    public List<Map<String, Object>> listarUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    /**
     * Devuelve solo los usuarios con rol 'propietario'.
     * Se usa en el formulario de crear propiedad para asignar propietario.
     */
    public List<Map<String, Object>> listarPropietarios() {
        return usuarioDAO.listarPropietarios();
    }
}
