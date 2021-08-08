package com.basarc.readingisgood.api;

public final class ApiResponseMessage {

    public static final String SYSTEM_ERROR = "common.error.system";
    public static final String BAD_REQUEST = "common.error.bad.request";
    public static final String DATA_INTEGRITY_ERROR = "common.error.data.integrity";
    public static final String NOT_FOUND_ERROR = "common.error.not.found";

    public static final String USER_NOT_FOUND = "common.error.user.not.found";
    public static final String USER_NOT_AUTHENTICATED = "common.error.user.not.authenticated";
    public static final String USER_USERNAME_PARAM_MISSING = "common.error.user.username.param.missing";
    public static final String USER_PASSO_PARAM_MISSING = "common.error.user.passo.param.missing";

    public static final String INVALID_EMAIL = "common.error.invalid.email";
    public static final String INVALID_NAME = "common.error.invalid.name";
    public static final String INVALID_SURNAME = "common.error.invalid.surname";
    public static final String INVALID_ADDRESS = "common.error.invalid.address";
    public static final String INVALID_QUANTITY = "common.error.invalid.quantity";


    public static final String CUSTOMER_NAME_PARAM_MISSING = "result.error.missing.customer.name";
    public static final String CUSTOMER_SURNAME_PARAM_MISSING = "result.error.missing.customer.surname";
    public static final String CUSTOMER_ADDRESS_PARAM_MISSING = "result.error.missing.customer.address";
    public static final String CUSTOMER_EMAIL_PARAM_MISSING = "result.error.missing.customer.email";
    public static final String CUSTOMER_ALREADY_DEFINED = "result.error.customer.already.defined";

    public static final String BOOK_NAME_PARAM_MISSING = "result.error.missing.book.name";
    public static final String BOOK_AUTHOR_PARAM_MISSING = "result.error.missing.book.author";
    public static final String BOOK_PRICE_MISSING = "result.error.missing.book.price";
    public static final String BOOK_INITIAL_STOCK_MISSING = "result.error.missing.book.initial.stock";
    public static final String BOOK_NAME_INVALID = "result.error.invalid.book.name";
    public static final String BOOK_AUTHOR_INVALID = "result.error.invalid.book.author";
    public static final String BOOK_ALREADY_DEFINED = "result.error.book.already.defined";
    public static final String BOOK_PRICE_INVALID = "result.error.invalid.book.price";
    public static final String BOOK_INITIAL_STOCK_INVALID = "result.error.invalid.book.initial.stock";
    public static final String BOOK_NOT_FOUND = "result.error.book.not.found";

    public static final String BOOK_ID_MISSING = "result.error.book.id.missing";
    public static final String STOCK_AMOUNT_MISSING = "result.error.stock.amount.missing";
    public static final String STOCK_AMOUNT_INVALID = "result.error.stock.amount.invalid";

    public static final String CUSTOMER_NOT_FOUND = "result.error.customer.not.found";

    public static final String ORDER_ID_MISSING = "result.error.order.id.missing";

}
