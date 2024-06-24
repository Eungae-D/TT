package gyuho.triptogether.domain.exception.exception;

import gyuho.triptogether.domain.exception.responseCode.TokenExceptionResponseCode;
import gyuho.triptogether.domain.exception.responseCode.UserExceptionResponseCode;
import lombok.Getter;

@Getter
public class TokenException extends RuntimeException{
    private TokenExceptionResponseCode exceptionCode;

    private String log;

    public TokenException(TokenExceptionResponseCode exceptionCode, String log){
        this.exceptionCode = exceptionCode;
        this.log = log;
    }
}
