package com.example.alohame.service;

import com.example.alohame.dao.ReservaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReservaService {

    @Autowired
    private ReservaDAO reservaDAO;

    public List<Map<String, Object>> listarReservas() {
        return reservaDAO.listarReservas();
    }

    public List<Map<String, Object>> obtenerReservasPorUsuario(int idUsuario) {
        return reservaDAO.obtenerPorUsuario(idUsuario);
    }

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

    public void cancelarReserva(int idReserva) {
        reservaDAO.cancelarReserva(idReserva);
    }
}

