package com.love.sports.auth.config.constant;

public class Whitelist {

    public static final String LOGIN_PAGE = "/login.html";

    public static final String REGISTER_PROCESSING_URL = "/auth/register";

    public static final String LOGIN_PROCESSING_URL = "/login";

    public static final String ACTUATOR = "/actuator/**";

    public static final String INSTANCES = "/instances/**";
    /**
     * /oauth/token,
     * /oauth/check_token,
     * /oauth/confirm_access,
     * /oauth/authorize
     */
    public static final String OAUTH = "/oauth/**";

    public static final String CSS = "/css/**";
    public static final String JS = "/js/**";
    public static final String IMAGES = "/images/**";
    public static final String IMG = "/img/**";
    public static final String WEBJARS = "/webjars/**";
    public static final String FAVICON = "/**/favicon.ico";


    public static final String[] WHITE_LIST = {LOGIN_PAGE, REGISTER_PROCESSING_URL, LOGIN_PROCESSING_URL, ACTUATOR, INSTANCES, OAUTH};

    public static final String[] IGNORE_LIST = {LOGIN_PAGE, CSS, JS, IMAGES, IMG, WEBJARS, FAVICON};

}