package gyuho.triptogether.domain.user.service;

import gyuho.triptogether.domain.user.dto.request.SignUpRequestDTO;


public interface UserService {

    //일반 회원가입
    void signUp(SignUpRequestDTO signUpRequestDTO);
}
