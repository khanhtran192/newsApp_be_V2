package com.newsApp_be.security.jwt;

import com.newsApp_be.entity.User;
import com.newsApp_be.repository.UserRepository;
import com.newsApp_be.security.request.LoginRequest;
import com.newsApp_be.security.response.JwtResponse;
import com.newsApp_be.security.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequest loginRequest;
        UsernamePasswordAuthenticationToken loginToken;
        try {
            loginRequest = getLoginRequest(request);
        } catch (IOException e) {
            throw new BadCredentialsException("Invalid parameters!");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        boolean isMatches;
        try {
            isMatches = passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword());
        } catch (IllegalArgumentException iae) {
            throw new BadCredentialsException("Người dùng hoặc mật khẩu không chính xác");
        }

        if (isMatches) {
            loginRequest.setEmail(userDetails.getUsername());
            if (ObjectUtils.isEmpty(userDetails.getAuthorities())) {
                throw new InternalAuthenticationServiceException(userDetails.getUsername() + " không có quyền");
            }
            loginRequest.setAuthorities(userDetails.getAuthorities());
            loginToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else {
            response.setHeader("email", null);
            throw new BadCredentialsException("Người dùng hoặc mật khẩu không đúng!");
        }
        return loginToken;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = jwtUtils.generateJwtToken(authResult);
        Object role = jwtUtils.getRoleFromJwtToken(token);
        String username = jwtUtils.getUserNameFromJwtToken(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng : "+ username));
        Object expiration = jwtUtils.getJwtExpirationMs(token);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(new JwtResponse(expiration,
                user.getUsername(), user.getFullName(), token, role)));
    }

    private LoginRequest getLoginRequest(HttpServletRequest request) throws IOException {
        String obj = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(obj);
        String email = jsonObject.getString("email").replace("\"","");
        String password = jsonObject.get("password").toString().replace("\"","");
        return new LoginRequest(email, password);
    }
}
