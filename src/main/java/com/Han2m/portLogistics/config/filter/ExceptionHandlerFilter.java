package com.Han2m.portLogistics.config.filter;

import com.Han2m.portLogistics.exception.CustomException.JwtException;
import com.Han2m.portLogistics.exception.ErrorApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.AccessDeniedException;

import static com.Han2m.portLogistics.exception.ApiResponse.errorResponse;
import static com.Han2m.portLogistics.exception.ErrorType.ACCESS_DENIED;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request,response);
        }catch (JwtException e){
            jwtExceptionHandler(response,e.getErrorApiResponse());
        }catch (AccessDeniedException e){
            accessDeniedExceptionHandler(response);
        }
    }

    public void accessDeniedExceptionHandler(HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String jsonResponse = objectMapper.writeValueAsString(errorResponse(ACCESS_DENIED));

        PrintWriter writer = response.getWriter();
        writer.write(jsonResponse);
    }

    public void jwtExceptionHandler(HttpServletResponse response, ErrorApiResponse errorApiResponse) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String jsonResponse = objectMapper.writeValueAsString(errorResponse(errorApiResponse));

        PrintWriter writer = response.getWriter();
        writer.write(jsonResponse);
    }
}
