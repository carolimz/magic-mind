/*
 * V21: Reorganizar los nombres de las etapas usando el sistema de variantes (A, B, C)
 * para no exceder las 9 etapas pedagógicas principales.
 */

-- La etapa en la posición 11 vuelve a ser 8B
UPDATE etapa
SET nombre = 'Etapa 8B - Ordena la historia',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 11;

-- La etapa en la posición 12 será 8C
UPDATE etapa
SET nombre = 'Etapa 8C - Verdadero o Falso',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 12;

-- La etapa final (posición 13) vuelve a ser la Etapa 9
UPDATE etapa
SET nombre = 'Etapa 9 - Comprensión lectora integral',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 13;
