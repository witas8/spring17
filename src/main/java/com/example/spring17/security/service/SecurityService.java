package com.example.spring17.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.spring17.model.curiosity.user.dto.UserDTO;
import com.example.spring17.service.user.UserServiceSelector;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * When the time of an access token expires then take a refresh token.
 * Take refresh token, verify, confirm that is valid, and send another access token.
 * Remember that we need an access token, because only with this one we can get resources
 * and with a refresh token we can automatically regain access one.
 * JWT = JSON Web Token = header (with type jwt) + payload (info about user) + signature (generated via algorithm).
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {

    private final UserServiceSelector userServiceSelector;

    public void refreshSession(HttpServletRequest request, HttpServletResponse response, String authorizationHeader) throws IOException {
        try {
            String refreshToken = authorizationHeader.substring("Bearer ".length());
            com.auth0.jwt.algorithms.Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            String username = decodedJWT.getSubject();
            log.info("The User's username in refresh token service is " + username);
            //find the user
            UserDTO user = userServiceSelector.getUser(username);
            log.info("The User's role in refresh token service is " + user.role());
            String accessToken = JWT.create()
                    .withSubject(user.username())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) //10 minutes
                    .withIssuer(request.getRequestURL().toString())
                    //in the user details library is authorities is defined as Collection that extends GrantedAuthority
                    .withClaim("roles", Stream.of(user.role()).collect(Collectors.toList()))
                    .sign(algorithm);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        } catch (Exception exception){
            log.error("Error logging in: {}", exception.getMessage());
            response.setHeader("error", exception.getMessage());
            response.setStatus(FORBIDDEN.value());
            //or more detailed response in a body:
            Map<String, String> error = new HashMap<>();
            error.put("error_message", exception.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }

}
