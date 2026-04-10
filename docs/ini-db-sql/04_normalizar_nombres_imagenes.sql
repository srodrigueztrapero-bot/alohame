-- Normaliza imagenes.url para dejar solo nombre de archivo (sin images/ y sin prefijo numerico)
-- Ejemplo: images/1775834385890_avila9.jpg -> avila9.jpg

START TRANSACTION;

-- 1) Comprobacion previa de posibles colisiones tras normalizar
-- Si devuelve filas, revisa antes de confirmar COMMIT.
SELECT nombre_normalizado, COUNT(*) AS repeticiones
FROM (
	SELECT REGEXP_REPLACE(SUBSTRING_INDEX(url, '/', -1), '^[0-9]+_', '') AS nombre_normalizado
	FROM imagenes
) t
GROUP BY nombre_normalizado
HAVING COUNT(*) > 1;

-- 2) Quitar cualquier prefijo de carpeta (images/ u otros)
UPDATE imagenes
SET url = SUBSTRING_INDEX(url, '/', -1);

-- 3) Quitar prefijo numerico tipo 1775834385890_
UPDATE imagenes
SET url = REGEXP_REPLACE(url, '^[0-9]+_', '');

COMMIT;

-- 4) Comprobacion final
SELECT id, id_propiedad, url
FROM imagenes
ORDER BY id;

