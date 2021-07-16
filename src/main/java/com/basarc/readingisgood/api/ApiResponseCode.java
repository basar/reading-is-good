package com.basarc.readingisgood.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.lang.reflect.Field;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
public class ApiResponseCode implements Serializable {

    public final static ApiResponseCode SUCCESS = new ApiResponseCode("0");
    public final static ApiResponseCode SYSTEM_ERROR = new ApiResponseCode("RIG-0001", ApiResponseMessage.RC_SYSTEM_ERROR);
    public final static ApiResponseCode BAD_REQUEST = new ApiResponseCode("RIG-0002", ApiResponseMessage.RC_BAD_REQUEST);

    public final static ApiResponseCode CUSTOMER_NAME_MISSING = new ApiResponseCode("RIG-0003", ApiResponseMessage.RC_CUSTOMER_NAME_PARAM_MISSING);

    @JsonValue
    private final String code;

    @Nullable
    private String message;


}
