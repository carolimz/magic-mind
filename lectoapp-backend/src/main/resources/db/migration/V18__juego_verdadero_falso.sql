/*
 * 1. DESPLAZAR LA ETAPA 12 A LA POSICIÓN 13 (Si existe)
 *    Para hacer espacio para la nueva etapa de Verdadero/Falso en la posición 12.
 */
UPDATE etapa SET orden = 13, updated_at = CURRENT_TIMESTAMP WHERE orden = 12;

/*
 * 2. CREAR LA NUEVA ETAPA "VERDADERO O FALSO" EN EL ORDEN 12
 */
INSERT INTO etapa (nombre, descripcion, objetivo_pedagogico, orden, activo, created_at, updated_at)
VALUES (
    'Etapa 12 - Verdadero o Falso',
    'Aprende a diferenciar conceptos mediante afirmaciones de verdadero o falso.',
    'Evaluar comprensión básica',
    12,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

/*
 * 3. CREAR LA ACTIVIDAD PARA LA NUEVA ETAPA
 */
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
    'Verdadero o Falso',
    'Lee la afirmación y responde si es Verdadera o Falsa.',
    'VERDADERO_FALSO',
    'FACIL',
    $json$
    {
      "items": [
        {
          "id": 1,
          "afirmacion": "¿La palabra OSO empieza con la vocal O?",
          "esVerdadero": true,
          "imagenUrl": "https://cdn-icons-png.flaticon.com/512/375/375084.png"
        },
        {
          "id": 2,
          "afirmacion": "¿La palabra GATO empieza con la vocal A?",
          "esVerdadero": false,
          "imagenUrl": "https://cdn-icons-png.flaticon.com/512/1154/1154448.png"
        },
        {
          "id": 3,
          "afirmacion": "¿La palabra ELEFANTE empieza con la vocal E?",
          "esVerdadero": true,
          "imagenUrl": "https://cdn-icons-png.flaticon.com/512/2923/2923126.png"
        }
      ]
    }
    $json$::jsonb,
    TRUE,
    (SELECT id FROM etapa WHERE orden = 12),
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
