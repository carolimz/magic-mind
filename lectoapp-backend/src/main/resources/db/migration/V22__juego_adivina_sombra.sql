/*
 * V22: Insertar la Etapa 8D (Adivina la Sombra) y su actividad.
 */

/* 1. CREAR LA NUEVA ETAPA "ADIVINA LA SOMBRA" EN EL ORDEN 14 */
-- Usamos el orden 14 porque 1-13 ya están ocupados.
INSERT INTO etapa (nombre, descripcion, objetivo_pedagogico, orden, activo, created_at, updated_at)
VALUES (
    'Etapa 8D - Adivina la Sombra',
    'Adivina el objeto o animal observando únicamente su silueta.',
    'Estimular el reconocimiento visual y asociación de palabras',
    14,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

/* 2. CREAR LA ACTIVIDAD PARA LA NUEVA ETAPA */
INSERT INTO actividad (
    nombre,
    descripcion,
    tipo_actividad,
    dificultad,
    configuracion,
    activo,
    etapa_id,
    created_at,
    updated_at
)
VALUES (
    'Adivina la Sombra',
    'Observa la sombra misteriosa y elige la palabra correcta.',
    'ADIVINA_LA_SOMBRA',
    'MEDIA',
    $json$
    {
      "items": [
        {
          "id": 1,
          "recurso": "🐘",
          "opciones": ["oso", "elefante", "gato"],
          "respuestaCorrecta": "elefante"
        },
        {
          "id": 2,
          "recurso": "🦆",
          "opciones": ["pato", "sol", "luna"],
          "respuestaCorrecta": "pato"
        },
        {
          "id": 3,
          "recurso": "🍎",
          "opciones": ["pera", "fruta", "manzana"],
          "respuestaCorrecta": "manzana"
        }
      ]
    }
    $json$::jsonb,
    TRUE,
    (SELECT id FROM etapa WHERE orden = 14),
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
