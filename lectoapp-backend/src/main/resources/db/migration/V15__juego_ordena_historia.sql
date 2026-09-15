/* --------------------------------------------------------------------------
 * 1. ACTIVIDAD DE LA ETAPA 8: ORDENAR SECUENCIA (VARIANTE B)
 * -------------------------------------------------------------------------- */

UPDATE actividad
SET nombre = 'Ordena la historia',
    descripcion = 'Escucha el cuento y arrastra las imágenes para ordenarlas.',
    tipo_actividad = 'ORDENAR_SECUENCIA',
    configuracion = $json$
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
          "texto": "Juan plantó una semilla en la tierra. Luego le echó agua con su regadera. Al final, creció una hermosa flor roja.",
          "pregunta": "Ordena la historia",
          "opciones": ["sembrando.png", "regando.png", "flor_crecida.png"],
          "respuestaCorrecta": "sembrando.png,regando.png,flor_crecida.png"
        },
        {
          "id": 3,
          "texto": "Mamá mezcló la harina y el azúcar en un tazón. Luego amasó la masa con el rodillo. Finalmente metió las galletas al horno.",
          "pregunta": "Ordena la historia",
          "opciones": ["mezclando.png", "amasando.png", "horneando.png"],
          "respuestaCorrecta": "mezclando.png,amasando.png,horneando.png"
        },
        {
          "id": 4,
          "texto": "El cielo se llenó de nubes oscuras. De pronto, empezó a llover muy fuerte. Sofía abrió su paraguas amarillo para no mojarse.",
          "pregunta": "Ordena la historia",
          "opciones": ["nubes.png", "lluvia.png", "paraguas.png"],
          "respuestaCorrecta": "nubes.png,lluvia.png,paraguas.png"
        }
      ]
    }
    $json$::jsonb,
    updated_at = CURRENT_TIMESTAMP
WHERE etapa_id = (SELECT id FROM etapa WHERE orden = 8);
