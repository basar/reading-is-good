package com.basarc.readingisgood.api;

public final class ApiResponseMessage {

    public static final String SYSTEM_ERROR = "common.error.system";
    public static final String BAD_REQUEST = "common.error.bad.request";

    public static final String USER_NOT_FOUND = "common.error.user.not.found";
    public static final String USER_NOT_AUTHENTICATED = "common.error.user.not.authenticated";
    public static final String USER_USERNAME_PARAM_MISSING = "common.error.user.username.param.missing";
    public static final String USER_PASSO_PARAM_MISSING = "common.error.user.passo.param.missing";

    public static final String INVALID_EMAIL = "common.error.invalid.email";
    public static final String INVALID_NAME = "common.error.invalid.name";
    public static final String INVALID_SURNAME = "common.error.invalid.surname";
    public static final String INVALID_ADDRESS = "common.error.invalid.address";


    public static final String CUSTOMER_NAME_PARAM_MISSING = "result.error.missing.customer.name";
    public static final String CUSTOMER_SURNAME_PARAM_MISSING = "result.error.missing.customer.surname";
    public static final String CUSTOMER_ADRESS_PARAM_MISSING = "result.error.missing.customer.address";
    public static final String CUSTOMER_EMAIL_PARAM_MISSING = "result.error.missing.customer.email";
    public static final String CUSTOMER_ALREADY_DEFINED = "result.error.customer.already.defined";


}
