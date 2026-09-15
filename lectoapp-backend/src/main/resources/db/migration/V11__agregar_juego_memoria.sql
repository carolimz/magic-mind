/*
 * Agrega una segunda actividad de tipo EMPAREJAR (juego de memoria) a las
 * etapas 2 (Vocales) y 3 (Consonantes).
 *
 * Etapa 2 — Juego de memoria mayúscula/minúscula:
 *   El niño empareja A con a, E con e, I con i, O con o, U con u.
 *
 * Etapa 3 — Juego de memoria letra/imagen-emoji:
 *   El niño empareja la letra M con 🌽 (maíz), P con 🐟 (pez), etc.
 *
 * IMPORTANTE: Se insertan NUEVAS filas. No se modifica la actividad existente.
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
    'Memoria de vocales',
    'Empareja cada vocal mayúscula con su versión minúscula.',
    'EMPAREJAR',
    'FACIL',
    $json$
    {
      "items": [
        {"id": 1, "par1": "A", "par2": "a", "tipo": "LETRA_LETRA"},
        {"id": 2, "par1": "E", "par2": "e", "tipo": "LETRA_LETRA"},
        {"id": 3, "par1": "I", "par2": "i", "tipo": "LETRA_LETRA"},
        {"id": 4, "par1": "O", "par2": "o", "tipo": "LETRA_LETRA"},
        {"id": 5, "par1": "U", "par2": "u", "tipo": "LETRA_LETRA"}
      ]
    }$json$::jsonb,
    TRUE,
    (SELECT id FROM etapa WHERE orden = 2),
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'Memoria de consonantes',
    'Empareja cada consonante con la imagen de una palabra que empieza con esa letra.',
    'EMPAREJAR',
    'FACIL',
    $json$
    {
      "items": [
        {"id": 1, "par1": "M",  "par2": "🌽", "tipo": "LETRA_IMAGEN"},
        {"id": 2, "par1": "P",  "par2": "🐟", "tipo": "LETRA_IMAGEN"},
        {"id": 3, "par1": "S",  "par2": "☀️", "tipo": "LETRA_IMAGEN"},
        {"id": 4, "par1": "L",  "par2": "🦁", "tipo": "LETRA_IMAGEN"},
        {"id": 5, "par1": "T",  "par2": "🐅", "tipo": "LETRA_IMAGEN"},
        {"id": 6, "par1": "N",  "par2": "🌙", "tipo": "LETRA_IMAGEN"},
        {"id": 7, "par1": "D",  "par2": "🍬", "tipo": "LETRA_IMAGEN"},
        {"id": 8, "par1": "B",  "par2": "🚌", "tipo": "LETRA_IMAGEN"}
      ]
    }$json$::jsonb,
    TRUE,
    (SELECT id FROM etapa WHERE orden = 3),
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
