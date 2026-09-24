/*
 * V27: Reordenamiento maestro de todas las etapas
 * 
 * Se reasignan los órdenes y nombres de las etapas de manera absoluta,
 * ubicando los juegos nuevos ("Verdadero o Falso" y "Adivina la Sombra")
 * en posiciones tempranas (Etapa 2C y Etapa 5B respectivamente).
 */

-- 1. Desplazar todos los órdenes temporalmente a números negativos para 
--    evitar violaciones de clave única (UNIQUE constraint) si existiera.
UPDATE etapa SET orden = -orden;

-- 2. Asignar el nuevo orden cronológico identificando las etapas por la 
--    actividad que tienen asociada, para evitar fallos si los nombres cambiaron.

UPDATE etapa e SET orden = 1, nombre = 'Etapa 1 - Exploración espacial' 
FROM actividad a WHERE a.etapa_id = e.id AND a.nombre = 'Ubica el objeto';

UPDATE etapa e SET orden = 2, nombre = 'Etapa 1B - Simón Dice' 
FROM actividad a WHERE a.etapa_id = e.id AND a.tipo_actividad = 'SIMON_DICE';

UPDATE etapa e SET orden = 3, nombre = 'Etapa 2 - Reconocimiento de vocales' 
FROM actividad a WHERE a.etapa_id = e.id AND a.nombre = 'Reconoce las vocales';

UPDATE etapa e SET orden = 4, nombre = 'Etapa 2B - Memoria de vocales' 
FROM actividad a WHERE a.etapa_id = e.id AND a.nombre = 'Memoria de vocales';

UPDATE etapa e SET orden = 5, nombre = 'Etapa 2C - Verdadero o Falso' 
FROM actividad a WHERE a.etapa_id = e.id AND a.tipo_actividad = 'VERDADERO_FALSO';

UPDATE etapa e SET orden = 6, nombre = 'Etapa 3 - Reconocimiento de consonantes' 
FROM actividad a WHERE a.etapa_id = e.id AND a.nombre = 'Reconoce las consonantes';

UPDATE etapa e SET orden = 7, nombre = 'Etapa 3B - Memoria de consonantes' 
FROM actividad a WHERE a.etapa_id = e.id AND a.nombre = 'Memoria de consonantes';

UPDATE etapa e SET orden = 8, nombre = 'Etapa 4 - Formación de sílabas directas' 
FROM actividad a WHERE a.etapa_id = e.id AND a.nombre = 'Forma sílabas directas';

UPDATE etapa e SET orden = 9, nombre = 'Etapa 5 - Completar palabras' 
FROM actividad a WHERE a.etapa_id = e.id AND a.nombre = 'Completa la palabra';

UPDATE etapa e SET orden = 10, nombre = 'Etapa 5B - Adivina la Sombra' 
FROM actividad a WHERE a.etapa_id = e.id AND a.tipo_actividad = 'ADIVINA_LA_SOMBRA';

UPDATE etapa e SET orden = 11, nombre = 'Etapa 6 - Sílabas complejas' 
FROM actividad a WHERE a.etapa_id = e.id AND a.nombre = 'Reconoce sílabas complejas';

UPDATE etapa e SET orden = 12, nombre = 'Etapa 7 - Completar frases' 
FROM actividad a WHERE a.etapa_id = e.id AND a.nombre = 'Completa la frase';

UPDATE etapa e SET orden = 13, nombre = 'Etapa 8 - Comprensión lectora' 
FROM actividad a WHERE a.etapa_id = e.id AND a.nombre = 'Comprensión lectora';

UPDATE etapa e SET orden = 14, nombre = 'Etapa 8B - Ordena la historia' 
FROM actividad a WHERE a.etapa_id = e.id AND a.tipo_actividad = 'ORDENAR_SECUENCIA';

UPDATE etapa e SET orden = 15, nombre = 'Etapa 9 - Comprensión lectora integral' 
FROM actividad a WHERE a.etapa_id = e.id AND a.nombre = 'Comprensión lectora integral';

-- 3. Por precaución, cualquier etapa que no haya sido emparejada
--    la devolvemos a números positivos (hacia el final) para que no crashee.
UPDATE etapa SET orden = -orden + 100 WHERE orden < 0;
