package com.aktog.yusuf.veteriner.security;

import com.aktog.yusuf.veteriner.entity.Role;
import com.aktog.yusuf.veteriner.entity.User;
import com.aktog.yusuf.veteriner.repository.UserRepository;
import com.aktog.yusuf.veteriner.util.JwtUtil;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtTokenFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.split(" ")[1].trim();

        if (!jwtUtil.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        String username = jwtUtil.getUsername(token);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userRepository.findRolesByUsername(username).
                forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));

        new ArrayList<>(authorities).forEach(auth -> System.out.println(auth.getAuthority()));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                null,
                authorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}