package lectoapp_backend.application.usecase.ai;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lectoapp_backend.application.service.CurrentUserService;
import lectoapp_backend.domain.model.ErrorFrecuente;
import lectoapp_backend.domain.model.Estudiante;
import lectoapp_backend.domain.model.ProgresoEtapa;
import lectoapp_backend.domain.model.ReporteIA;
import lectoapp_backend.domain.model.ResumenEstadistica;
import lectoapp_backend.domain.model.Usuario;
import lectoapp_backend.domain.repository.EstadisticaRepository;
import lectoapp_backend.domain.repository.EstudianteRepository;
import lectoapp_backend.domain.service.GeneradorReporteIAService;
import lectoapp_backend.shared.enums.Rol;
import lectoapp_backend.shared.exception.ResourceNotFoundException;

/**
 * Caso de uso para generar un reporte pedagógico con IA de un estudiante.
 *
 * <p>
 * Orquesta la recopilación de datos del estudiante (resumen estadístico,
 * progreso por etapas y errores frecuentes), construye un resumen formateado
 * y delega la generación del reporte al servicio de dominio
 * {@link GeneradorReporteIAService}.
 * </p>
 *
 * <p>
 * Valida que el docente autenticado tenga acceso al estudiante antes
 * de llamar a la IA, igual que los demás casos de uso del módulo de estadísticas.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class GenerarReporteIAEstudianteUseCase {

    private static final int LIMITE_ERRORES_PARA_IA = 5;

    private final EstudianteRepository estudianteRepository;
    private final EstadisticaRepository estadisticaRepository;
    private final CurrentUserService currentUserService;
    private final GeneradorReporteIAService generadorReporteIAService;

    /**
     * Genera un reporte pedagógico con IA para el estudiante indicado.
     *
     * @param estudianteId identificador del estudiante.
     * @return reporte generado por la IA en formato Markdown.
     */
    public ReporteIA ejecutar(Long estudianteId) {

        Usuario usuarioActual = currentUserService.obtenerUsuarioActual();

        Estudiante estudiante = estudianteRepository
                .buscarPorId(estudianteId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado."));

        validarPermiso(usuarioActual, estudiante);

        // Recopilar los tres bloques de datos que alimentarán el prompt de la IA
        ResumenEstadistica resumen = estadisticaRepository.obtenerResumenEstudiante(estudianteId);
        List<ProgresoEtapa> progreso = estadisticaRepository.obtenerProgresoPorEtapas(estudianteId);
        List<ErrorFrecuente> errores = estadisticaRepository
                .obtenerErroresFrecuentesEstudiante(estudianteId, LIMITE_ERRORES_PARA_IA);

        String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido();

        // Si el resumen aún no tiene nombre (estudiante sin actividades), completar
        if (resumen.getNombreCompleto() == null) {
            resumen.setNombreCompleto(nombreCompleto);
        }

        String resumenFormateado = formatearDatosParaPrompt(resumen, progreso, errores);

        ReporteIA reporte = generadorReporteIAService.generarReporte(nombreCompleto, resumenFormateado);
        reporte.setEstudianteId(estudianteId);

        return reporte;
    }

    /**
     * Formatea los datos del estudiante en un bloque de texto estructurado
     * para incluirlo en el prompt de la IA.
     *
     * @param resumen   resumen estadístico del estudiante.
     * @param progreso  lista de progreso por etapas.
     * @param errores   lista de tipos de error frecuentes.
     * @return texto formateado listo para ser embebido en el prompt.
     */
    private String formatearDatosParaPrompt(
            ResumenEstadistica resumen,
            List<ProgresoEtapa> progreso,
            List<ErrorFrecuente> errores) {

        StringBuilder sb = new StringBuilder();

        sb.append("### Estadísticas Generales\n");
        sb.append("- Actividades realizadas: ").append(
                resumen.getActividadesRealizadas() != null ? resumen.getActividadesRealizadas() : 0).append("\n");
        sb.append("- Promedio general: ").append(
                resumen.getPromedioGeneral() != null ? resumen.getPromedioGeneral() + " puntos" : "Sin datos").append("\n");
        sb.append("- Porcentaje de éxito: ").append(
                resumen.getPorcentajeExito() != null ? resumen.getPorcentajeExito() + "%" : "Sin datos").append("\n");
        sb.append("- Etapa actual: ").append(
                resumen.getNombreEtapa() != null ? resumen.getNombreEtapa() : "Sin actividad registrada").append("\n");

        if (!progreso.isEmpty()) {
            sb.append("\n### Progreso por Etapas\n");
            progreso.forEach(etapa ->
                    sb.append("- ").append(etapa.getNombreEtapa())
                            .append(": ").append(etapa.getActividadesCompletadas())
                            .append("/").append(etapa.getTotalActividades())
                            .append(" actividades (").append(etapa.getPorcentajeCompletado()).append("%)\n")
            );
        }

        if (!errores.isEmpty()) {
            sb.append("\n### Tipos de Error Más Frecuentes\n");
            String listaErrores = errores.stream()
                    .map(e -> "- " + e.getTipoError() + ": " + e.getFrecuencia() + " ocurrencias")
                    .collect(Collectors.joining("\n"));
            sb.append(listaErrores).append("\n");
        } else {
            sb.append("\n### Errores\n- No se han registrado errores frecuentes.\n");
        }

        return sb.toString();
    }

    /**
     * Valida que el usuario autenticado sea ADMIN o el docente responsable del estudiante.
     *
     * @param usuarioActual usuario autenticado.
     * @param estudiante    estudiante a consultar.
     */
    private void validarPermiso(Usuario usuarioActual, Estudiante estudiante) {

        boolean esAdministrador = Rol.ADMIN.equals(usuarioActual.getRol());

        boolean esDocenteResponsable = Objects.equals(
                estudiante.getDocenteId(),
                usuarioActual.getId());

        if (!esAdministrador && !esDocenteResponsable) {
            throw new AccessDeniedException("El estudiante no pertenece al docente autenticado.");
        }
    }
}
