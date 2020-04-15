package guru.springframework.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

/**
 * @author Gaetan Bloch
 * Created on 15/04/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestError {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String trace;
    private String path;

    public RestError(Exception exception, HttpStatus httpStatus, WebRequest webRequest) {
        this.timestamp = LocalDateTime.now();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = exception.getMessage();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        this.trace = sw.toString();
        this.path = ((ServletWebRequest) webRequest).getRequest().getRequestURI();
    }
}
