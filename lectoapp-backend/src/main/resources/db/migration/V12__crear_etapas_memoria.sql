/*
 * Crea dos etapas nuevas para los juegos de memoria y reubica las
 * actividades de tipo EMPAREJAR que se insertaron en V11.
 *
 * Árbol de etapas resultante:
 *   1  - Exploración espacial
 *   2  - Reconocimiento de vocales  (burbujas)
 *   2b - Memoria de vocales          ← NUEVA
 *   3  - Reconocimiento de consonantes (burbujas)
 *   3b - Memoria de consonantes      ← NUEVA
 *   4  - Formación de sílabas directas
 *   5  - Completar palabras
 *   6  - Sílabas complejas
 *   7  - Completar frases
 *   8  - Comprensión lectora
 *   9  - Comprensión lectora integral
 *
 * Se desplazan las etapas 4-9 para hacer hueco usando orden decimal.
 * El campo `orden` es INTEGER, así que renumeramos:
 *   etapas actuales 3→4  quedan  3→4 sin cambio
 *   etapa nueva "2b" recibe orden = 10
 *   etapa nueva "3b" recibe orden = 11
 * Y actualizamos la lógica de detección en el frontend por etapaId, no orden.
 *
 * Estrategia simple: las nuevas etapas toman ordenes 10 y 11 para no
 * desplazar las existentes y evitar conflictos de FK.
 */

/* ── 1. Insertar las dos nuevas etapas ────────────────────────────────── */

INSERT INTO etapa (
    nombre,
    descripcion,
    objetivo_pedagogico,
    orden,
    created_at,
    updated_at
)
VALUES
(
    'Etapa 2b - Memoria de vocales',
    'Empareja vocales mayúsculas con sus versiones minúsculas.',
    'Reforzar que cada vocal tiene dos formas visuales distintas mediante un juego de memoria.',
    10,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'Etapa 3b - Memoria de consonantes',
    'Empareja consonantes con imágenes de palabras que empiezan con esa letra.',
    'Conectar el sonido de la consonante con una palabra real de forma lúdica.',
    11,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

/* ── 2. Reubicar las actividades EMPAREJAR a las etapas recién creadas ── */

UPDATE actividad
SET etapa_id = (SELECT id FROM etapa WHERE orden = 10),
    updated_at = CURRENT_TIMESTAMP
WHERE tipo_actividad = 'EMPAREJAR'
  AND etapa_id = (SELECT id FROM etapa WHERE orden = 2);

UPDATE actividad
SET etapa_id = (SELECT id FROM etapa WHERE orden = 11),
    updated_at = CURRENT_TIMESTAMP
WHERE tipo_actividad = 'EMPAREJAR'
  AND etapa_id = (SELECT id FROM etapa WHERE orden = 3);
