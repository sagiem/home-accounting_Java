package ru.sagiem.whattobuy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class SuccessResponse {

    @Schema(description = "Status", example = "200")
    private Integer status;
    @Schema(description = "Success response message", example = "Message about any success")
    private String message;
    @JsonIgnore
    private Object object;

    public SuccessResponse(HttpStatus status, String message, Object object) {
        this.status = status.value();
        this.message = message;
        this.object = object;
    }
}
