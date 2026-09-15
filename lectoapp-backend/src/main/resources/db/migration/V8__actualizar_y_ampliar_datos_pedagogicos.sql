/*
 * Corrige y amplía la ruta pedagógica de LectoApp.
 *
 * Esta migración no elimina etapas ni actividades y, por tanto, conserva
 * los identificadores que ya están relacionados con resultados históricos.
 * Cada actividad queda con un banco de ocho ejercicios.
 */

/* --------------------------------------------------------------------------
 * 1. ETAPAS
 * -------------------------------------------------------------------------- */

UPDATE etapa
SET nombre = 'Etapa 1 - Exploración espacial',
    descripcion = 'Descubre posiciones y direcciones básicas.',
    objetivo_pedagogico = 'Desarrollar la orientación espacial mediante el reconocimiento de arriba, abajo, izquierda y derecha.',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 1;

UPDATE etapa
SET nombre = 'Etapa 2 - Reconocimiento de vocales',
    descripcion = 'Identifica y reconoce las vocales.',
    objetivo_pedagogico = 'Reconocer visual y auditivamente las vocales A, E, I, O y U.',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 2;

UPDATE etapa
SET nombre = 'Etapa 3 - Reconocimiento de consonantes',
    descripcion = 'Identifica y reconoce consonantes básicas.',
    objetivo_pedagogico = 'Diferenciar consonantes frecuentes y algunas parejas con semejanza visual o fonológica.',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 3;

UPDATE etapa
SET nombre = 'Etapa 4 - Formación de sílabas directas',
    descripcion = 'Forma sílabas directas uniendo consonantes y vocales.',
    objetivo_pedagogico = 'Comprender la unión consonante-vocal para construir sílabas directas.',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 4;

UPDATE etapa
SET nombre = 'Etapa 5 - Completar palabras',
    descripcion = 'Completa palabras utilizando la vocal correcta.',
    objetivo_pedagogico = 'Reconocer la vocal que falta dentro de palabras cotidianas.',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 5;

UPDATE etapa
SET nombre = 'Etapa 6 - Sílabas complejas',
    descripcion = 'Reconoce sílabas complejas y completa palabras.',
    objetivo_pedagogico = 'Identificar grupos consonánticos frecuentes como PL, BR, TR, FR, BL, GL y CR.',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 6;

UPDATE etapa
SET nombre = 'Etapa 7 - Completar frases',
    descripcion = 'Completa oraciones utilizando palabras adecuadas.',
    objetivo_pedagogico = 'Desarrollar comprensión semántica mediante oraciones simples y vocabulario cotidiano.',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 7;

UPDATE etapa
SET nombre = 'Etapa 8 - Comprensión lectora',
    descripcion = 'Lee textos cortos y responde preguntas de comprensión.',
    objetivo_pedagogico = 'Localizar información explícita en textos breves.',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 8;

UPDATE etapa
SET nombre = 'Etapa 9 - Comprensión lectora integral',
    descripcion = 'Integra las habilidades adquiridas para comprender textos.',
    objetivo_pedagogico = 'Comprender secuencias, causas, acciones y detalles en relatos breves.',
    updated_at = CURRENT_TIMESTAMP
WHERE orden = 9;

/* --------------------------------------------------------------------------
 * 2. ACTIVIDAD DE LA ETAPA 1: ORIENTACIÓN ESPACIAL
 * -------------------------------------------------------------------------- */

