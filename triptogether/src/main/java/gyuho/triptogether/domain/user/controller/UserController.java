package gyuho.triptogether.domain.user.controller;

import gyuho.triptogether.domain.user.dto.request.EmailRequestDTO;
import gyuho.triptogether.domain.user.dto.request.SignUpRequestDTO;
import gyuho.triptogether.domain.user.service.UserService;
import gyuho.triptogether.global.util.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 이메일 중복 체크
    @PostMapping("/email-check")
    public ResponseEntity<ApiResponse<?>> emailCheck(@Valid @RequestBody EmailRequestDTO emailRequestDTO){
        boolean result = userService.emailCheck(emailRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createSuccess(result, "이메일 중복 체크 성공. (true = 중복, false = 사용 가능)"));
    }

    // 회원가입
    @PostMapping()
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO){
        userService.register(signUpRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccessNoContent("회원가입 성공."));
    }
}
