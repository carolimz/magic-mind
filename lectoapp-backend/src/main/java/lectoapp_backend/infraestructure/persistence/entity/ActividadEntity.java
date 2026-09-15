package lectoapp_backend.infraestructure.persistence.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
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
import lectoapp_backend.shared.enums.DificultadActividad;
import lectoapp_backend.shared.enums.TipoActividad;

/**
 * Entidad JPA que representa una actividad pedagógica.
 *
 * <p>
 * Cada actividad pertenece a una etapa y contiene la configuración necesaria
 * para que el frontend pueda construir dinámicamente el ejercicio que realizará
 * el estudiante.
 * </p>
 */
@Entity
@Table(name = "actividad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActividadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_actividad", nullable = false, length = 30)
    private TipoActividad tipoActividad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DificultadActividad dificultad;

    /**
     * Configuración de la actividad almacenada como JSONB en PostgreSQL.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private String configuracion;

    @Builder.Default
    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "etapa_id", nullable = false)
    private EtapaEntity etapa;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}