package gyuho.triptogether.domain.user.service.impl;

import gyuho.triptogether.domain.user.dto.request.SignUpRequestDTO;
import gyuho.triptogether.domain.user.repository.UserRepository;
import gyuho.triptogether.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service //로그 변수 선언 자동으로 해주는 어노테이션
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public void signUp(SignUpRequestDTO signUpRequestDTO){

    }

}
