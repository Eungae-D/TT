package gyuho.triptogether.domain.user.service;

import gyuho.triptogether.domain.user.dto.request.EmailRequestDTO;
import gyuho.triptogether.domain.user.dto.request.SignUpRequestDTO;


public interface UserService {

    // 이메일 중복 체크
    boolean emailCheck(EmailRequestDTO emailRequestDTO);
    // 일반 회원가입
    void register(SignUpRequestDTO signUpRequestDTO);
}
