package com.basarc.readingisgood.exception;

import com.basarc.readingisgood.api.ApiResponse;
import com.basarc.readingisgood.api.ApiResponseCode;
import com.basarc.readingisgood.api.ApiResponseCodeCache;
import com.basarc.readingisgood.api.ApiResponseHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final ApplicationContext applicationContext;

    @Autowired
    public ApiExceptionHandler(ApplicationContext applicationContext) {
        log.debug("Api exception handler created.");
        this.applicationContext = applicationContext;
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Bad request error has been occurred", ex);

        final FieldError fieldError = ex.getBindingResult().getFieldError();
        final String message = fieldError != null ? fieldError.getDefaultMessage() : null;

        if (message != null) {
            ApiResponseCode apiResponseCode = ApiResponseCodeCache.findByMessage(message);
            if (apiResponseCode!=null) {
                return createExceptionResponse(apiResponseCode, status);
            }
        }

        return createExceptionResponse(ApiResponseCode.BAD_REQUEST,status);
    }


    private ResponseEntity<Object> createExceptionResponse(ApiResponseCode responseCode, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponse<>(new ApiResponseHeader(responseCode, getLocalizedMessage(responseCode.getMessage()))));
    }


    private String getLocalizedMessage(String key) {
        try {
            return applicationContext.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException ex) {
            log.error("Message not found for the key: {}", key);
            return key;
        }
    }


}
