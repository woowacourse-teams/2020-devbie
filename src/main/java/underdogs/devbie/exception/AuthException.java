package underdogs.devbie.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthException extends RuntimeException{

    public AuthException(String reason) {
        super(String.format("인증 에러가 발생하였습니다. 이유 : %s", reason));
    }
}
