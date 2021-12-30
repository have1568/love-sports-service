package com.love.sports.user.config.constant;

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


    public static final String[] WHITE_LIST = {LOGIN_PAGE, REGISTER_PROCESSING_URL, LOGIN_PROCESSING_URL, ACTUATOR, INSTANCES, OAUTH};

}