package gyuho.triptogether.domain.user.service.impl;

import gyuho.triptogether.domain.exception.exception.UserException;
import gyuho.triptogether.domain.exception.responseCode.UserExceptionResponseCode;
import gyuho.triptogether.domain.user.dto.request.EmailRequestDTO;
import gyuho.triptogether.domain.user.dto.request.SignUpRequestDTO;
import gyuho.triptogether.domain.user.entity.User;
import gyuho.triptogether.domain.user.repository.UserRepository;
import gyuho.triptogether.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service //로그 변수 선언 자동으로 해주는 어노테이션
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    // 이메일 중복확인
    @Override
    @Transactional(readOnly = true)
    public boolean emailCheck(EmailRequestDTO emailRequestDTO){

        return userRepository.existsByEmail(emailRequestDTO.getEmail());
    }

    // 회원가입
    @Override
    public void register(SignUpRequestDTO signUpRequestDTO){

        //이메일 중복 확인
        if(userRepository.existsByEmail(signUpRequestDTO.getEmail())){
            throw new UserException(UserExceptionResponseCode.EXISTS_USER, "이미 존재하는 유저입니다.");
        }

        User user = signUpRequestDTO.toEntity(encoder.encode(signUpRequestDTO.getPassword()));

        userRepository.save(user);
    }
}
