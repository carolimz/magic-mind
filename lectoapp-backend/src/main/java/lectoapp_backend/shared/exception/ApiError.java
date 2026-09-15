package lectoapp_backend.shared.exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiError {

    private LocalDateTime timestamp;

    private Integer status;

    private String error;

    private String message;

    private String path;
}