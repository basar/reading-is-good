package com.basarc.readingisgood.exception;

import com.basarc.readingisgood.api.ApiResponseCode;
import lombok.Getter;

public class ReadingException extends RuntimeException {

    @Getter
    private final ApiResponseCode apiResponseCode;

    public ReadingException(ApiResponseCode apiResponseCode) {
        this(apiResponseCode, apiResponseCode.getMessage());
    }

    public ReadingException(ApiResponseCode apiResponseCode, String message) {
        super(message);
        this.apiResponseCode = apiResponseCode;
    }

}
