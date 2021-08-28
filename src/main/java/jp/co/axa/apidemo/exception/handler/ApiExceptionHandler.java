package jp.co.axa.apidemo.exception.handler;

import jp.co.axa.apidemo.exception.ApiBusinessException;
import jp.co.axa.apidemo.exception.ApiSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * ApiExceptionHandler
 * Our custom application adviser.
 * Catch the Exceptions and
 * Runtime Exceptions that occurred in the controller class.
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = {ApiBusinessException.class})
    public ResponseEntity<Object> handleApiBusinessException(ApiBusinessException e, WebRequest webRequest) {
        logger.debug("Exception handler for ApiBusinessException");
        return createResponseEntityForRuntimeError(e, webRequest);
    }

    @ExceptionHandler(value = {ApiSystemException.class})
    public ResponseEntity<Object> handleApiSystemException(ApiSystemException e, WebRequest webRequest) {
        logger.debug("Exception handler for ApiSystemException");
        return createResponseEntityForExceptionError(e, webRequest);
    }

    private ResponseEntity<Object> createResponseEntityForRuntimeError(ApiBusinessException e, WebRequest webRequest) {
        ApiBusinessException response = new ApiBusinessException(e.getErrorMessage(),
                e.getErrorStatus(), e.getTimeStamp(), e.getDetailedErrorMessage(),getUri(webRequest));
        return new ResponseEntity<> (response,e.getErrorStatus());
    }

    private ResponseEntity<Object> createResponseEntityForExceptionError(ApiSystemException e, WebRequest webRequest) {
        ApiSystemException response = new ApiSystemException(e.getErrorMessage(),
                e.getErrorStatus(), e.getTimeStamp(), e.getDetailedErrorMessage(),getUri(webRequest));
        return new ResponseEntity<> (response,e.getErrorStatus());
    }

    private String getUri(WebRequest webRequest) {
        return ((ServletWebRequest) webRequest).getRequest().getRequestURI();
    }
}
