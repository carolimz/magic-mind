/*
 * V29: Restaurar actividades originales y crear nuevas variantes B
 * 
 * 1. Restaura las actividades de Reconocimiento de consonantes y 
 *    Comprensión lectora integral a su estado original (deshace V28).
 * 2. Crea la Etapa 2D para el nuevo juego "Diagnóstico Abecedario" (ALPHABET_GRID).
 * 3. Crea la Etapa 9B para Lecturas Inferenciales Avanzadas.
 */

-- 1. RESTAURAR "Reconoce las consonantes" a su estado original
UPDATE actividad
SET configuracion = $json$
{
    "items":[
        {"id": 1, "pregunta":"Selecciona la consonante M", "opciones":["M","A","E","I"], "respuestaCorrecta":"M", "recurso":""},
        {"id": 2, "pregunta":"Selecciona la consonante P", "opciones":["A","P","O","U"], "respuestaCorrecta":"P", "recurso":""},
        {"id": 3, "pregunta":"Selecciona la consonante S", "opciones":["E","I","S","A"], "respuestaCorrecta":"S", "recurso":""},
        {"id": 4, "pregunta":"Selecciona la consonante L", "opciones":["L","O","U","A"], "respuestaCorrecta":"L", "recurso":""},
        {"id": 5, "pregunta":"Selecciona la consonante T", "opciones":["I","E","T","O"], "respuestaCorrecta":"T", "recurso":""}
    ]
}
$json$::jsonb, updated_at = CURRENT_TIMESTAMP
WHERE nombre = 'Reconoce las consonantes';

-- 2. RESTAURAR "Comprensión lectora integral" a su estado original
UPDATE actividad
SET configuracion = $json$
{
    "items":[
        {"id": 1, "texto":"María fue al parque con su hermano. Jugaron en los columpios y luego regresaron felices a casa.", "pregunta":"¿A dónde regresaron María y su hermano?", "opciones":["Al colegio","Al parque","A casa","A la tienda"], "respuestaCorrecta":"A casa", "recurso":""},
        {"id": 2, "texto":"Juan leyó un libro antes de dormir.", "pregunta":"¿Qué hizo Juan antes de dormir?", "opciones":["Jugó","Leyó un libro","Corrió","Comió"], "respuestaCorrecta":"Leyó un libro", "recurso":""},
        {"id": 3, "texto":"Laura regó las flores porque hacía mucho calor.", "pregunta":"¿Por qué Laura regó las flores?", "opciones":["Porque llovía","Porque hacía calor","Porque era de noche","Porque estaban secas de pintura"], "respuestaCorrecta":"Porque hacía calor", "recurso":""},
        {"id": 4, "texto":"Carlos desayunó cereal con leche antes de ir a clases.", "pregunta":"¿Qué desayunó Carlos?", "opciones":["Pan","Cereal con leche","Fruta","Sopa"], "respuestaCorrecta":"Cereal con leche", "recurso":""},
        {"id": 5, "texto":"Sofía encontró un cachorro perdido y llamó a su mamá para ayudarlo.", "pregunta":"¿Qué encontró Sofía?", "opciones":["Un gato","Un cachorro","Un pájaro","Un conejo"], "respuestaCorrecta":"Un cachorro", "recurso":""}
    ]
}
$json$::jsonb, updated_at = CURRENT_TIMESTAMP
WHERE nombre = 'Comprensión lectora integral';

-- 3. DESPLAZAR ORDENES PARA HACER ESPACIO PARA 2D (Diagnóstico Abecedario) en orden 6 y 9B (Lecturas Inferenciales) en orden 17
-- Desplazamos temporalmente a negativo
UPDATE etapa SET orden = -orden;

-- Reasignar de manera explícita respetando V27 y agregando las nuevas:
UPDATE etapa SET orden = 1 WHERE nombre = 'Etapa 1 - Exploración espacial';
UPDATE etapa SET orden = 2 WHERE nombre = 'Etapa 1B - Simón Dice';
UPDATE etapa SET orden = 3 WHERE nombre = 'Etapa 2 - Reconocimiento de vocales';
UPDATE etapa SET orden = 4 WHERE nombre = 'Etapa 2B - Memoria de vocales';
UPDATE etapa SET orden = 5 WHERE nombre = 'Etapa 2C - Verdadero o Falso';
-- AQUI IRA LA NUEVA ETAPA 2D (Orden 6)
UPDATE etapa SET orden = 7 WHERE nombre = 'Etapa 3 - Reconocimiento de consonantes';
UPDATE etapa SET orden = 8 WHERE nombre = 'Etapa 3B - Memoria de consonantes';
UPDATE etapa SET orden = 9 WHERE nombre = 'Etapa 4 - Formación de sílabas directas';
UPDATE etapa SET orden = 10 WHERE nombre = 'Etapa 5 - Completar palabras';
UPDATE etapa SET orden = 11 WHERE nombre = 'Etapa 5B - Adivina la Sombra';
UPDATE etapa SET orden = 12 WHERE nombre = 'Etapa 6 - Sílabas complejas';
UPDATE etapa SET orden = 13 WHERE nombre = 'Etapa 7 - Completar frases';
UPDATE etapa SET orden = 14 WHERE nombre = 'Etapa 8 - Comprensión lectora';
UPDATE etapa SET orden = 15 WHERE nombre = 'Etapa 8B - Ordena la historia';
UPDATE etapa SET orden = 16 WHERE nombre = 'Etapa 9 - Comprensión lectora integral';
-- AQUI IRA LA NUEVA ETAPA 9B (Orden 17)

