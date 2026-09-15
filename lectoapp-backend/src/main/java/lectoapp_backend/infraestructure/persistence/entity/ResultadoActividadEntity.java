package lectoapp_backend.infraestructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lectoapp_backend.shared.enums.EstadoResultado;

/**
 * Entidad JPA que representa el resultado obtenido por un estudiante al
 * finalizar una actividad.
 *
 * <p>
 * Cada registro corresponde a un intento realizado por un estudiante.
 * Estos registros conforman el historial de aprendizaje y posteriormente
 * serán utilizados para generar estadísticas, seguimiento docente
 * y recomendaciones mediante inteligencia artificial.
 * </p>
 */
@Entity
@Table(name = "resultado_actividad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoActividadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Estudiante que realizó la actividad.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private EstudianteEntity estudiante;

    /**
     * Actividad que fue resuelta.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "actividad_id", nullable = false)
    private ActividadEntity actividad;

    /**
     * Puntaje obtenido (0 - 100).
     */
    @Column(nullable = false)
    private Integer puntaje;

    /**
     * Cantidad de respuestas correctas.
     */
    @Column(name = "cantidad_correctas", nullable = false)
    private Integer cantidadCorrectas;

    /**
     * Cantidad de respuestas incorrectas.
     */
    @Column(name = "cantidad_incorrectas", nullable = false)
    private Integer cantidadIncorrectas;

    /**
     * Porcentaje de aciertos.
     */
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentaje;

    /**
     * Tiempo empleado por el estudiante para finalizar
     * la actividad, expresado en segundos.
     */
    @Column(name = "duracion_segundos", nullable = false)
    private Integer duracionSegundos;

    /**
     * Estado final del intento.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoResultado estado;

    /**
     * Lista de errores detectados durante la actividad.
     *
     * Se almacena como JSONB para conservar toda la
     * información necesaria para los análisis posteriores.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private String errores;

    /**
     * Fecha en la que el estudiante terminó la actividad.
     */
    @Column(name = "fecha_realizacion", nullable = false)
    private LocalDateTime fechaRealizacion;

    /**
     * Fecha de creación del registro.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}