UPDATE actividad
SET nombre = 'Ubica el objeto',
    descripcion = 'Reconoce hacia dónde se encuentra o se mueve cada objeto.',
    tipo_actividad = 'SELECCION',
    dificultad = 'FACIL',
    configuracion = $json$
    {
      "items": [
        {"id":1,"pregunta":"¿Hacia dónde apunta la flecha?","opciones":["Arriba","Abajo","Izquierda","Derecha"],"respuestaCorrecta":"Arriba","recurso":"flecha_arriba.png"},
        {"id":2,"pregunta":"¿Hacia dónde apunta la flecha?","opciones":["Arriba","Abajo","Izquierda","Derecha"],"respuestaCorrecta":"Abajo","recurso":"flecha_abajo.png"},
        {"id":3,"pregunta":"¿Hacia dónde apunta la flecha?","opciones":["Arriba","Abajo","Izquierda","Derecha"],"respuestaCorrecta":"Izquierda","recurso":"flecha_izquierda.png"},
        {"id":4,"pregunta":"¿Hacia dónde apunta la flecha?","opciones":["Arriba","Abajo","Izquierda","Derecha"],"respuestaCorrecta":"Derecha","recurso":"flecha_derecha.png"},
        {"id":5,"pregunta":"¿Dónde está el gato?","opciones":["Arriba","Abajo","Izquierda","Derecha"],"respuestaCorrecta":"Arriba","recurso":"gato_arriba.png"},
        {"id":6,"pregunta":"¿Dónde está la pelota?","opciones":["Arriba","Abajo","Izquierda","Derecha"],"respuestaCorrecta":"Abajo","recurso":"pelota_abajo.png"},
        {"id":7,"pregunta":"¿Dónde está la estrella?","opciones":["Arriba","Abajo","Izquierda","Derecha"],"respuestaCorrecta":"Izquierda","recurso":"estrella_izquierda.png"},
        {"id":8,"pregunta":"¿Dónde está el avión?","opciones":["Arriba","Abajo","Izquierda","Derecha"],"respuestaCorrecta":"Derecha","recurso":"avion_derecha.png"}
      ]
    }
    $json$::jsonb,
    activo = TRUE,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 1);

/* --------------------------------------------------------------------------
 * 3. ACTIVIDAD DE LA ETAPA 2: VOCALES
 * -------------------------------------------------------------------------- */

UPDATE actividad
SET nombre = 'Reconoce las vocales',
    descripcion = 'Encuentra la vocal indicada entre varias letras.',
    tipo_actividad = 'SELECCION',
    dificultad = 'FACIL',
    configuracion = $json$
    {
      "items": [
        {"id":1,"pregunta":"Busca la vocal A","opciones":["A","M","P","S"],"respuestaCorrecta":"A","recurso":""},
        {"id":2,"pregunta":"Busca la vocal E","opciones":["T","L","E","R"],"respuestaCorrecta":"E","recurso":""},
        {"id":3,"pregunta":"Busca la vocal I","opciones":["B","I","F","N"],"respuestaCorrecta":"I","recurso":""},
        {"id":4,"pregunta":"Busca la vocal O","opciones":["O","P","M","T"],"respuestaCorrecta":"O","recurso":""},
        {"id":5,"pregunta":"Busca la vocal U","opciones":["L","U","R","S"],"respuestaCorrecta":"U","recurso":""},
        {"id":6,"pregunta":"Busca otra vez la vocal A","opciones":["E","A","O","U"],"respuestaCorrecta":"A","recurso":""},
        {"id":7,"pregunta":"Busca otra vez la vocal E","opciones":["I","O","A","E"],"respuestaCorrecta":"E","recurso":""},
        {"id":8,"pregunta":"Busca otra vez la vocal O","opciones":["U","A","O","I"],"respuestaCorrecta":"O","recurso":""}
      ]
    }
    $json$::jsonb,
    activo = TRUE,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 2);

/* --------------------------------------------------------------------------
 * 4. ACTIVIDAD DE LA ETAPA 3: CONSONANTES
 * -------------------------------------------------------------------------- */

UPDATE actividad
SET nombre = 'Reconoce las consonantes',
    descripcion = 'Encuentra la consonante indicada entre varias letras.',
    tipo_actividad = 'SELECCION',
    dificultad = 'FACIL',
    configuracion = $json$
    {
      "items": [
        {"id":1,"pregunta":"Busca la consonante M","opciones":["M","N","A","E"],"respuestaCorrecta":"M","recurso":""},
        {"id":2,"pregunta":"Busca la consonante N","opciones":["M","N","I","O"],"respuestaCorrecta":"N","recurso":""},
        {"id":3,"pregunta":"Busca la consonante P","opciones":["P","Q","A","U"],"respuestaCorrecta":"P","recurso":""},
        {"id":4,"pregunta":"Busca la consonante Q","opciones":["P","Q","E","O"],"respuestaCorrecta":"Q","recurso":""},
        {"id":5,"pregunta":"Busca la consonante B","opciones":["B","D","A","I"],"respuestaCorrecta":"B","recurso":""},
        {"id":6,"pregunta":"Busca la consonante D","opciones":["B","D","E","U"],"respuestaCorrecta":"D","recurso":""},
        {"id":7,"pregunta":"Busca la consonante S","opciones":["S","L","A","O"],"respuestaCorrecta":"S","recurso":""},
        {"id":8,"pregunta":"Busca la consonante T","opciones":["T","L","E","I"],"respuestaCorrecta":"T","recurso":""}
      ]
    }
    $json$::jsonb,
    activo = TRUE,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 3);

