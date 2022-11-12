package com.newsApp_be.services.impl;

import com.newsApp_be.dto.request.UserInfoDTO;
import com.newsApp_be.dto.request.VerifyEmailRequest;
import com.newsApp_be.exception.BadRequestException;
import com.newsApp_be.entity.ERole;
import com.newsApp_be.entity.User;
import com.newsApp_be.repository.UserRepository;
import com.newsApp_be.security.jwt.JwtUtils;
import com.newsApp_be.security.request.LoginRequest;
import com.newsApp_be.security.request.SignupRequest;
import com.newsApp_be.security.response.JwtResponse;
import com.newsApp_be.security.response.MessageResponse;
import com.newsApp_be.security.response.ResponseStatus;
import com.newsApp_be.security.service.UserDetailsImpl;
import com.newsApp_be.services.UserService;
import com.newsApp_be.utils.ThreadSendEmailOTP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder encoder;

    private final ThreadSendEmailOTP threadSendEmailOTP;

    private final UserRepository userRepository;


    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        User user = userRepository.findByEmail(userDetails.getEmail()).get();
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                user.getFullName(),
                userDetails.getEmail(),
                roles));
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsernameAndEmailAndEnable(signupRequest.getUsername(), signupRequest.getEmail(), true)) {
            return new ResponseEntity<>("Username hoặc email đã tồn tại!", HttpStatus.BAD_REQUEST);
        }

        User user;
        int randomCode = 1902;
        if (Boolean.TRUE.equals(userRepository.existsByEmailAndEnable(signupRequest.getEmail(), false))) {
            user = userRepository.findByEmail(signupRequest.getEmail())
                    .orElseThrow(() -> new BadRequestException("Tài khoản chưa xác thực"));
            user.setPassword(encoder.encode(signupRequest.getPassword()));
            user.setVerifyCode(String.valueOf(randomCode));
        } else {
            user = User.builder()
                    .username(signupRequest.getUsername())
                    .email(signupRequest.getEmail())
                    .fullName(signupRequest.getFullname())
                    .createdTime(new Timestamp(System.currentTimeMillis()))
                    .verifyCode(String.valueOf(randomCode))
                    .email(signupRequest.getEmail())
                    .password(encoder.encode(signupRequest.getPassword()))
                    .role(ERole.ROLE_USER)
                    .enable(false).build();
        }
        userRepository.save(user);
        threadSendEmailOTP.start(signupRequest.getEmail(), String.valueOf(randomCode));
        return new ResponseEntity<>("Mã xác thực đã được gửi. Vui lòng xác thực tài khoản", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> verifyEmail(VerifyEmailRequest request) {
        if (userRepository.existsByEmailAndEnable(request.getEmail(), true)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse<>("Lỗi: Email đã được sử dụng", ResponseStatus.FAIL.getValue()));
        }
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Lỗi: Email không tồn tại"));
        String code = Objects.isNull(user.getVerifyCode()) ? "0" : user.getVerifyCode();
        if (code.equals(request.getCode())) {
            user.setEnable(true);
            user.setVerifyCode(null);
            userRepository.save(user);
            return ResponseEntity.ok()
                    .body(new MessageResponse<>("Xác thực email thành công",
                            ResponseStatus.SUCCESS.getValue(),
                            Map.of("email", request.getEmail())));
        }
        return ResponseEntity.badRequest()
                .body(new MessageResponse<>("Sai mã xác thực hoặc mã xác thực hết thời hạn",
                        ResponseStatus.FAIL.getValue()));
    }


    @Override
    public ResponseEntity<?> getUserInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmailAndEnable(email, true)
                .orElseThrow(() -> new BadRequestException("Lỗi: Email không tồn tại hoặc chưa được kích hoạt"));
        UserInfoDTO userInfoDTO = user.toUserInfoDTO();
        if (!StringUtils.isEmpty(user.getAvatar())){
            userInfoDTO.setLinkAvatar("");
        }
        return ResponseEntity.ok()
                .body(new MessageResponse<>(ResponseStatus.SUCCESS.getValue(), userInfoDTO));
    }

    @Override
    public ResponseEntity<?> updateUserInfo(UserInfoDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmailAndEnable(email, true)
                .orElseThrow(() -> new BadRequestException("Lỗi: Email không tồn tại hoặc chưa được kích hoạt"));
        if (!StringUtils.isEmpty(request.getAvatar())) {
            user.setAvatar(request.getAvatar());
        }
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(user);
        return ResponseEntity.ok()
                .body(new MessageResponse<>("Xác thực thông tin thành công",
                        ResponseStatus.SUCCESS.getValue(),
                        user.toUserInfoDTO()));
    }


}
