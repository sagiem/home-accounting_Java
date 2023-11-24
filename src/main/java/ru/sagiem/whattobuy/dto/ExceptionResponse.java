package ru.sagiem.whattobuy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Getter
@Setter
public class ExceptionResponse {

    @Schema(description = "Status", example = "200")
    private Integer status;
    @Schema(description = "Timestamp", example = "2023-12-01")
    private final LocalDate timestamp = LocalDate.now();
    @Schema(description = "Exception response message", example = "Some exception")
    private String message;
    @Schema(description = "Exception type", example = "Some exception type")
    private String type;

    public ExceptionResponse(HttpStatus status, String message, String type) {
        this.status = status.value();
        this.message = message;
        this.type = type;
    }
}
