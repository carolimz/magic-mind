/*
 * Amplía las opciones de respuesta en la actividad de formación de sílabas.
 *
 * Etapa 4 (Sílabas directas): pasa de tener solo las letras correctas (ej. M, A)
 * a incluir letras distractoras (ej. M, A, P, E) para que el niño deba
 * discriminar cuáles son las correctas antes de arrastrarlas.
 */

UPDATE actividad
SET configuracion = $json$
{
  "items": [
    {"id":1,"letras":["M","A","P","E"],"respuestaCorrecta":"MA"},
    {"id":2,"letras":["P","A","M","O"],"respuestaCorrecta":"PA"},
    {"id":3,"letras":["S","O","Z","A"],"respuestaCorrecta":"SO"},
    {"id":4,"letras":["L","U","R","E"],"respuestaCorrecta":"LU"},
    {"id":5,"letras":["T","E","D","I"],"respuestaCorrecta":"TE"},
    {"id":6,"letras":["N","I","M","A"],"respuestaCorrecta":"NI"},
    {"id":7,"letras":["D","O","B","U"],"respuestaCorrecta":"DO"},
    {"id":8,"letras":["B","E","V","A"],"respuestaCorrecta":"BE"}
  ]
}
$json$::jsonb,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 4);
