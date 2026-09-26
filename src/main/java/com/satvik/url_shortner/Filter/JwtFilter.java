package com.satvik.url_shortner.Filter;

import com.satvik.url_shortner.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private  final JwtUtil jwtutil;

    public JwtFilter(JwtUtil jwtutil){
        this.jwtutil = jwtutil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
         throws ServletException,IOException {
         String authHeader = request.getHeader("Authorization");

         if(authHeader != null && authHeader.startsWith("Bearer")){
             String token = authHeader.substring(7);

             if(jwtutil.isTokenValid(token)){
               String username = jwtutil.extractusername(token);

                 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                         username,null, List.of());


             }
         }
         filterChain.doFilter(request,response);
        }






}
