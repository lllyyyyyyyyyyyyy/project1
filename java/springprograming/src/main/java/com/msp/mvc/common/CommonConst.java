package com.msp.mvc.common;

public class CommonConst {

    //error fields
    public static final String FIELD_ERROR_MSG = "errorMsg";

    //REGEX
    public static final String EMAIL_REGEX = "^[A-Za-z0-9]+@[a-z0-9]+\\.[a-z]{2,6}$";

    public static class ErrorMsg {
        public static final String EM_AGE_REQUIRED = "Please provide a age";
        public static final String EM_INVALID_EMAIL_FORMAT = "Email is not valid";
        public static final String EM_NAME_REQUIRED = "Please provide a name";
    }

    public static class ViewName {
        public static final String INSERT = "insert";
        public static final String UPDATE = "update";
    }
}
