package com.example.spring17.controller;

import com.example.spring17.exceptions.BadRequestException;
import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.security.service.SecurityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.spring17.utils.ConstantURL.MAIN_HOST;
import static com.example.spring17.utils.ConstantURL.REFRESH_TOKEN_URL;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping
@AllArgsConstructor
@Slf4j
public class SecurityController {

    private final SecurityService securityService;

    @GetMapping(REFRESH_TOKEN_URL)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            securityService.refreshSession(request, response, authorizationHeader);
        } else{
            throw new NotFoundException("Token", "refresh token value", authorizationHeader);
        }
    }
}

