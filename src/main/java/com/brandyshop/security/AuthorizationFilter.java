package com.brandyshop.security;

import com.brandyshop.domain.srv.security.SocketPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null) {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            try {

                SocketPrincipal socketPrincipal = null;

                if (token.equals("abcd")) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + "ADMIN"));

                    socketPrincipal = SocketPrincipal.builder()
                            .name("ADMIN")
                            .token(token)
                            .activity("true")
                            .build();
                }

                return new UsernamePasswordAuthenticationToken(socketPrincipal, token, authorities);

            } catch (Exception e) {
                //nothing
            }
        }

        return null;
    }

}
