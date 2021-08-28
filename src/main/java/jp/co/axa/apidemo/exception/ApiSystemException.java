package jp.co.axa.apidemo.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * ApiSystemException
 * Our application custom exception class.
 * (for exception errors)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSystemException extends Exception {

    private static final long serialVersionUID = -3597396533209030257L;

    private String errorMessage;
    private HttpStatus errorStatus;
    private String timeStamp;
    private String detailedErrorMessage;
    private String uri;

    public ApiSystemException(String errorMessage, HttpStatus errorStatus) {
        this.errorMessage = errorMessage;
        this.errorStatus = errorStatus;
        this.timeStamp = getLocalTimeStamp();
    }

    public ApiSystemException(String errorMessage, String detailedErrorMessage, String uri) {
        this.errorMessage = errorMessage;
        this.detailedErrorMessage = detailedErrorMessage;
        this.uri = uri;
        this.timeStamp = getLocalTimeStamp();
    }

    public ApiSystemException(String errorMessage, HttpStatus errorStatus, String detailedErrorMessage, String uri) {
        this(errorMessage,errorStatus);
        this.detailedErrorMessage = detailedErrorMessage;
        this.uri = uri;
        this.timeStamp = getLocalTimeStamp();
    }

    public ApiSystemException(String errorMessage, HttpStatus errorStatus, String uri) {
        this(errorMessage,errorStatus);
        this.uri = uri;
        this.timeStamp = getLocalTimeStamp();
    }

    public ApiSystemException(String errorMessage, HttpStatus errorStatus, Throwable exception) {
        this(errorMessage,errorStatus);
        this.detailedErrorMessage = exception.getLocalizedMessage();
        this.timeStamp = getLocalTimeStamp();
    }

    private String getLocalTimeStamp() {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
    }
}
