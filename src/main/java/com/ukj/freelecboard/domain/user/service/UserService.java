package com.ukj.freelecboard.domain.user.service;

import com.ukj.freelecboard.domain.user.User;
import com.ukj.freelecboard.domain.user.dto.UserResponseDto;
import com.ukj.freelecboard.domain.user.repository.UserRepository;
import com.ukj.freelecboard.domain.user.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long save(UserSaveRequestDto requestDto) {
        requestDto.setPassword1(passwordEncoder.encode(requestDto.getPassword1()));
        return userRepository.save(requestDto.toEntity()).getId();
    }

    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        return new UserResponseDto(user);
    }

    public UserResponseDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        return new UserResponseDto(user);
    }
}
