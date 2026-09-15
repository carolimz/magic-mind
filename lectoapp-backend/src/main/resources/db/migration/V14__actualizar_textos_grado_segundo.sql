/* --------------------------------------------------------------------------
 * 1. ACTIVIDAD DE LA ETAPA 8: COMPRENSIÓN LITERAL Y SECUENCIAL (GRADO 2)
 * -------------------------------------------------------------------------- */

UPDATE actividad
SET configuracion = $json$
    {
      "items": [
        {"id":1,"texto":"Simón es un perrito blanco muy curioso. Esta mañana encontró una pelota roja escondida debajo del sofá y corrió al jardín para jugar con su amiga la gata Misi.","pregunta":"¿Dónde encontró Simón la pelota roja?","opciones":["Debajo del sofá","En el jardín","En la cocina","En la cama"],"respuestaCorrecta":"Debajo del sofá","recurso":"perrito_simon.png"},
        {"id":2,"texto":"Mateo y su abuelo vieron un pequeño colibrí en el árbol de guayabas. El pajarito construyó un nido con ramitas suaves y cuidó dos huevitos muy pequeños durante toda la tarde.","pregunta":"¿En qué árbol construyó el nido el colibrí?","opciones":["En el de guayabas","En el de naranjas","En el de mangos","En el de manzanas"],"respuestaCorrecta":"En el de guayabas","recurso":"colibri_nido.png"},
        {"id":3,"texto":"La abuela Rosa preparó un gran pastel de chocolate para el cumpleaños de Leo. Lo decoró con fresas frescas y colocó ocho velas de colores brillantes en la parte de arriba.","pregunta":"¿De qué sabor era el pastel que preparó la abuela?","opciones":["De vainilla","De chocolate","De fresa","De limón"],"respuestaCorrecta":"De chocolate","recurso":"pastel_abuela.png"},
        {"id":4,"texto":"Tres caracoles organizaron una carrera en el patio trasero. El caracol Tito fue el ganador porque descubrió un atajo secreto por debajo de las grandes hojas del helecho.","pregunta":"¿Por dónde descubrió un atajo el caracol Tito?","opciones":["Por debajo del helecho","Por encima de las piedras","Por el pasto alto","Por la pared de ladrillos"],"respuestaCorrecta":"Por debajo del helecho","recurso":"caracol_carrera.png"},
        {"id":5,"texto":"El valiente pirata Barba Roja encontró un antiguo mapa del tesoro dentro de una botella de cristal. Siguió las pistas hasta una playa de arena blanca donde desenterró un cofre lleno de monedas.","pregunta":"¿Dónde estaba guardado el antiguo mapa del tesoro?","opciones":["Dentro de un cofre","Dentro de una botella","Bajo la arena blanca","En un barco viejo"],"respuestaCorrecta":"Dentro de una botella","recurso":"pirata_mapa.png"},
        {"id":6,"texto":"El oso Benito se despertó con mucha hambre después del largo invierno. Caminó por el bosque hasta encontrar un panal de abejas lleno de rica miel dorada y se la comió toda.","pregunta":"¿Qué comió el oso Benito en el bosque?","opciones":["Frutas maduras","Miel dorada","Hojas verdes","Pescado fresco"],"respuestaCorrecta":"Miel dorada","recurso":"oso_miel.png"},
        {"id":7,"texto":"El pequeño robot Z-9 viajó en su nave plateada hasta llegar al planeta rojo. Allí recogió muestras de rocas brillantes para llevarlas de regreso a la Tierra.","pregunta":"¿A qué planeta viajó el pequeño robot Z-9?","opciones":["Al planeta azul","Al planeta rojo","Al planeta verde","Al planeta amarillo"],"respuestaCorrecta":"Al planeta rojo","recurso":"robot_espacio.png"},
        {"id":8,"texto":"Las mariquitas, las mariposas y los grillos organizaron una fiesta en el prado. Los grillos tocaron música alegre mientras todos bailaban bajo la luz de la luna llena.","pregunta":"¿Quiénes tocaron música en la fiesta del prado?","opciones":["Las mariquitas","Las mariposas","Los grillos","Las abejas"],"respuestaCorrecta":"Los grillos","recurso":"insectos_fiesta.png"}
      ]
    }
    $json$::jsonb,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 8);

