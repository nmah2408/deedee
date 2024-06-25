package com.deedee.identity.exception;

import com.deedee.identity.exception.response.ErrorApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleTokenNotFoundException(TokenNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorApiResponse> handleTokenExpiredException(TokenExpiredException ex) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountExistsException.class)
    public ResponseEntity<ErrorApiResponse> handleAccountExistsException(AccountExistsException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    /*--------------------------------------------*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApiResponse> handleAllExceptions(Exception ex) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleNoResourceFoundException(Exception ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorApiResponse> handleHttpRequestMethodNotSupportedException(Exception ex) {
        return buildErrorResponse(ex, HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ResponseEntity<ErrorApiResponse> buildErrorResponse(Exception ex, HttpStatus status) {

        ErrorApiResponse errorResponse = new ErrorApiResponse();

        ErrorApiResponse.Meta meta = new ErrorApiResponse.Meta();
        meta.setTraceId(UUID.randomUUID().toString());
        meta.setSuccess(false);

        ErrorApiResponse.ErrorDetails errorDetails = new ErrorApiResponse.ErrorDetails();
        errorDetails.setCode(status.value());
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setData(null);

        errorResponse.setMeta(meta);
        errorResponse.setError(errorDetails);
        if (status != HttpStatus.INTERNAL_SERVER_ERROR) {
            errorResponse.setStackTrace(null);
        } else {
            errorResponse.setStackTrace(Stream.of(ex.getStackTrace())
                    .map(StackTraceElement::toString)
                    .collect(Collectors.toList()));
        }

        return new ResponseEntity<>(errorResponse, status);
    }
}
