package com.vss.projectstudent2.common;

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

        public static final String HOME = "homepage";


        public static final String STUDENTPAGE = "student";

        public static final String CLASSROOM_PAGE = "classroom";

        public static final String COURSE_PAGE = "course";

        public static final String SCHEDULE_PAGE = "schedule";

        public static final String SUBJECT_PAGE = "subject";

        public static final String INSERT_COURSE = "insertCourse";

        public static final String UPDATE_COURSE = "updateCourse";

        public static final String SCHEDULE_K62 = "scheduleK62";

        public static final String SCHEDULE_K63 = "scheduleK63";

        public static final String SCHEDULE_K64 = "scheduleK64";

        public static final String INSERT_SUBJECT = "insertSubject";
    }

    public static class URLName{
        public static final String HOME = "redirect:/";

        public static final String CLASS = "redirect:/classroom/";

        public static final String COURSES = "redirect:/course/";

        public static final String STUDENTPAGE = "redirect:/student/";

        public static final String SCHEDULE = "redirect:/schedule/";

        public static final String SUBJECT = "redirect:/subject/";
    }


}
