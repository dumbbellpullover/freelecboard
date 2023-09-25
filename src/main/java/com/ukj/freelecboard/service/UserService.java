package com.ukj.freelecboard.service;

import com.ukj.freelecboard.domain.user.UserRepository;
import com.ukj.freelecboard.web.dto.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long save(UserSaveRequestDto requestDto) {
        requestDto.setPassword1(passwordEncoder.encode(requestDto.getPassword1()));
        return userRepository.save(requestDto.toEntity()).getId();
    }
}
