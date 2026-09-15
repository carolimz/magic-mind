/*
 * Reordena las etapas para que los juegos de memoria queden intercalados
 * entre las etapas de vocales/consonantes y las siguientes etapas.
 *
 * Orden final deseado:
 *   1  → Exploración espacial
 *   2  → Vocales (burbujas)
 *   3  → Memoria de vocales         ← era orden=10
 *   4  → Consonantes (burbujas)     ← era orden=3
 *   5  → Memoria de consonantes     ← era orden=11
 *   6  → Sílabas directas           ← era orden=4
 *   7  → Completar palabras         ← era orden=5
 *   8  → Sílabas complejas          ← era orden=6
 *   9  → Completar frases           ← era orden=7
 *  10  → Comprensión básica         ← era orden=8
 *  11  → Comprensión integral       ← era orden=9
 *
 * IMPORTANTE: usamos valores temporales altos para evitar
 * conflicto de UNIQUE CONSTRAINT en el campo `orden`.
 */

/* ── Paso 1: Mover todo a valores temporales (>100) ──────────────────── */

UPDATE etapa SET orden = 101 WHERE orden = 1;
UPDATE etapa SET orden = 102 WHERE orden = 2;
UPDATE etapa SET orden = 110 WHERE orden = 10;  -- memoria vocales
UPDATE etapa SET orden = 103 WHERE orden = 3;
UPDATE etapa SET orden = 111 WHERE orden = 11;  -- memoria consonantes
UPDATE etapa SET orden = 104 WHERE orden = 4;
UPDATE etapa SET orden = 105 WHERE orden = 5;
UPDATE etapa SET orden = 106 WHERE orden = 6;
UPDATE etapa SET orden = 107 WHERE orden = 7;
UPDATE etapa SET orden = 108 WHERE orden = 8;
UPDATE etapa SET orden = 109 WHERE orden = 9;

UPDATE etapa SET updated_at = CURRENT_TIMESTAMP
WHERE orden BETWEEN 101 AND 120;

/* ── Paso 2: Asignar los órdenes definitivos ─────────────────────────── */

UPDATE etapa SET orden = 1  WHERE orden = 101;  -- Exploración espacial
UPDATE etapa SET orden = 2  WHERE orden = 102;  -- Vocales (burbujas)
UPDATE etapa SET orden = 3  WHERE orden = 110;  -- Memoria de vocales ← NUEVA
UPDATE etapa SET orden = 4  WHERE orden = 103;  -- Consonantes (burbujas)
UPDATE etapa SET orden = 5  WHERE orden = 111;  -- Memoria de consonantes ← NUEVA
UPDATE etapa SET orden = 6  WHERE orden = 104;  -- Sílabas directas
UPDATE etapa SET orden = 7  WHERE orden = 105;  -- Completar palabras
UPDATE etapa SET orden = 8  WHERE orden = 106;  -- Sílabas complejas
UPDATE etapa SET orden = 9  WHERE orden = 107;  -- Completar frases
UPDATE etapa SET orden = 10 WHERE orden = 108;  -- Comprensión básica
UPDATE etapa SET orden = 11 WHERE orden = 109;  -- Comprensión integral

UPDATE etapa SET updated_at = CURRENT_TIMESTAMP
WHERE orden BETWEEN 1 AND 11;
