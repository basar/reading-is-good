package com.basarc.readingisgood.api;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponse<T> implements Serializable {

    private final ApiResponseHeader header;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T detail;


}
