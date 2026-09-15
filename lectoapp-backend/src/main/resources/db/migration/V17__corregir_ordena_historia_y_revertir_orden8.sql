/*
 * 1. RESTAURAR LA ETAPA 8 A SU ESTADO ORIGINAL ("Sílabas complejas")
 *    En V14, V15 y V16 sobrescribimos por error la etapa con orden = 8.
 *    Originalmente, debido a V13, la etapa con orden = 8 es "Sílabas complejas".
 */

-- Restaurar el nombre de la etapa
UPDATE etapa
SET nombre = 'Etapa 6 - Sílabas complejas',
    descripcion = 'Aprende y forma sílabas con grupos consonánticos como pr, bl, tr.',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 8;

-- Restaurar la actividad de la etapa 8
UPDATE actividad
SET nombre = 'Reconoce sílabas complejas',
    descripcion = 'Selecciona la sílaba compleja que completa cada palabra.',
    tipo_actividad = 'SELECCION',
    dificultad = 'MEDIA',
    configuracion = $json$
    {
      "items": [
        {"id":1,"pregunta":"Completa la palabra _ T O","opciones":["PLA","PRA","BRA","GRA"],"respuestaCorrecta":"PLA","recurso":"plato.png"},
        {"id":2,"pregunta":"Completa la palabra _ J A","opciones":["BRU","BLU","GRU","DRU"],"respuestaCorrecta":"BRU","recurso":"bruja.png"},
        {"id":3,"pregunta":"Completa la palabra _ N","opciones":["TRE","PRE","CRE","GRE"],"respuestaCorrecta":"TRE","recurso":"tren.png"},
        {"id":4,"pregunta":"Completa la palabra _ S A","opciones":["FRE","CRE","BRE","DRE"],"respuestaCorrecta":"FRE","recurso":"fresa.png"},
        {"id":5,"pregunta":"Completa la palabra _ N C O","opciones":["BLA","CLA","PLA","GRA"],"respuestaCorrecta":"BLA","recurso":"blanco.png"},
        {"id":6,"pregunta":"Completa la palabra _ B O","opciones":["GLO","CLO","PLO","BLO"],"respuestaCorrecta":"GLO","recurso":"globo.png"},
        {"id":7,"pregunta":"Completa la palabra _ M A","opciones":["CRE","PRE","GRE","BRE"],"respuestaCorrecta":"CRE","recurso":"crema.png"},
        {"id":8,"pregunta":"Completa la palabra _ T A","opciones":["FRU","BRU","GRU","CRU"],"respuestaCorrecta":"FRU","recurso":"fruta.png"}
      ]
    }
    $json$::jsonb,
    activo = TRUE,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 8);


/*
 * 2. DESPLAZAR LA ETAPA 11 ("Comprensión integral") A LA POSICIÓN 12
 *    Para hacer espacio para la Variante B en la posición 11.
 */
UPDATE etapa SET orden = 12, updated_at = CURRENT_TIMESTAMP WHERE orden = 11;


/*
 * 3. CREAR LA NUEVA ETAPA "ORDENA LA HISTORIA" (VARIANTE B) EN EL ORDEN 11
 *    (Justo después de "Etapa 8 - Comprensión lectora" que está en el orden 10).
 */
INSERT INTO etapa (nombre, descripcion, objetivo_pedagogico, orden, activo, created_at, updated_at)
VALUES (
    'Etapa 8B - Ordena la historia',
    'Escucha el cuento y ordena las imágenes en la secuencia correcta.',
    'Refuerza la noción de secuencia narrativa',
    11,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

/*
 * 4. CREAR LA ACTIVIDAD PARA LA NUEVA ETAPA
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
    'Ordena la historia',
    'Escucha el cuento y arrastra las imágenes para ordenarlas.',
    'ORDENAR_SECUENCIA',
    'MEDIA',
    $json$
    {
      "items": [
        {
          "id": 1,
          "texto": "La liebre corría muy rápido y se quedó dormida bajo un árbol. La tortuga caminó lento pero sin parar y ganó la carrera.",
          "pregunta": "Ordena la historia",
          "opciones": ["liebre_corriendo.png", "liebre_durmiendo.png", "tortuga_ganando.png"],
          "respuestaCorrecta": "liebre_corriendo.png,liebre_durmiendo.png,tortuga_ganando.png"
        },
        {
          "id": 2,
          "texto": "Juan sembró una pequeña semilla en la maceta. Todos los días le echó agua con su regadera, y por fin nació una hermosa flor amarilla.",
          "pregunta": "Ordena la historia",
          "opciones": ["sembrando.png", "regando.png", "flor_crecida.png"],
          "respuestaCorrecta": "sembrando.png,regando.png,flor_crecida.png"
        },
        {
          "id": 3,
          "texto": "Mamá mezcló la harina y el azúcar en un tazón. Luego amasó la masa con sus manos y finalmente metió las galletas al horno.",
          "pregunta": "Ordena la historia",
          "opciones": ["mezclando.png", "amasando.png", "horneando.png"],
          "respuestaCorrecta": "mezclando.png,amasando.png,horneando.png"
        },
        {
          "id": 4,
          "texto": "El cielo se llenó de nubes grises. Empezó a caer una lluvia muy fuerte, así que abrí mi paraguas para no mojarme.",
          "pregunta": "Ordena la historia",
          "opciones": ["nubes.png", "lluvia.png", "paraguas.png"],
          "respuestaCorrecta": "nubes.png,lluvia.png,paraguas.png"
        }
      ]
    }
    $json$::jsonb,
    TRUE,
    (SELECT id FROM etapa WHERE orden = 11),
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
