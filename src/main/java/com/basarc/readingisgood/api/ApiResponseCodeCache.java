package com.basarc.readingisgood.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class ApiResponseCodeCache {

    private static final List<ApiResponseCode> CODES = new ArrayList<>();

    static {
        cacheResponseCodes();
    }

    //To prevent object creation
    private ApiResponseCodeCache() {
    }

    public static void add(@NonNull ApiResponseCode apiResponseCode) {
        getCodes().add(apiResponseCode);
    }

    @NonNull
    public static List<ApiResponseCode> getCodes() {
        return CODES;
    }

    @Nullable
    public static ApiResponseCode findByMessage(@NonNull String message) {
        for (ApiResponseCode apiResponseCode : CODES) {
            if (apiResponseCode.getMessage() != null &&
                    apiResponseCode.getMessage().equals(message)) {
                return apiResponseCode;
            }
        }
        return null;
    }

    private static void cacheResponseCodes() {
        Field[] fields = ApiResponseCode.class.getFields();
        for (Field field : fields) {
            if (field.getType().equals(ApiResponseCode.class)) {
                try {
                    add((ApiResponseCode) field.get(null));
                } catch (IllegalAccessException e) {
                    log.error("Illegal access exception occurred while accessing the static fields ", e);
                }
            }
        }
    }

}