/* --------------------------------------------------------------------------
 * 2. ACTIVIDAD DE LA ETAPA 9: COMPRENSIÓN INFERENCIAL (GRADO 2)
 * -------------------------------------------------------------------------- */

UPDATE actividad
SET configuracion = $json$
    {
      "items": [
        {"id":1,"texto":"Valentina pasó toda la tarde armando una cometa con papel de colores. Al día siguiente fue a la colina, pero las hojas de los árboles no se movían. Valentina se sentó a esperar pacientemente.","pregunta":"¿Por qué se sentó Valentina a esperar?","opciones":["Porque no había viento","Porque estaba muy cansada","Porque empezó a llover","Porque olvidó su cometa"],"respuestaCorrecta":"Porque no había viento","recurso":"cometa_viento.png"},
        {"id":2,"texto":"Lucas puso una semilla en un vaso con algodón húmedo. La colocó junto a la ventana para que recibiera luz. Días después, al ver brotar una raíz verde, sonrió y dio un salto.","pregunta":"¿Cómo se sentía Lucas al ver la raíz verde?","opciones":["Alegre y emocionado","Triste y preocupado","Enojado","Aburrido"],"respuestaCorrecta":"Alegre y emocionado","recurso":"experimento_semilla.png"},
        {"id":3,"texto":"Camilo salió corriendo de casa porque iba tarde para la escuela. El cielo estaba lleno de nubes grises muy oscuras. A mitad de camino, sintió unas gotas frías en la cara y tuvo que regresar.","pregunta":"¿Por qué tuvo que regresar Camilo a su casa?","opciones":["Porque olvidó su tarea","Porque olvidó su paraguas","Porque olvidó su chaqueta","Porque era muy temprano"],"respuestaCorrecta":"Porque olvidó su paraguas","recurso":"nubes_lluvia.png"},
        {"id":4,"texto":"Sofía abrió un libro muy antiguo en la biblioteca. De repente, las páginas brillaron con una luz dorada y pequeños dragones salieron volando alrededor de su cabeza, haciéndola reír a carcajadas.","pregunta":"¿De dónde salieron los pequeños dragones?","opciones":["De una caja dorada","De la ventana","Del libro antiguo","Del techo"],"respuestaCorrecta":"Del libro antiguo","recurso":"libro_magico.png"},
        {"id":5,"texto":"El gato Félix escuchó unos maullidos muy suaves que venían de un árbol alto. Subió rápidamente saltando por las ramas gruesas y ayudó a bajar a un gatito que temblaba de miedo.","pregunta":"¿Por qué temblaba el gatito en el árbol?","opciones":["Porque tenía frío","Porque estaba asustado","Porque tenía hambre","Porque estaba enfermo"],"respuestaCorrecta":"Porque estaba asustado","recurso":"gato_arbol.png"},
        {"id":6,"texto":"Papá dejó la torta en el horno y se puso a leer el periódico en la sala. Poco después, un olor a humo llenó la casa. Papá corrió a la cocina tapándose la nariz.","pregunta":"¿Por qué se llenó la casa con olor a humo?","opciones":["Porque se quemó la torta","Porque papá encendió la chimenea","Porque había un incendio afuera","Porque se quemó el periódico"],"respuestaCorrecta":"Porque se quemó la torta","recurso":"torta_horno.png"},
        {"id":7,"texto":"Martín saltó sobre un gran charco de lodo con sus botas nuevas. El agua salpicó tan alto que manchó la ropa limpia de su hermana mayor. Ella lo miró con los brazos cruzados y el ceño fruncido.","pregunta":"¿Cómo se sentía la hermana de Martín?","opciones":["Muy contenta","Asustada","Molesta","Aburrida"],"respuestaCorrecta":"Molesta","recurso":"charco_lodo.png"},
        {"id":8,"texto":"El viento soplaba muy fuerte y hacía mucho frío. La abuela le tejió a Daniel una larga bufanda de lana roja. Daniel se la puso de inmediato y sintió un abrazo calientito en el cuello.","pregunta":"¿Para qué le tejió la abuela una bufanda a Daniel?","opciones":["Para que no tuviera frío","Para que se viera elegante","Para ir a una fiesta","Para jugar en el parque"],"respuestaCorrecta":"Para que no tuviera frío","recurso":"bufanda_lana.png"}
      ]
    }
    $json$::jsonb,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 9);
