package com.example.alohame.service;

import com.example.alohame.dao.ReservaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Servicio de lógica de negocio para la gestión de reservas.
 *
 * Centraliza las reglas de negocio de reservas: verificar disponibilidad
 * antes de crear una reserva, listar reservas y cancelarlas.
 * Separa la lógica del controlador para mayor claridad y mantenibilidad.
 */
@Service
public class ReservaService {

    // Inyección del DAO que ejecuta las consultas SQL sobre la tabla reservas
    @Autowired
    private ReservaDAO reservaDAO;

    /**
     * Devuelve todas las reservas del sistema.
     * Usado en el panel de administración para ver el listado global.
     */
    public List<Map<String, Object>> listarReservas() {
        return reservaDAO.listarReservas();
    }

    /**
     * Devuelve las reservas de un usuario concreto con datos de la propiedad e imagen.
     * Se muestra en el panel del cliente (zona privada).
     */
    public List<Map<String, Object>> obtenerReservasPorUsuario(int idUsuario) {
        return reservaDAO.obtenerPorUsuario(idUsuario);
    }

    /**
     * Intenta crear una reserva para las fechas indicadas.
     * Primero comprueba disponibilidad en la BD: si la propiedad ya está
     * reservada en ese rango de fechas devuelve false sin guardar nada.
     * Si está libre, guarda la reserva y devuelve true.
     */
    public boolean guardarReservaSiDisponible(int idUsuario,
                                              int idPropiedad,
                                              String fechaInicio,
                                              String fechaFin) {
        if (!reservaDAO.estaDisponible(idPropiedad, fechaInicio, fechaFin)) {
            return false;
        }
        reservaDAO.guardarReserva(idUsuario, idPropiedad, fechaInicio, fechaFin);
        return true;
    }

    /**
     * Cancela (elimina) una reserva por su ID.
     * Solo accesible para el rol admin desde el panel de reservas.
     */
    public void cancelarReserva(int idReserva) {
        reservaDAO.cancelarReserva(idReserva);
    }
}
