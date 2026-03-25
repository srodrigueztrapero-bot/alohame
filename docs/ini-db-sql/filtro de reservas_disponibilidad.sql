-- ============================================
-- CONSULTA: PROPIEDADES DISPONIBLES POR FECHAS
-- ============================================
-- Esta consulta obtiene todas las propiedades
-- que NO tienen reservas en el rango de fechas indicado.
--
-- FECHA INICIO: 2026-04-02
-- FECHA FIN:    2026-04-03
--
-- LÓGICA:
-- Se excluyen las propiedades que tienen reservas
-- que se solapan con el rango seleccionado.
-- ============================================

SELECT *
FROM propiedades p

-- Excluimos propiedades que están reservadas
WHERE p.id NOT IN (

    SELECT r.id_propiedad
    FROM reservas r

    WHERE (

        -- Caso 1:
        -- La fecha de inicio de búsqueda cae dentro de una reserva existente
        '2026-04-02' BETWEEN r.fecha_inicio AND r.fecha_fin

        OR

        -- Caso 2:
        -- La fecha de fin de búsqueda cae dentro de una reserva existente
        '2026-04-03' BETWEEN r.fecha_inicio AND r.fecha_fin

        OR

        -- Caso 3:
        -- La reserva existente empieza dentro del rango buscado
        r.fecha_inicio BETWEEN '2026-04-02' AND '2026-04-03'
    )
);