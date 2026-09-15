/*
 * Amplía las opciones de respuesta en las actividades de vocales y consonantes.
 *
 * Etapa 2 (Vocales):   pasa de 4 → 6 opciones por ítem.
 * Etapa 3 (Consonantes): pasa de 4 → 6 opciones por ítem.
 *
 * Las letras distractor se eligen por semejanza visual o fonética con la
 * respuesta correcta para aumentar el desafío de forma pedagógicamente
 * adecuada para niños en proceso de aprendizaje lecto-escritor.
 */

/* --------------------------------------------------------------------------
 * ETAPA 2 – RECONOCIMIENTO DE VOCALES (6 opciones por ítem)
 * -------------------------------------------------------------------------- */

UPDATE actividad
SET configuracion = $json$
{
  "items": [
    {
      "id": 1,
      "pregunta": "Busca la vocal A",
      "opciones": ["A", "M", "P", "S", "H", "T"],
      "respuestaCorrecta": "A",
      "recurso": ""
    },
    {
      "id": 2,
      "pregunta": "Busca la vocal E",
      "opciones": ["T", "L", "E", "R", "F", "N"],
      "respuestaCorrecta": "E",
      "recurso": ""
    },
    {
      "id": 3,
      "pregunta": "Busca la vocal I",
      "opciones": ["B", "I", "F", "N", "L", "T"],
      "respuestaCorrecta": "I",
      "recurso": ""
    },
    {
      "id": 4,
      "pregunta": "Busca la vocal O",
      "opciones": ["O", "P", "M", "T", "C", "S"],
      "respuestaCorrecta": "O",
      "recurso": ""
    },
    {
      "id": 5,
      "pregunta": "Busca la vocal U",
      "opciones": ["L", "U", "R", "S", "N", "V"],
      "respuestaCorrecta": "U",
      "recurso": ""
    },
    {
      "id": 6,
      "pregunta": "Busca otra vez la vocal A",
      "opciones": ["E", "A", "O", "U", "I", "Y"],
      "respuestaCorrecta": "A",
      "recurso": ""
    },
    {
      "id": 7,
      "pregunta": "Busca otra vez la vocal E",
      "opciones": ["I", "O", "A", "E", "U", "Y"],
      "respuestaCorrecta": "E",
      "recurso": ""
    },
    {
      "id": 8,
      "pregunta": "Busca otra vez la vocal O",
      "opciones": ["U", "A", "O", "I", "E", "Y"],
      "respuestaCorrecta": "O",
      "recurso": ""
    }
  ]
}
$json$::jsonb,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 2);

/* --------------------------------------------------------------------------
 * ETAPA 3 – RECONOCIMIENTO DE CONSONANTES (6 opciones por ítem)
 * -------------------------------------------------------------------------- */

UPDATE actividad
SET configuracion = $json$
{
  "items": [
    {
      "id": 1,
      "pregunta": "Busca la consonante M",
      "opciones": ["M", "N", "A", "E", "H", "W"],
      "respuestaCorrecta": "M",
      "recurso": ""
    },
    {
      "id": 2,
      "pregunta": "Busca la consonante N",
      "opciones": ["M", "N", "I", "O", "H", "U"],
      "respuestaCorrecta": "N",
      "recurso": ""
    },
    {
      "id": 3,
      "pregunta": "Busca la consonante P",
      "opciones": ["P", "Q", "A", "U", "B", "F"],
      "respuestaCorrecta": "P",
      "recurso": ""
    },
    {
      "id": 4,
      "pregunta": "Busca la consonante Q",
      "opciones": ["P", "Q", "E", "O", "G", "C"],
      "respuestaCorrecta": "Q",
      "recurso": ""
    },
    {
      "id": 5,
      "pregunta": "Busca la consonante B",
      "opciones": ["B", "D", "A", "I", "P", "R"],
      "respuestaCorrecta": "B",
      "recurso": ""
    },
    {
      "id": 6,
      "pregunta": "Busca la consonante D",
      "opciones": ["B", "D", "E", "U", "P", "O"],
      "respuestaCorrecta": "D",
      "recurso": ""
    },
    {
      "id": 7,
      "pregunta": "Busca la consonante S",
      "opciones": ["S", "L", "A", "O", "Z", "C"],
      "respuestaCorrecta": "S",
      "recurso": ""
    },
    {
      "id": 8,
      "pregunta": "Busca la consonante T",
      "opciones": ["T", "L", "E", "I", "F", "J"],
      "respuestaCorrecta": "T",
      "recurso": ""
    }
  ]
}
$json$::jsonb,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 3);
