/*
 * Corrige la alineación pedagógica entre las etapas y las actividades
 * precargadas en V5__insertar_datos_iniciales.sql.
 *
 * No modifica actividades, identificadores ni resultados existentes.
 */

UPDATE etapa
SET
    nombre = CASE orden
        WHEN 2 THEN 'Etapa 2 - Reconocimiento de vocales'
        WHEN 3 THEN 'Etapa 3 - Reconocimiento de consonantes'
        WHEN 4 THEN 'Etapa 4 - Formación de sílabas directas'
        WHEN 5 THEN 'Etapa 5 - Completar palabras'
        WHEN 6 THEN 'Etapa 6 - Sílabas complejas'
        WHEN 7 THEN 'Etapa 7 - Completar frases'
        WHEN 8 THEN 'Etapa 8 - Comprensión lectora'
        WHEN 9 THEN 'Etapa 9 - Comprensión lectora integral'
        ELSE nombre
    END,

    descripcion = CASE orden
        WHEN 2 THEN 'Identifica y reconoce las vocales.'
        WHEN 3 THEN 'Identifica y reconoce consonantes básicas.'
        WHEN 4 THEN 'Forma sílabas directas uniendo consonantes y vocales.'
        WHEN 5 THEN 'Completa palabras utilizando la letra correcta.'
        WHEN 6 THEN 'Reconoce sílabas complejas y completa palabras.'
        WHEN 7 THEN 'Completa oraciones utilizando palabras adecuadas.'
        WHEN 8 THEN 'Lee textos cortos y responde preguntas de comprensión.'
        WHEN 9 THEN 'Integra las habilidades adquiridas para comprender textos.'
        ELSE descripcion
    END,

    objetivo_pedagogico = CASE orden
        WHEN 2 THEN 'Fortalecer el reconocimiento visual de las vocales.'
        WHEN 3 THEN 'Fortalecer el reconocimiento visual de consonantes básicas.'
        WHEN 4 THEN 'Desarrollar la unión de consonantes y vocales para formar sílabas directas.'
        WHEN 5 THEN 'Mejorar el reconocimiento y la escritura de palabras básicas.'
        WHEN 6 THEN 'Ampliar la capacidad para reconocer estructuras silábicas complejas.'
        WHEN 7 THEN 'Desarrollar la comprensión semántica y la coherencia en frases.'
        WHEN 8 THEN 'Mejorar la comprensión de textos breves y la interpretación literal.'
        WHEN 9 THEN 'Fortalecer la comprensión integral y autónoma de textos simples.'
        ELSE objetivo_pedagogico
    END,

    updated_at = CURRENT_TIMESTAMP

WHERE orden BETWEEN 2 AND 9;