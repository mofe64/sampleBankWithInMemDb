package com.example.digicoreassessment.services.security;

import com.example.digicoreassessment.payloads.response.APIError;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (JwtException exception) {
            log.info("jwt exception block");
            exception.printStackTrace();
            setErrorResponse(HttpStatus.BAD_REQUEST, response, exception);
        } catch (RuntimeException exception) {
            log.info("runtime block");
            exception.printStackTrace();
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, exception);
        }

    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable exception) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        APIError apiError = new APIError(status, exception);
        try {
            String JsonOutput = apiError.convertToJson();
            response.getWriter().write(JsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
