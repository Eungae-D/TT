package gyuho.triptogether.domain.exception.responseCode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserExceptionResponseCode {


    //404 NOT FOUND
    EXISTS_USER(HttpStatus.CONFLICT, "U-001", "이미 존재하는 유저입니다.");





    private final HttpStatus status;
    private final String code;
    private final String message;

    UserExceptionResponseCode(HttpStatus status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}