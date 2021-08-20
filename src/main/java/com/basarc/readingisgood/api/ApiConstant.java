package com.basarc.readingisgood.api;

public final class ApiConstant {


    public final static class Path {

        public static final String HOME = "/api";
        public static final String VERSION_V1 = "/v1";
        public static final String AUTHENTICATE = HOME + VERSION_V1 + "/authenticate";

        public static final String CUSTOMER = HOME + VERSION_V1 + "/customers";
        public static final String BOOK = HOME + VERSION_V1 + "/books";
        public static final String ORDER = HOME + VERSION_V1 + "/orders";
        public static final String STATS = HOME + VERSION_V1 + "/stats";

    }



}
