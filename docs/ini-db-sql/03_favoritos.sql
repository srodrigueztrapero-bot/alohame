-- Migracion: tabla favoritos
-- Ejecutar sobre la base de datos alohame

USE alohame;

CREATE TABLE IF NOT EXISTS favoritos (
  id INT NOT NULL AUTO_INCREMENT,
  id_usuario INT NOT NULL,
  id_propiedad INT NOT NULL,
  fecha_agregado DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_favorito_usuario_propiedad (id_usuario, id_propiedad),
  KEY idx_favorito_usuario (id_usuario),
  KEY idx_favorito_propiedad (id_propiedad),
  CONSTRAINT fk_favorito_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
  CONSTRAINT fk_favorito_propiedad FOREIGN KEY (id_propiedad) REFERENCES propiedades(id)
);

