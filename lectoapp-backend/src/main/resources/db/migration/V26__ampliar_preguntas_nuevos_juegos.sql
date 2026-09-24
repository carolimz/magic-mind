/*
 * V26: Ampliar preguntas de los 3 nuevos juegos (Simón Dice, Adivina la Sombra, Verdadero/Falso)
 * Incrementando la dificultad gradualmente.
 */

-- 1. Actualizar Verdadero o Falso
UPDATE actividad
SET configuracion = $json$
{
  "items": [
    {
      "id": 1,
      "afirmacion": "¿La palabra OSO empieza con la vocal O?",
      "esVerdadero": true,
      "imagenUrl": "https://cdn-icons-png.flaticon.com/512/375/375084.png"
    },
    {
      "id": 2,
      "afirmacion": "¿La palabra GATO empieza con la vocal A?",
      "esVerdadero": false,
      "imagenUrl": "https://cdn-icons-png.flaticon.com/512/1154/1154448.png"
    },
    {
      "id": 3,
      "afirmacion": "¿La palabra ELEFANTE empieza con la vocal E?",
      "esVerdadero": true,
      "imagenUrl": "https://cdn-icons-png.flaticon.com/512/2923/2923126.png"
    },
    {
      "id": 4,
      "afirmacion": "¿La palabra ABEJA empieza con la vocal E?",
      "esVerdadero": false,
      "imagenUrl": "https://cdn-icons-png.flaticon.com/512/815/815042.png"
    },
    {
      "id": 5,
      "afirmacion": "¿La palabra CABALLO tiene 3 sílabas (ca-ba-llo)?",
      "esVerdadero": true,
      "imagenUrl": "https://cdn-icons-png.flaticon.com/512/1998/1998638.png"
    },
    {
      "id": 6,
      "afirmacion": "¿La palabra MURCIÉLAGO termina con la vocal A?",
      "esVerdadero": false,
      "imagenUrl": "https://cdn-icons-png.flaticon.com/512/10328/10328221.png"
    },
    {
      "id": 7,
      "afirmacion": "¿La palabra ESTRELLA tiene 4 sílabas?",
      "esVerdadero": false,
      "imagenUrl": "https://cdn-icons-png.flaticon.com/512/616/616490.png"
    }
  ]
}
$json$::jsonb,
updated_at = CURRENT_TIMESTAMP
WHERE tipo_actividad = 'VERDADERO_FALSO';

-- 2. Actualizar Adivina la Sombra
UPDATE actividad
SET configuracion = $json$
{
  "items": [
    {
      "id": 1,
      "recurso": "🐘",
      "opciones": ["oso", "elefante", "gato"],
      "respuestaCorrecta": "elefante"
    },
    {
      "id": 2,
      "recurso": "🦆",
      "opciones": ["pato", "sol", "luna"],
      "respuestaCorrecta": "pato"
    },
    {
      "id": 3,
      "recurso": "🍎",
      "opciones": ["pera", "fruta", "manzana"],
      "respuestaCorrecta": "manzana"
    },
    {
      "id": 4,
      "recurso": "🐢",
      "opciones": ["sapo", "tortuga", "iguana"],
      "respuestaCorrecta": "tortuga"
    },
    {
      "id": 5,
      "recurso": "🚲",
      "opciones": ["moto", "patineta", "bicicleta"],
      "respuestaCorrecta": "bicicleta"
    },
    {
      "id": 6,
      "recurso": "🚁",
      "opciones": ["avión", "helicóptero", "globo"],
      "respuestaCorrecta": "helicóptero"
    },
    {
      "id": 7,
      "recurso": "🎸",
      "opciones": ["piano", "violín", "guitarra"],
      "respuestaCorrecta": "guitarra"
    }
  ]
}
$json$::jsonb,
updated_at = CURRENT_TIMESTAMP
WHERE tipo_actividad = 'ADIVINA_LA_SOMBRA';

-- 3. Actualizar Simón Dice
UPDATE actividad
SET configuracion = $json$
{
  "items": [
    {
      "id": 1,
      "pregunta": "Simón dice... ¡Toca el animal que ladra!",
      "opciones": ["perro", "gato", "pato"],
      "respuestaCorrecta": "perro"
    },
    {
      "id": 2,
      "pregunta": "Simón dice... ¡Toca la fruta de color amarillo!",
      "opciones": ["fresa", "banano", "uva"],
      "respuestaCorrecta": "banano"
    },
    {
      "id": 3,
      "pregunta": "Simón dice... ¡Toca el medio de transporte que vuela!",
      "opciones": ["carro", "barco", "avión"],
      "respuestaCorrecta": "avión"
    },
    {
      "id": 4,
      "pregunta": "Simón dice... ¡Toca el objeto que sirve para protegerse de la lluvia!",
      "opciones": ["sombrero", "paraguas", "chaqueta"],
      "respuestaCorrecta": "paraguas"
    },
    {
      "id": 5,
      "pregunta": "Simón dice... ¡Toca el animal que tiene el cuello muy largo!",
      "opciones": ["jirafa", "elefante", "cebra"],
      "respuestaCorrecta": "jirafa"
    },
    {
      "id": 6,
      "pregunta": "Simón dice... ¡Toca la parte del cuerpo que usamos para escuchar!",
      "opciones": ["ojo", "boca", "oreja"],
      "respuestaCorrecta": "oreja"
    },
    {
      "id": 7,
      "pregunta": "Simón dice... ¡Toca el lugar al que los niños van para aprender y estudiar!",
      "opciones": ["parque", "colegio", "hospital"],
      "respuestaCorrecta": "colegio"
    }
  ]
}
$json$::jsonb,
updated_at = CURRENT_TIMESTAMP
WHERE tipo_actividad = 'SIMON_DICE';
