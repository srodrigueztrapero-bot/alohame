# SEO — Mejoras aplicadas al sistema

## Resumen

Se han añadido meta tags HTML a los 18 templates de la aplicación para mejorar el posicionamiento en buscadores (SEO) y la compartición en redes sociales.

---

## Meta tags añadidos

### Páginas públicas

Aplicados en: `index.html`, `propiedades.html`, `detallepropiedad.html`, `mapa.html`

| Tag | Propósito |
|-----|-----------|
| `<meta name="description">` | Descripción de la página para buscadores |
| `<meta name="keywords">` | Palabras clave relacionadas con el contenido |
| `<meta name="author">` | Autoría del sitio (Alohame) |
| `<meta property="og:title">` | Título al compartir en redes sociales |
| `<meta property="og:description">` | Descripción al compartir en redes sociales |
| `<meta property="og:type">` | Tipo de contenido Open Graph (`website`) |

### Páginas privadas / internas

Aplicados en: `login.html`, `crearusuario.html`, `crearpropiedad.html`, `editarpropiedad.html`, `crearReserva.html`, `crearcomentario.html`, `favoritos.html`, `reservas.html`, `comentarios.html`, `usuarios.html`, `admin.html`, `cliente.html`, `propietario.html`, `mensajespropietario.html`

| Tag | Propósito |
|-----|-----------|
| `<meta name="description">` | Descripción de la página |
| `<meta name="keywords">` | Palabras clave |
| `<meta name="author">` | Autoría del sitio (Alohame) |
| `<meta name="robots" content="noindex, nofollow">` | Impide que los buscadores indexen páginas privadas |

---

## Correcciones adicionales

Varios templates carecían de tags básicos que también se añadieron:

| Archivo | Corrección |
|---------|-----------|
| `crearusuario.html` | Añadidos `charset`, `viewport` y `lang="es"` |
| `crearpropiedad.html` | Añadidos `charset`, `viewport` y `lang="es"` |
| `editarpropiedad.html` | Añadidos `viewport` y `lang="es"` |
| `reservas.html` | Añadidos `charset`, `viewport` y `lang="es"` |
| `comentarios.html` | Añadidos `charset`, `viewport` y `lang="es"` |
| `usuarios.html` | Añadidos `charset`, `viewport` y `lang="es"` |
| `mensajespropietario.html` | Añadido `viewport` |

---

## Criterio aplicado

- Las páginas públicas (inicio, listado, detalle, mapa) reciben tags Open Graph para mejorar la previsualización al compartir enlaces.
- Las páginas privadas reciben `robots: noindex, nofollow` para evitar que los buscadores las indexen, ya que requieren sesión activa y no deben aparecer en resultados de búsqueda.