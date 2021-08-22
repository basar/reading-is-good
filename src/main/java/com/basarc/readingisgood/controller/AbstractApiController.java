package com.basarc.readingisgood.controller;

import com.basarc.readingisgood.api.ApiResponse;
import com.basarc.readingisgood.api.ApiResponseCode;
import com.basarc.readingisgood.api.ApiResponseHeader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractApiController {

    public <T> ResponseEntity<ApiResponse<T>> ok(T body){
        return new ResponseEntity<>
                (new ApiResponse<>(new ApiResponseHeader(ApiResponseCode.SUCCESS), body), HttpStatus.OK);
    }

}
