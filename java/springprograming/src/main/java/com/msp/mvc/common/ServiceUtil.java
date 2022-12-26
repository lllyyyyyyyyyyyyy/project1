package com.msp.mvc.common;

import java.util.regex.Pattern;

import static com.msp.mvc.common.CommonConst.EMAIL_REGEX;

public class ServiceUtil {

    public static final Pattern pattern = Pattern.compile(EMAIL_REGEX);
}
