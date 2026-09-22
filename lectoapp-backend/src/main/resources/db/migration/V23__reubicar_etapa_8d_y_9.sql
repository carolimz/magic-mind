/*
 * V23: Reubicar Etapa 8D para que aparezca ANTES de la Etapa 9
 * en el mapa del estudiante, respetando el orden cronológico.
 */

-- 1. Mover temporalmente la Etapa 8D a un número alto para liberar el orden 14 y evitar conflicto de unicidad
UPDATE etapa
SET orden = 999,
    updated_at = CURRENT_TIMESTAMP
WHERE nombre = 'Etapa 8D - Adivina la Sombra';

-- 2. Empujar la "Etapa 9 - Comprensión lectora integral" (que estaba en 13) hacia abajo (orden 14)
UPDATE etapa
SET orden = 14,
    updated_at = CURRENT_TIMESTAMP
WHERE nombre = 'Etapa 9 - Comprensión lectora integral';

-- 3. Subir la "Etapa 8D - Adivina la Sombra" a la posición 13
UPDATE etapa
SET orden = 13,
    updated_at = CURRENT_TIMESTAMP
WHERE nombre = 'Etapa 8D - Adivina la Sombra';
