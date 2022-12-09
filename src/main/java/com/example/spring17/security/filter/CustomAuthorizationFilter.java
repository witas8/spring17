package com.example.spring17.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.example.spring17.utils.ConstantURL.*;
import static com.example.spring17.utils.Constants.*;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Authorization decides if you have a permission to access a resource (access to the specific place in a website)
 * by using Access Control URLs and Access Control List (ACLs).
 * Steps:
    * get JWT Bearer Token (Api Access Token)
    * decode JWT token
    * verify access token
 */
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    String BEARER_TOKEN_BEGINNING = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //if it is a login path then do nothing and let the request go through
        if(request.getServletPath().equals(LOGIN_URL) || request.getServletPath().equals(REFRESH_TOKEN_URL)){
            filterChain.doFilter(request, response);
        } else {
          //initialize a key to the access token
          String authorizationHeader = request.getHeader(AUTHORIZATION);
          if(authorizationHeader != null && authorizationHeader.startsWith(BEARER_TOKEN_BEGINNING)){
              try {
                  DecodedJWT decodedJWT = getDecodedToken(authorizationHeader);
                  UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(decodedJWT);
                  //Spring Security determines what resource a user can get by considering a role
                  SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                  filterChain.doFilter(request, response);
              } catch (Exception exception){
                  log.error("Error logging in: {}", exception.getMessage());
                  response.setHeader("error", exception.getMessage());
                  response.setStatus(FORBIDDEN.value());
                  Map<String, String> error = new HashMap<>();
                  error.put(TOKEN_ERROR_INDEX, exception.getMessage());
                  response.setContentType(APPLICATION_JSON_VALUE);
                  new ObjectMapper().writeValue(response.getOutputStream(), error);
              }
          } else{
              //just go through
              filterChain.doFilter(request, response);
          }
        }
    }

    private DecodedJWT getDecodedToken(String header){
        //get everything what is after 'Bearer and space'
        String token = header.substring(BEARER_TOKEN_BEGINNING.length());
        //use the algorithm from CustomAuthenticationFilter
        Algorithm algorithm = Algorithm.HMAC256(ALGORITHM_SECRET.getBytes());
        //verify token by using auth0
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(DecodedJWT decodedJWT){
        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim(JWT_CLAIM).asArray(String.class);
        //Spring Security is expecting a GrantedAuthority, so make a conversion:
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}
