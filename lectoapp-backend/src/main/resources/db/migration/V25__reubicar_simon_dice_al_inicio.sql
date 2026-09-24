/*
 * V25: Reubicar y organizar Simón Dice al principio (Etapa 1B)
 */

-- 1. Limpiar cualquier intento previo de Simón Dice para evitar duplicados o errores
DELETE FROM actividad WHERE tipo_actividad = 'SIMON_DICE';
DELETE FROM etapa WHERE nombre LIKE '%Simón Dice%';

-- 2. Desplazar todas las etapas desde la 2 en adelante para hacer espacio en el orden 2.
-- Usamos números negativos temporalmente para evitar cualquier error de restricción UNIQUE.
UPDATE etapa SET orden = -orden WHERE orden >= 2;
UPDATE etapa SET orden = (-orden) + 1 WHERE orden <= -2;

-- 3. Insertar la nueva etapa 1B en el hueco que quedó (orden = 2)
INSERT INTO etapa (nombre, descripcion, objetivo_pedagogico, orden, activo, created_at, updated_at)
VALUES (
    'Etapa 1B - Simón Dice',
    'Juego de comprensión auditiva rápida donde el niño escucha una instrucción y reacciona.',
    'Desarrollar la memoria de trabajo y la comprensión auditiva rápida.',
    2,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- 4. Insertar la actividad y asociarla a la nueva etapa
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
    $json$
    {
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
    }
    $json$::jsonb,
    TRUE,
    (SELECT id FROM etapa WHERE orden = 2 LIMIT 1),
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
