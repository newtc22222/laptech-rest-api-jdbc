package com.laptech.restapi.jwt.filter;

import com.laptech.restapi.jwt.service.JwtService;
import com.laptech.restapi.jwt.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
@WebFilter(urlPatterns = "/*", dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD })
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        if(!request.getRequestURI().contains("/login")) {
            final String header = request.getHeader("Authorization");
            String userPhone = null;
            String jwtToken = null;
            if (header != null && header.startsWith("Bearer ")) {
//                System.out.println(header);
                jwtToken = header.substring(7);
                try {
                    userPhone = jwtUtil.getUserPhoneFromToken(jwtToken);
                } catch (ExpiredJwtException err_ex) {
                    log.warn("[ERROR] Authorization error: {0}", err_ex);
                    request.setAttribute("expired", err_ex.getMessage());
                } catch (SignatureException err_s) {
                    log.warn("[ERROR] Invalid token: {0}", err_s);
                    request.setAttribute("signature", err_s.getMessage());
                } catch (IllegalArgumentException err_i) {
                    log.warn("[ERROR] {}", err_i.getMessage());
                    throw new IllegalArgumentException("[INFO] Your data is invalid!");
                }
            }

            if (userPhone != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = jwtService.loadUserByUsername(userPhone);

                if (jwtUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
