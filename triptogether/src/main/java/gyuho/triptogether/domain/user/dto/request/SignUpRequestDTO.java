package gyuho.triptogether.domain.user.dto.request;

import gyuho.triptogether.domain.user.entity.Role;
import gyuho.triptogether.domain.user.entity.SocialType;
import gyuho.triptogether.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDTO {

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @Pattern(regexp = "^[a-zA-Z가-힣0-9]*$", message = "닉네임은 한글, 영문, 숫자만 입력 가능합니다.")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Size(min = 2, max =10, message = "닉네임은 2자이상 10자 이하로 입력해주세요.")
    private String nickname;


    public SignUpRequestDTO(String email, String password, String nickname){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public User toEntity(String passwordEncoding){
        return User.builder()
                .socialType(SocialType.GENERAL)
                .email(email)
                .password(passwordEncoding)
                .nickname(nickname)
                .role(Role.USER)
                .profileImage(null)
                .build();
    }

}
