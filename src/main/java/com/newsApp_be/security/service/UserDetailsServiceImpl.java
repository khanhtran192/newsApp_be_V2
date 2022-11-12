package com.newsApp_be.security.service;

import com.newsApp_be.entity.User;
import com.newsApp_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmailAndEnable(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng hoặc người dùng bị khóa: " + username));
        return UserDetailsImpl.build(user);
    }
}
