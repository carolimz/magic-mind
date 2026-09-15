package lectoapp_backend.infraestructure.persistence.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa una etapa del proceso de aprendizaje.
 *
 * <p>
 * Las etapas son registros fijos del sistema y sirven para organizar las
 * actividades según el nivel de aprendizaje definido en LectoApp.
 * </p>
 */
@Entity
@Table(name = "etapa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtapaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(name = "objetivo_pedagogico", nullable = false)
    private String objetivoPedagogico;

    @Column(nullable = false, unique = true)
    private Integer orden;

    @Builder.Default
    @Column(nullable = false)
    private Boolean activo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}