/* --------------------------------------------------------------------------
 * 5. ACTIVIDAD DE LA ETAPA 4: SÍLABAS DIRECTAS
 * -------------------------------------------------------------------------- */

UPDATE actividad
SET nombre = 'Forma sílabas directas',
    descripcion = 'Ordena una consonante y una vocal para formar la sílaba.',
    tipo_actividad = 'ARRASTRAR',
    dificultad = 'FACIL',
    configuracion = $json$
    {
      "items": [
        {"id":1,"letras":["M","A"],"respuestaCorrecta":"MA"},
        {"id":2,"letras":["P","A"],"respuestaCorrecta":"PA"},
        {"id":3,"letras":["S","O"],"respuestaCorrecta":"SO"},
        {"id":4,"letras":["L","U"],"respuestaCorrecta":"LU"},
        {"id":5,"letras":["T","E"],"respuestaCorrecta":"TE"},
        {"id":6,"letras":["N","I"],"respuestaCorrecta":"NI"},
        {"id":7,"letras":["D","O"],"respuestaCorrecta":"DO"},
        {"id":8,"letras":["B","E"],"respuestaCorrecta":"BE"}
      ]
    }
    $json$::jsonb,
    activo = TRUE,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 4);

/* --------------------------------------------------------------------------
 * 6. ACTIVIDAD DE LA ETAPA 5: COMPLETAR PALABRAS
 * -------------------------------------------------------------------------- */

UPDATE actividad
SET nombre = 'Completa la palabra',
    descripcion = 'Selecciona la vocal correcta para completar cada palabra.',
    tipo_actividad = 'SELECCION',
    dificultad = 'MEDIA',
    configuracion = $json$
    {
      "items": [
        {"id":1,"pregunta":"Completa la palabra C _ S A","opciones":["A","E","I","O"],"respuestaCorrecta":"A","recurso":"casa.png"},
        {"id":2,"pregunta":"Completa la palabra P _ T O","opciones":["A","E","I","U"],"respuestaCorrecta":"A","recurso":"pato.png"},
        {"id":3,"pregunta":"Completa la palabra M _ S A","opciones":["E","I","O","U"],"respuestaCorrecta":"E","recurso":"mesa.png"},
        {"id":4,"pregunta":"Completa la palabra S _ L","opciones":["A","E","O","U"],"respuestaCorrecta":"O","recurso":"sol.png"},
        {"id":5,"pregunta":"Completa la palabra L _ N A","opciones":["U","O","I","E"],"respuestaCorrecta":"U","recurso":"luna.png"},
        {"id":6,"pregunta":"Completa la palabra G _ T O","opciones":["A","E","I","O"],"respuestaCorrecta":"A","recurso":"gato.png"},
        {"id":7,"pregunta":"Completa la palabra P _ R A","opciones":["A","E","I","O"],"respuestaCorrecta":"E","recurso":"pera.png"},
        {"id":8,"pregunta":"Completa la palabra M _ T O","opciones":["A","E","I","O"],"respuestaCorrecta":"O","recurso":"moto.png"}
      ]
    }
    $json$::jsonb,
    activo = TRUE,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 5);

/* --------------------------------------------------------------------------
 * 7. ACTIVIDAD DE LA ETAPA 6: SÍLABAS COMPLEJAS
 * -------------------------------------------------------------------------- */

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
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 6);

/* --------------------------------------------------------------------------
 * 8. ACTIVIDAD DE LA ETAPA 7: COMPLETAR FRASES
 * -------------------------------------------------------------------------- */

