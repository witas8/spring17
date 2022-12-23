package com.example.spring17.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.spring17.utils.ConstantURL.LOGIN_URL;
import static com.example.spring17.utils.ConstantURL.USER_URL;

@RestController
@RequestMapping(LOGIN_URL)
public class LoginController {
}
