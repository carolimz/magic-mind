package lectoapp_backend.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * Respuesta generada después de registrar
 * un resultado de actividad.
 */
@Getter
@Builder
public class ResultadoActividadResponse {

    private Long id;

    /**
     * Identificador de la actividad realizada.
     */
    private Long actividadId;

    private Integer puntaje;

    private Integer cantidadCorrectas;

    private Integer cantidadIncorrectas;

    private BigDecimal porcentaje;

    private Integer duracionSegundos;

    private String estado;

    private LocalDateTime fechaRealizacion;

    /**
     * Errores pedagógicos detectados durante la actividad.
     */
    private List<ErrorDetectadoResponse> errores;

    /**
     * Retroalimentación mostrada al estudiante.
     */
    private String mensaje;

}
// package lectoapp_backend.application.dto.response;

// import java.math.BigDecimal;
// import java.time.LocalDateTime;
// import java.util.List;

// import lombok.Builder;
// import lombok.Getter;

// /**
//  * Respuesta generada después de registrar
//  * un resultado de actividad.
//  */
// @Getter
// @Builder
// public class ResultadoActividadResponse {

//     private Long id;

//     private Integer puntaje;

//     private Integer cantidadCorrectas;

//     private Integer cantidadIncorrectas;

//     private BigDecimal porcentaje;

//     private Integer duracionSegundos;

//     private String estado;

//     private LocalDateTime fechaRealizacion;

//     /**
//      * Errores pedagógicos detectados durante la actividad.
//      */
//     private List<ErrorDetectadoResponse> errores;

//     /**
//      * Retroalimentación mostrada al estudiante.
//      */
//     private String mensaje;

// }