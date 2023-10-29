package com.libraryplusplus.filter;

import com.libraryplusplus.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        System.out.println(token);
        if (token != null) {
            if (TokenUtils.validateToken(token)) {
                Claims claimsUser = TokenUtils.getClaimsFromToken(token);
                Authentication authentication = createAuthentication(claimsUser.get("id").toString(), claimsUser.get("role").toString());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
//            response.put("id", userInfo.get("id").toString());
//            response.put("role", (String) userInfo.get("role"));
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    public Authentication createAuthentication(String userId, String role) {
        UserDetails user = User.builder()
                .username(userId)
                .password("")
                .roles(role)
                .build();
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
