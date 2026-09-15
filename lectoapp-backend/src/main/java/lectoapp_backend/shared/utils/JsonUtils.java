package lectoapp_backend.shared.utils;


import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utilidad para centralizar la conversión entre objetos Java y JSON.
 *
 * <p>
 * Esta clase evita repetir la creación de instancias de {@link ObjectMapper}
 * en distintos mappers del proyecto y proporciona métodos reutilizables para
 * serializar y deserializar objetos.
 * </p>
 */
public final class JsonUtils {

    /**
     * Instancia única de ObjectMapper reutilizada en toda la aplicación.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    /**
     * Convierte una cadena JSON a un JsonNode.
     *
     * @param json contenido JSON
     * @return representación como JsonNode
     */
    public static JsonNode toJsonNode(String json) {

        try {

            return OBJECT_MAPPER.readTree(json);

        } catch (IOException e) {

            throw new RuntimeException("Error al convertir el JSON.", e);

        }

    }

    /**
     * Convierte un objeto Java a su representación JSON.
     *
     * @param object objeto a convertir
     * @return cadena JSON
     */
    public static String toJson(Object object) {

        try {

            return OBJECT_MAPPER.writeValueAsString(object);

        } catch (IOException e) {

            throw new RuntimeException("Error al serializar el objeto.", e);

        }

    }

    /**
     * Convierte una cadena JSON al tipo indicado.
     *
     * @param <T> tipo esperado
     * @param json contenido JSON
     * @param typeReference tipo destino
     * @return objeto convertido
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {

        try {

            return OBJECT_MAPPER.readValue(json, typeReference);

        } catch (IOException e) {

            throw new RuntimeException("Error al deserializar el JSON.", e);

        }

    }
    /**
     * Convierte una cadena JSON al tipo indicado.
     *
     * @param <T> tipo esperado
     * @param json contenido JSON
     * @param clazz clase destino
     * @return objeto convertido
     */
    public static <T> T fromJson(String json, Class<T> clazz) {

        try {

            return OBJECT_MAPPER.readValue(json, clazz);

        } catch (IOException e) {

            throw new RuntimeException("Error al deserializar el JSON.", e);

        }

    }

}