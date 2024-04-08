package dev.adrianalonso.dekra.quickprod.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Builder
public class HttpResponse {
    private String timeStamp;
    private int statusCode;
    private HttpStatus status;
    private String reason;
    private String message;
    private String developerMessage;
    private Map<String, Object> data;
}
