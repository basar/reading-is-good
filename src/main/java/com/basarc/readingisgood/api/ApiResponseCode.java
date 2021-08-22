package com.basarc.readingisgood.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.io.Serializable;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponseCode implements Serializable {

    public final static ApiResponseCode SUCCESS = new ApiResponseCode("0", HttpStatus.OK);
    public final static ApiResponseCode SYSTEM_ERROR = new ApiResponseCode("RIG-001", ApiResponseMessage.SYSTEM_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    public final static ApiResponseCode BAD_REQUEST = new ApiResponseCode("RIG-002", ApiResponseMessage.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode DATA_INTEGRITY_ERROR = new ApiResponseCode("RIG-003",ApiResponseMessage.DATA_INTEGRITY_ERROR,HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode NOT_FOUND_ERROR = new ApiResponseCode("RIG-004",ApiResponseMessage.NOT_FOUND_ERROR,HttpStatus.NOT_FOUND);

    public final static ApiResponseCode USER_NOT_AUTHENTICATED = new ApiResponseCode("RIG-100", ApiResponseMessage.USER_NOT_AUTHENTICATED, HttpStatus.UNAUTHORIZED);
    public final static ApiResponseCode USER_USERNAME_MISSING = new ApiResponseCode("RIG-101", ApiResponseMessage.USER_USERNAME_PARAM_MISSING, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode USER_PASSO_MISSING = new ApiResponseCode("RIG-102", ApiResponseMessage.USER_PASSO_PARAM_MISSING, HttpStatus.BAD_REQUEST);


    public final static ApiResponseCode INVALID_EMAIL = new ApiResponseCode("RIG-103", ApiResponseMessage.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode INVALID_NAME = new ApiResponseCode("RIG-104", ApiResponseMessage.INVALID_NAME, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode INVALID_SURNAME = new ApiResponseCode("RIG-105", ApiResponseMessage.INVALID_SURNAME, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode INVALID_ADDRESS = new ApiResponseCode("RIG-106", ApiResponseMessage.INVALID_ADDRESS, HttpStatus.BAD_REQUEST);


    public final static ApiResponseCode CUSTOMER_NAME_MISSING = new ApiResponseCode("RIG-107", ApiResponseMessage.CUSTOMER_NAME_PARAM_MISSING, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode CUSTOMER_SURNAME_MISSING = new ApiResponseCode("RIG-108", ApiResponseMessage.CUSTOMER_SURNAME_PARAM_MISSING, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode CUSTOMER_EMAIL_MISSING = new ApiResponseCode("RIG-109", ApiResponseMessage.CUSTOMER_EMAIL_PARAM_MISSING, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode CUSTOMER_ADDRESS_MISSING = new ApiResponseCode("RIG-110", ApiResponseMessage.CUSTOMER_ADDRESS_PARAM_MISSING, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode CUSTOMER_ALREADY_DEFINED = new ApiResponseCode("RIG-111", ApiResponseMessage.CUSTOMER_ALREADY_DEFINED, HttpStatus.CONFLICT);

    public final static ApiResponseCode BOOK_NAME_MISSING = new ApiResponseCode("RIG-112", ApiResponseMessage.BOOK_NAME_PARAM_MISSING, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode BOOK_AUTHOR_MISSING = new ApiResponseCode("RIG-113", ApiResponseMessage.BOOK_AUTHOR_PARAM_MISSING, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode BOOK_PRICE_MISSING = new ApiResponseCode("RIG-114", ApiResponseMessage.BOOK_PRICE_MISSING, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode BOOK_INITIAL_STOCK_MISSING = new ApiResponseCode("RIG-115", ApiResponseMessage.BOOK_INITIAL_STOCK_MISSING, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode BOOK_NAME_INVALID = new ApiResponseCode("RIG-116", ApiResponseMessage.BOOK_NAME_INVALID, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode BOOK_AUTHOR_INVALID = new ApiResponseCode("RIG-117", ApiResponseMessage.BOOK_AUTHOR_INVALID, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode BOOK_ALREADY_DEFINED = new ApiResponseCode("RIG-118", ApiResponseMessage.BOOK_ALREADY_DEFINED, HttpStatus.CONFLICT);
    public final static ApiResponseCode BOOK_PRICE_INVALID = new ApiResponseCode("RIG-119", ApiResponseMessage.BOOK_PRICE_INVALID, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode BOOK_INITIAL_STOCK_INVALID = new ApiResponseCode("RIG-120", ApiResponseMessage.BOOK_INITIAL_STOCK_INVALID, HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode BOOK_NOT_FOUND = new ApiResponseCode("RIG-121", ApiResponseMessage.BOOK_NOT_FOUND, HttpStatus.NOT_FOUND);

    public final static ApiResponseCode BOOK_ID_MISSING = new ApiResponseCode("RIG-122",ApiResponseMessage.BOOK_ID_MISSING,HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode STOCK_AMOUNT_MISSING = new ApiResponseCode("RIG-123",ApiResponseMessage.STOCK_AMOUNT_MISSING,HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode STOCK_AMOUNT_INVALID = new ApiResponseCode("RIG-124",ApiResponseMessage.STOCK_AMOUNT_INVALID,HttpStatus.BAD_REQUEST);
    public final static ApiResponseCode CUSTOMER_NOT_FOUND = new ApiResponseCode("RIG-125",ApiResponseMessage.CUSTOMER_NOT_FOUND,HttpStatus.NOT_FOUND);

    public final static ApiResponseCode ORDER_ID_MISSING = new ApiResponseCode("RIG-126",ApiResponseMessage.ORDER_ID_MISSING,HttpStatus.NOT_FOUND);

    @JsonValue
    private final String code;

    @Nullable
    private String message;

    @JsonIgnore
    private HttpStatus httpStatus;

    public ApiResponseCode(String code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

}