-- Devolver a positivo a las que no hicimos match por si acaso
UPDATE etapa SET orden = -orden + 100 WHERE orden < 0;


-- 4. INSERTAR ETAPA 2D - DIAGNÓSTICO ABECEDARIO (Orden 6)
INSERT INTO etapa (nombre, descripcion, objetivo_pedagogico, orden, activo, created_at, updated_at)
VALUES (
    'Etapa 2D - Diagnóstico Abecedario',
    'Un panel con todo el abecedario para diagnosticar qué letras conoce el estudiante.',
    'Diagnosticar el nivel de reconocimiento de todas las letras del alfabeto.',
    6, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

INSERT INTO actividad (nombre, descripcion, tipo_actividad, dificultad, configuracion, activo, etapa_id, created_at, updated_at)
VALUES (
    'Diagnóstico Abecedario',
    'Escucha la letra y encuéntrala en el panel mágico.',
    'ALPHABET_GRID',
    'MEDIA',
    $json$
    {
      "items": [
        {"id": 1, "pregunta": "Encuentra la letra M", "respuestaCorrecta": "M"},
        {"id": 2, "pregunta": "Encuentra la letra P", "respuestaCorrecta": "P"},
        {"id": 3, "pregunta": "Encuentra la letra S", "respuestaCorrecta": "S"},
        {"id": 4, "pregunta": "Encuentra la letra L", "respuestaCorrecta": "L"},
        {"id": 5, "pregunta": "Encuentra la letra B", "respuestaCorrecta": "B"},
        {"id": 6, "pregunta": "Encuentra la letra F", "respuestaCorrecta": "F"},
        {"id": 7, "pregunta": "Encuentra la letra R", "respuestaCorrecta": "R"},
        {"id": 8, "pregunta": "Encuentra la letra C", "respuestaCorrecta": "C"},
        {"id": 9, "pregunta": "Encuentra la letra D", "respuestaCorrecta": "D"},
        {"id": 10, "pregunta": "Encuentra la letra G", "respuestaCorrecta": "G"}
      ]
    }
    $json$::jsonb,
    TRUE,
    (SELECT id FROM etapa WHERE orden = 6 LIMIT 1),
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- 5. INSERTAR ETAPA 9B - LECTURAS INFERENCIALES AVANZADAS (Orden 17)
INSERT INTO etapa (nombre, descripcion, objetivo_pedagogico, orden, activo, created_at, updated_at)
VALUES (
    'Etapa 9B - Lecturas Inferenciales Avanzadas',
    'Lecturas más largas donde las respuestas no están explícitas y deben deducirse.',
    'Desarrollar la comprensión inferencial y el análisis de textos largos.',
    17, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

INSERT INTO actividad (nombre, descripcion, tipo_actividad, dificultad, configuracion, activo, etapa_id, created_at, updated_at)
VALUES (
    'Lecturas Inferenciales',
    'Lee atentamente la historia y deduce la respuesta correcta.',
    'SELECCION',
    'DIFICIL',
    $json$
    {
      "items": [
        {
          "id": 1,
          "texto": "Había una vez un perrito llamado Toby. Toby vivía en una granja muy grande con vacas, gallinas y caballos. Un día, mientras Toby perseguía una mariposa amarilla, escuchó un ruido extraño que venía del granero. Al acercarse lentamente, descubrió que era un pequeño gatito asustado que se había perdido. Toby, siendo muy amable, le compartió su comida y se hicieron grandes amigos.",
          "pregunta": "¿Por qué el gatito estaba haciendo ruido en el granero?",
          "opciones": ["Porque tenía hambre", "Porque estaba asustado y perdido", "Porque quería jugar con Toby", "Porque estaba cantando"],
          "respuestaCorrecta": "Porque estaba asustado y perdido",
          "recurso": ""
        },
        {
          "id": 2,
          "texto": "El sol brillaba muy fuerte y no había ni una sola nube en el cielo. Carlos llegó a su casa sudando y con mucha sed. Abrió la nevera rápidamente, sacó una jarra de limonada fría y se sirvió un vaso grande. Después se sentó frente al ventilador cerrando los ojos.",
          "pregunta": "¿Qué podemos deducir sobre el clima en la historia?",
          "opciones": ["Estaba lloviendo mucho", "Hacía muchísimo calor", "Estaba nevando", "Hacía mucho viento frío"],
          "respuestaCorrecta": "Hacía muchísimo calor",
          "recurso": ""
        },
        {
          "id": 3,
          "texto": "Marta miró su reloj y comenzó a correr hacia la estación. Llevaba su pesada mochila saltando en su espalda. Cuando llegó al andén, vio las puertas cerrarse y la gran máquina de metal comenzó a alejarse sobre los rieles. Marta suspiró y se sentó en la banca a esperar el siguiente.",
          "pregunta": "¿Qué le pasó a Marta?",
          "opciones": ["Se subió al tren a tiempo", "Perdió el tren por llegar tarde", "Iba a viajar en avión", "Se olvidó la mochila"],
          "respuestaCorrecta": "Perdió el tren por llegar tarde",
          "recurso": ""
        }
      ]
    }
    $json$::jsonb,
    TRUE,
    (SELECT id FROM etapa WHERE orden = 17 LIMIT 1),
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
