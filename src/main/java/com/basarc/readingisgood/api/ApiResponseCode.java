package com.basarc.readingisgood.api;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.io.Serializable;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponseCode implements Serializable {

    public final static ApiResponseCode SUCCESS = new ApiResponseCode("0", HttpStatus.OK);
    public final static ApiResponseCode SYSTEM_ERROR = new ApiResponseCode("RIG-0001", ApiResponseMessage.SYSTEM_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    public final static ApiResponseCode BAD_REQUEST = new ApiResponseCode("RIG-0002", ApiResponseMessage.BAD_REQUEST, HttpStatus.BAD_REQUEST);

    public final static ApiResponseCode USER_NOT_AUTHENTICATED = new ApiResponseCode("RIG-0011", ApiResponseMessage.USER_NOT_AUTHENTICATED, HttpStatus.UNAUTHORIZED);
    public final static ApiResponseCode USER_USERNAME_MISSING = new ApiResponseCode("RIG-0012", ApiResponseMessage.USER_USERNAME_PARAM_MISSING, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode USER_PASSO_MISSING = new ApiResponseCode("RIG-0013", ApiResponseMessage.USER_PASSO_PARAM_MISSING, HttpStatus.BAD_REQUEST);


    public final static ApiResponseCode INVALID_EMAIL = new ApiResponseCode("RIG-0004", ApiResponseMessage.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode INVALID_NAME = new ApiResponseCode("RIG-0007", ApiResponseMessage.INVALID_NAME, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode INVALID_SURNAME = new ApiResponseCode("RIG-0008", ApiResponseMessage.INVALID_SURNAME, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode INVALID_ADDRESS = new ApiResponseCode("RIG-0009", ApiResponseMessage.INVALID_ADDRESS, HttpStatus.BAD_REQUEST);


    public final static ApiResponseCode CUSTOMER_NAME_MISSING = new ApiResponseCode("RIG-0003", ApiResponseMessage.CUSTOMER_NAME_PARAM_MISSING, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode CUSTOMER_SURNAME_MISSING = new ApiResponseCode("RIG-0006", ApiResponseMessage.CUSTOMER_SURNAME_PARAM_MISSING, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode CUSTOMER_EMAIL_MISSING = new ApiResponseCode("RIG-0005", ApiResponseMessage.CUSTOMER_EMAIL_PARAM_MISSING, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode CUSTOMER_ADDRESS_MISSING = new ApiResponseCode("RIG-0010", ApiResponseMessage.CUSTOMER_ADRESS_PARAM_MISSING, HttpStatus.BAD_REQUEST);


    @JsonValue
    private final String code;

    @Nullable
    private String message;

    private HttpStatus httpStatus;

    public ApiResponseCode(String code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

}