UPDATE actividad
SET nombre = 'Completa la frase',
    descripcion = 'Selecciona la palabra que completa correctamente cada oración.',
    tipo_actividad = 'SELECCION',
    dificultad = 'MEDIA',
    configuracion = $json$
    {
      "items": [
        {"id":1,"pregunta":"El perro ____ en el parque.","opciones":["corre","mesa","amarillo","ventana"],"respuestaCorrecta":"corre","recurso":"perro_parque.png"},
        {"id":2,"pregunta":"La niña come una ____.","opciones":["manzana","silla","pelota","camisa"],"respuestaCorrecta":"manzana","recurso":"manzana.png"},
        {"id":3,"pregunta":"El pez nada en el ____.","opciones":["agua","árbol","cielo","carro"],"respuestaCorrecta":"agua","recurso":"pez.png"},
        {"id":4,"pregunta":"El pájaro vuela en el ____.","opciones":["cielo","mar","suelo","río"],"respuestaCorrecta":"cielo","recurso":"pajaro.png"},
        {"id":5,"pregunta":"La vaca da ____.","opciones":["leche","pan","jugo","arroz"],"respuestaCorrecta":"leche","recurso":"vaca.png"},
        {"id":6,"pregunta":"El niño duerme en la ____.","opciones":["cama","nube","mesa","calle"],"respuestaCorrecta":"cama","recurso":"nino_durmiendo.png"},
        {"id":7,"pregunta":"La rana salta en el ____.","opciones":["charco","techo","carro","plato"],"respuestaCorrecta":"charco","recurso":"rana.png"},
        {"id":8,"pregunta":"Mamá cocina en la ____.","opciones":["cocina","escuela","piscina","calle"],"respuestaCorrecta":"cocina","recurso":"mama_cocinando.png"}
      ]
    }
    $json$::jsonb,
    activo = TRUE,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 7);

/* --------------------------------------------------------------------------
 * 9. ACTIVIDAD DE LA ETAPA 8: COMPRENSIÓN LITERAL
 * -------------------------------------------------------------------------- */

UPDATE actividad
SET nombre = 'Comprensión lectora',
    descripcion = 'Lee o escucha un texto corto y encuentra información explícita.',
    tipo_actividad = 'SELECCION',
    dificultad = 'DIFICIL',
    configuracion = $json$
    {
      "items": [
        {"id":1,"texto":"Ana tiene un gato. Todos los días juega con él en el jardín.","pregunta":"¿Con quién juega Ana?","opciones":["Con un perro","Con un gato","Con un conejo","Con un pájaro"],"respuestaCorrecta":"Con un gato","recurso":"ana_gato.png"},
        {"id":2,"texto":"Luis fue al colegio en bicicleta.","pregunta":"¿En qué fue Luis al colegio?","opciones":["En bus","En bicicleta","En carro","En moto"],"respuestaCorrecta":"En bicicleta","recurso":"luis_bicicleta.png"},
        {"id":3,"texto":"María compró pan en la tienda.","pregunta":"¿Qué compró María?","opciones":["Leche","Pan","Queso","Jugo"],"respuestaCorrecta":"Pan","recurso":"maria_tienda.png"},
        {"id":4,"texto":"Pedro juega fútbol con sus amigos.","pregunta":"¿Qué deporte juega Pedro?","opciones":["Baloncesto","Fútbol","Tenis","Natación"],"respuestaCorrecta":"Fútbol","recurso":"pedro_futbol.png"},
        {"id":5,"texto":"El árbol tiene muchas hojas verdes.","pregunta":"¿De qué color son las hojas?","opciones":["Azules","Verdes","Rojas","Amarillas"],"respuestaCorrecta":"Verdes","recurso":"arbol_verde.png"},
        {"id":6,"texto":"Sofía guarda sus juguetes en una caja azul.","pregunta":"¿Dónde guarda Sofía sus juguetes?","opciones":["En una caja","En la cama","En una mochila","En la mesa"],"respuestaCorrecta":"En una caja","recurso":"sofia_juguetes.png"},
        {"id":7,"texto":"Tomás desayuna una arepa y toma leche.","pregunta":"¿Qué toma Tomás?","opciones":["Agua","Jugo","Leche","Sopa"],"respuestaCorrecta":"Leche","recurso":"tomas_desayuno.png"},
        {"id":8,"texto":"El conejo blanco come una zanahoria.","pregunta":"¿Qué come el conejo?","opciones":["Una manzana","Una zanahoria","Una pera","Una fresa"],"respuestaCorrecta":"Una zanahoria","recurso":"conejo_zanahoria.png"}
      ]
    }
    $json$::jsonb,
    activo = TRUE,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 8);

