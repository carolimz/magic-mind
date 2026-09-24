/*
 * V24: Insertar la Etapa 9 y su actividad (Simón Dice).
 */

INSERT INTO etapa (
    id,
    nombre,
    orden,
    descripcion,
    objetivo_pedagogico,
    activo
) VALUES (
    9,
    'Etapa 9 - Simón Dice',
    9,
    'Juego de comprensión auditiva rápida donde el niño escucha una instrucción y reacciona.',
    'Desarrollar la memoria de trabajo y la comprensión auditiva rápida.',
    true
) ON CONFLICT (id) DO NOTHING;

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
) VALUES (
    'Simón Dice',
    'Escucha a Simón con atención y toca la opción correcta antes de que acabe el tiempo.',
    'SIMON_DICE',
    'MEDIA',
    '{
      "items": [
        {
          "id": 1,
          "pregunta": "Simón dice... ¡Toca el animal que ladra!",
          "opciones": ["perro", "gato", "pato"],
          "respuestaCorrecta": "perro"
        },
        {
          "id": 2,
          "pregunta": "Simón dice... ¡Toca la fruta de color amarillo!",
          "opciones": ["fresa", "banano", "uva"],
          "respuestaCorrecta": "banano"
        },
        {
          "id": 3,
          "pregunta": "Simón dice... ¡Toca el medio de transporte que vuela!",
          "opciones": ["carro", "barco", "avión"],
          "respuestaCorrecta": "avión"
        }
      ]
    }'::jsonb,
    true,
    9,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
