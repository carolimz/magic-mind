package lectoapp_backend.application.dto.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RutaAprendizajeResponse {

    private BigDecimal porcentajeMinimoAprobacion;

    private Integer etapasCompletadas;

    private Integer totalEtapas;

    private Boolean rutaCompletada;

    private List<EtapaRutaAprendizajeResponse> etapas;
}