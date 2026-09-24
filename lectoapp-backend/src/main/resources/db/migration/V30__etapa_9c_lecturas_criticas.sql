-- V30: Etapa 9C - Lecturas Críticas y Morales
-- Historias largas con preguntas analíticas y reflexivas.

-- 1. Insertar la Etapa 9C (Orden 18 para ir después de la 17)
INSERT INTO etapa (nombre, descripcion, objetivo_pedagogico, orden, activo, created_at, updated_at)
VALUES (
    'Etapa 9C - Comprensión Lectora Crítica', 
    'Lee historias largas, analiza el comportamiento de los personajes y descubre las moralejas ocultas.', 
    'Desarrollar la comprensión crítica y el análisis moral.',
    18, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- 2. Insertar la Actividad para la Etapa 9C
INSERT INTO actividad (nombre, descripcion, tipo_actividad, dificultad, configuracion, activo, etapa_id, created_at, updated_at)
VALUES (
    'Historias para Pensar y Reflexionar',
    'Lee con atención cada historia y responde analizando lo que hicieron los personajes.',
    'SELECCION',
    'DIFICIL',
    $json$
    {
      "items": [
        {
          "id": 1,
          "texto": "Había una vez dos amigos, Tomás y Mateo, que encontraron una billetera tirada en el parque mientras jugaban al fútbol. La billetera estaba llena de billetes. Mateo sugirió que se repartieran el dinero y se compraran muchos helados y juguetes, ya que nadie los estaba viendo. Sin embargo, Tomás vio que adentro había una identificación con la foto de un señor mayor que vivía cerca de allí. Tomás pensó en lo triste y preocupado que debía estar ese señor por haber perdido su dinero. Aunque a Tomás le encantaban los helados, convenció a su amigo de caminar hasta la casa del señor para devolvérsela. Al llegar, el señor les dio las gracias con lágrimas en los ojos, pues ese dinero era para comprar sus medicinas.",
          "pregunta": "¿Por qué crees que Tomás decidió devolver la billetera en lugar de comprarse helados?",
          "opciones": [
            "Porque no le gustaban los helados",
            "Porque sintió empatía y pensó en la preocupación del dueño",
            "Porque su mamá lo estaba mirando desde lejos"
          ],
          "respuestaCorrecta": "Porque sintió empatía y pensó en la preocupación del dueño"
        },
        {
          "id": 2,
          "texto": "Había una vez dos amigos, Tomás y Mateo, que encontraron una billetera tirada en el parque mientras jugaban al fútbol. La billetera estaba llena de billetes. Mateo sugirió que se repartieran el dinero y se compraran muchos helados y juguetes, ya que nadie los estaba viendo. Sin embargo, Tomás vio que adentro había una identificación con la foto de un señor mayor que vivía cerca de allí. Tomás pensó en lo triste y preocupado que debía estar ese señor por haber perdido su dinero. Aunque a Tomás le encantaban los helados, convenció a su amigo de caminar hasta la casa del señor para devolvérsela. Al llegar, el señor les dio las gracias con lágrimas en los ojos, pues ese dinero era para comprar sus medicinas.",
          "pregunta": "¿Cuál es la moraleja o el mensaje principal de esta historia?",
          "opciones": [
            "Es mejor quedarse con lo que uno encuentra en la calle",
            "Siempre debemos hacer lo correcto, aunque nadie nos esté viendo",
            "Los niños no deben jugar al fútbol en el parque"
          ],
          "respuestaCorrecta": "Siempre debemos hacer lo correcto, aunque nadie nos esté viendo"
        },
        {
          "id": 3,
          "texto": "En un bosque lejano vivía un búho llamado Sabio y una ardilla llamada Rápida. Rápida siempre se burlaba de Sabio porque él volaba muy despacio y le gustaba dormir de día. Rápida, en cambio, corría por todas las ramas presumiendo de su velocidad. Un día, una fuerte tormenta de nieve cubrió todo el bosque y la comida quedó enterrada. Rápida corría de un lado a otro desesperada pero no encontraba sus nueces. Cuando llegó la noche oscura, Sabio, que tenía una excelente visión nocturna, voló silenciosamente y pudo ver desde las alturas los pequeños montículos donde estaban las nueces. En lugar de burlarse de Rápida, Sabio la guió hasta su comida.",
          "pregunta": "¿Qué hubiera pasado si Sabio decidía vengarse de la ardilla por sus burlas?",
          "opciones": [
            "La ardilla habría encontrado su comida más rápido",
            "La ardilla se habría quedado sin comer durante la tormenta",
            "El búho habría empezado a correr por las ramas"
          ],
          "respuestaCorrecta": "La ardilla se habría quedado sin comer durante la tormenta"
        },
        {
          "id": 4,
          "texto": "En un bosque lejano vivía un búho llamado Sabio y una ardilla llamada Rápida. Rápida siempre se burlaba de Sabio porque él volaba muy despacio y le gustaba dormir de día. Rápida, en cambio, corría por todas las ramas presumiendo de su velocidad. Un día, una fuerte tormenta de nieve cubrió todo el bosque y la comida quedó enterrada. Rápida corría de un lado a otro desesperada pero no encontraba sus nueces. Cuando llegó la noche oscura, Sabio, que tenía una excelente visión nocturna, voló silenciosamente y pudo ver desde las alturas los pequeños montículos donde estaban las nueces. En lugar de burlarse de Rápida, Sabio la guió hasta su comida.",
          "pregunta": "¿Qué nos enseña el comportamiento del búho al final de la historia?",
          "opciones": [
            "Que todos tenemos talentos diferentes y debemos ayudarnos en lugar de burlarnos",
            "Que los búhos siempre deben comer nueces",
            "Que las ardillas son mejores que los búhos"
          ],
          "respuestaCorrecta": "Que todos tenemos talentos diferentes y debemos ayudarnos en lugar de burlarnos"
        }
      ]
    }
    $json$::jsonb,
    TRUE,
    (SELECT id FROM etapa WHERE orden = 18 LIMIT 1),
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
