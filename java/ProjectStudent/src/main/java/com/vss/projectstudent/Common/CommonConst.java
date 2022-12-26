package com.vss.projectstudent.Common;

public class CommonConst {

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
        public static final String HOME = "homepage";

        public static final String UPDATE = "update";

        public static final String HELLO = "hello";

        public static final String CLASS = "classroom";

        public static final String COURSES = "courses";
    }

    public static class URLName{
        public static final String HOME = "redirect:/";

        public static final String CLASS = "redirect:/get-course";

        public static final String COURSES = "redirect:/get-courses/get-class";
    }

}
