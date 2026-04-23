package com.springai.resumax.security.util;

import com.springai.resumax.security.entity.User;
import com.springai.resumax.security.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final SecurityUtil securityUtil;
    private final UserRepository userRepository;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            final String requestHeader = request.getHeader("Authorization");

            if(requestHeader==null || !requestHeader.startsWith("Bearer")){

                filterChain.doFilter(request,response);
                return;
            }

            String token = requestHeader.split("Bearer ")[1];

            String username = securityUtil.findUsernameByToken(token);

            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

                User user = userRepository.findByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                user,null,user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request,response);
            }

        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request,response,null,e);
        }


    }
}
