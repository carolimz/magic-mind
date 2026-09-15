package lectoapp_backend.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lectoapp_backend.application.dto.response.ErrorFrecuenteResponse;
import lectoapp_backend.application.dto.response.ProgresoEtapaResponse;
import lectoapp_backend.application.dto.response.ResumenEstadisticoEstudianteResponse;
import lectoapp_backend.domain.model.ErrorFrecuente;
import lectoapp_backend.domain.model.ProgresoEtapa;
import lectoapp_backend.domain.model.ResumenEstadistica;
import lectoapp_backend.infraestructure.persistence.projection.ErrorFrecuenteProjection;
import lectoapp_backend.infraestructure.persistence.projection.ProgresoEtapaProjection;
import lectoapp_backend.infraestructure.persistence.projection.ResumenEstadisticaProjection;

/**
 * Mapper encargado de transformar las proyecciones de estadísticas
 * provenientes de la infraestructura en modelos de dominio, y estos
 * en DTOs de respuesta.
 */
@Mapper(componentModel = "spring")
public interface EstadisticaMapper {

    /**
     * Convierte la proyección del resumen estadístico en un modelo de dominio.
     *
     * @param projection proyección obtenida desde la base de datos.
     * @return resumen estadístico del estudiante.
     */
    ResumenEstadistica toDomain(ResumenEstadisticaProjection projection);

    /**
     * Convierte una lista de proyecciones de resumen estadístico en modelos de dominio.
     */
    List<ResumenEstadistica> toDomainResumenList(List<ResumenEstadisticaProjection> projections);
    
    /**
     * Convierte el modelo de dominio en un DTO de respuesta.
     * 
     * @param dominio modelo de dominio.
     * @return DTO de respuesta.
     */
    ResumenEstadisticoEstudianteResponse toResponse(ResumenEstadistica dominio);

    /**
     * Convierte una lista de modelos de dominio ResumenEstadistica en DTOs de respuesta.
     */
    List<ResumenEstadisticoEstudianteResponse> toResponseResumenList(List<ResumenEstadistica> dominioList);

    // ── Progreso por etapa ─────────────────────────────────────────────────────

    /** Convierte la proyección de progreso por etapa en un modelo de dominio. */
    ProgresoEtapa toDomain(ProgresoEtapaProjection projection);

    /** Convierte una lista de proyecciones de progreso en modelos de dominio. */
    List<ProgresoEtapa> toDomainProgresoList(List<ProgresoEtapaProjection> projections);

    /** Convierte el modelo de dominio ProgresoEtapa en su DTO de respuesta. */
    ProgresoEtapaResponse toResponse(ProgresoEtapa dominio);

    /** Convierte una lista de ProgresoEtapa en DTOs de respuesta. */
    List<ProgresoEtapaResponse> toResponseProgresoList(List<ProgresoEtapa> dominioList);

    // ── Errores frecuentes ─────────────────────────────────────────────────────

    /** Convierte la proyección de error frecuente en un modelo de dominio. */
    ErrorFrecuente toDomain(ErrorFrecuenteProjection projection);

    /** Convierte una lista de proyecciones de errores en modelos de dominio. */
    List<ErrorFrecuente> toDomainErrorList(List<ErrorFrecuenteProjection> projections);

    /** Convierte el modelo de dominio ErrorFrecuente en su DTO de respuesta. */
    ErrorFrecuenteResponse toResponse(ErrorFrecuente dominio);

    /** Convierte una lista de ErrorFrecuente en DTOs de respuesta. */
    List<ErrorFrecuenteResponse> toResponseErrorList(List<ErrorFrecuente> dominioList);
}