/* --------------------------------------------------------------------------
 * 10. ACTIVIDAD DE LA ETAPA 9: COMPRENSIÓN INTEGRAL
 * -------------------------------------------------------------------------- */

UPDATE actividad
SET nombre = 'Comprensión lectora integral',
    descripcion = 'Comprende acciones, secuencias y causas en relatos breves.',
    tipo_actividad = 'SELECCION',
    dificultad = 'DIFICIL',
    configuracion = $json$
    {
      "items": [
        {"id":1,"texto":"María fue al parque con su hermano. Jugaron en los columpios y luego regresaron felices a casa.","pregunta":"¿A dónde regresaron María y su hermano?","opciones":["Al colegio","Al parque","A casa","A la tienda"],"respuestaCorrecta":"A casa","recurso":"maria_parque.png"},
        {"id":2,"texto":"Juan guardó sus juguetes, se puso el pijama y leyó un libro antes de dormir.","pregunta":"¿Qué hizo Juan antes de dormir?","opciones":["Jugó fútbol","Leyó un libro","Salió al parque","Preparó el desayuno"],"respuestaCorrecta":"Leyó un libro","recurso":"juan_leyendo.png"},
        {"id":3,"texto":"Laura regó las flores porque hacía mucho calor y la tierra estaba seca.","pregunta":"¿Por qué Laura regó las flores?","opciones":["Porque hacía calor","Porque estaba lloviendo","Porque era de noche","Porque quería cortarlas"],"respuestaCorrecta":"Porque hacía calor","recurso":"laura_flores.png"},
        {"id":4,"texto":"Carlos desayunó cereal con leche y después preparó su mochila para ir a clases.","pregunta":"¿Qué hizo Carlos después de desayunar?","opciones":["Preparó su mochila","Volvió a dormir","Jugó en el parque","Cocinó la cena"],"respuestaCorrecta":"Preparó su mochila","recurso":"carlos_mochila.png"},
        {"id":5,"texto":"Sofía encontró un cachorro perdido. Llamó a su mamá y juntas buscaron a su dueño.","pregunta":"¿Para qué llamó Sofía a su mamá?","opciones":["Para ayudar al cachorro","Para comprar un juguete","Para preparar comida","Para ir al colegio"],"respuestaCorrecta":"Para ayudar al cachorro","recurso":"sofia_cachorro.png"},
        {"id":6,"texto":"Mateo sembró una semilla, la cubrió con tierra y todos los días le puso agua. Después de varias semanas nació una planta.","pregunta":"¿Qué hizo Mateo todos los días?","opciones":["Puso agua a la semilla","Cambió la semilla","Cortó la planta","Quitó la tierra"],"respuestaCorrecta":"Puso agua a la semilla","recurso":"mateo_planta.png"},
        {"id":7,"texto":"Elena llevó un paraguas porque el cielo estaba oscuro. Al salir de la escuela comenzó a llover.","pregunta":"¿Por qué Elena llevó un paraguas?","opciones":["Porque podía llover","Porque hacía calor","Porque iba a nadar","Porque perdió su mochila"],"respuestaCorrecta":"Porque podía llover","recurso":"elena_paraguas.png"},
        {"id":8,"texto":"Nicolás terminó su tarea y luego ayudó a su abuelo a ordenar los libros. Al final, los dos tomaron chocolate.","pregunta":"¿Qué hicieron después de ordenar los libros?","opciones":["Tomaron chocolate","Hicieron la tarea","Fueron al colegio","Compraron juguetes"],"respuestaCorrecta":"Tomaron chocolate","recurso":"nicolas_abuelo.png"}
      ]
    }
    $json$::jsonb,
    activo = TRUE,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 9);