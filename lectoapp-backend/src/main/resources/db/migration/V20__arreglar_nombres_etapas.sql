/*
 * V20: Dar coherencia a los nombres de las etapas para que coincidan con su orden cronológico
 * en el frontend (mapa de la ruta de aprendizaje).
 */

-- La etapa "Ordena la historia" está en la posición 11, pero se llamaba "Etapa 8B".
UPDATE etapa
SET nombre = 'Etapa 11 - Ordena la historia',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 11;

-- La etapa "Comprensión lectora integral" está en la posición 13, pero se llamaba "Etapa 9".
UPDATE etapa
SET nombre = 'Etapa 13 - Comprensión lectora integral',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 13;
