package com.vss.social_webapp.common;

import org.thymeleaf.util.ListUtils;

import java.util.List;

public class CommonConst {

    public static class ViewName {
        public static final String INDEX = "index";
        public static final String ADMIN_PAGE = "admin_page";
        public static final String LOGIN = "login";
    }
    public static class UrlName{
        public static final String LOGIN = "redirect:/login";
        public static final String INDEX = "redirect:/";
    }

    public static class Regex{
        public static final String EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        public static final String PHONE = "^[0-9]{6,12}$";
        public static final String DATE_OF_BIRTH = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
    }

//    public boolean contains(final List<SimpleGrantedAuthority> target, final Object element) {
//        for (SimpleGrantedAuthority au : target) {
//            if (au.getAuthority().equals(element.toString()))
//                return true;
//        }
//        return false;
//    }
}
