package com.basarc.readingisgood.exception;

import com.basarc.readingisgood.api.ApiResponse;
import com.basarc.readingisgood.api.ApiResponseCode;
import com.basarc.readingisgood.api.ApiResponseCodeCache;
import com.basarc.readingisgood.api.ApiResponseHeader;
import com.basarc.readingisgood.util.LocalizationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final LocalizationResolver localizationResolver;

    @Autowired
    public ApiExceptionHandler(LocalizationResolver localizationResolver) {
        log.debug("Api exception handler created.");
        this.localizationResolver = localizationResolver;
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.error("Bad request error has been occurred", ex);

        final FieldError fieldError = ex.getBindingResult().getFieldError();
        final String message = fieldError != null ? fieldError.getDefaultMessage() : null;

        if (message != null) {
            ApiResponseCode apiResponseCode = ApiResponseCodeCache.findByMessage(message);
            if (apiResponseCode != null) {
                return createExceptionResponse(apiResponseCode);
            }
        }

        return createExceptionResponse(ApiResponseCode.BAD_REQUEST);
    }


    @ExceptionHandler({ReadingException.class})
    public ResponseEntity<Object> handleReadingException(HttpServletRequest request, ReadingException readingException) {
        return createExceptionResponse(readingException.getApiResponseCode());
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(HttpServletRequest request,AuthenticationException exception){
        return createExceptionResponse(ApiResponseCode.USER_NOT_AUTHENTICATED);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(HttpServletRequest request, Exception exception) {
        log.error("Unhandled exception occurred", exception);
        return createExceptionResponse(ApiResponseCode.SYSTEM_ERROR);
    }


    private ResponseEntity<Object> createExceptionResponse(ApiResponseCode responseCode) {
        return ResponseEntity.status(responseCode.getHttpStatus()).body(new ApiResponse<>(
                new ApiResponseHeader(responseCode, localizationResolver.resolve(responseCode.getMessage()))));
    }


}
