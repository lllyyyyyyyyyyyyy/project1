package com.vss.lynt.common;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    public boolean contains(final List<SimpleGrantedAuthority> target, final Object element) {
        for (SimpleGrantedAuthority au : target) {
            if (au.getAuthority().equals(element.toString()))
                return true;
        }
        return false;
    }
}
