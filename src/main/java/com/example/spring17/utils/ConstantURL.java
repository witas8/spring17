package com.example.spring17.utils;

public class ConstantURL {

    private ConstantURL(){}

    public static final String MAIN_HOST = "/spring17";
    public static final String LOGIN_URL = "/spring17/login";
    public static final String REFRESH_TOKEN_URL = "/spring17/token/refresh";
    public static final String USER_URL = "/spring17/user";
    public static final String CURIOSITY_URL = "/spring17/curiosity";
    public static final String AFTER_SUCCESSFUL_LOGIN_URL = "http://localhost:8080/spring17/curiosity/all";
    public static final String[] AUTH_SWAGGER_WHITELIST = {
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/webjars/**",
            "/v2/**"
    };
}
