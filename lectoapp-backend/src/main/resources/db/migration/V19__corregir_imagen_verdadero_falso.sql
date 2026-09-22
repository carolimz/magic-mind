/*
 * V19: Corregir el JSON de la actividad Verdadero/Falso.
 * Se cambia la clave "imagenUrl" a "recurso" para que el backend de Java lo procese sin dar Error 500
 * (debido a UnrecognizedPropertyException).
 * Además, se usan emojis en lugar de URLs externas para evitar problemas de carga de imágenes (iconos rotos).
 */
UPDATE actividad
SET configuracion = $json$
{
  "items": [
    {
      "id": 1,
      "afirmacion": "¿La palabra OSO empieza con la vocal O?",
      "esVerdadero": true,
      "recurso": "🐻"
    },
    {
      "id": 2,
      "afirmacion": "¿La palabra GATO empieza con la vocal A?",
      "esVerdadero": false,
      "recurso": "🐱"
    },
    {
      "id": 3,
      "afirmacion": "¿La palabra ELEFANTE empieza con la vocal E?",
      "esVerdadero": true,
      "recurso": "🐘"
    }
  ]
}
$json$::jsonb,
updated_at = CURRENT_TIMESTAMP
WHERE nombre = 'Verdadero o Falso' AND tipo_actividad = 'VERDADERO_FALSO';
