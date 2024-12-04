package org.koreait.global.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * 공통 예외 처리 - 유연하게 처리 위해 RuntimeException 상속
 *
 */

// 응답 코드 가져오기 위해 Get & Set 추가
@Getter @Setter
public class CommonException extends RuntimeException {

    // HttpStatus - Spring에서 지원해주는 응답 코드(Enum Class)
    private HttpStatus status;

    // message의 Error Code 여부, 단순 문자열이 아니라 Error Code 여야 국가 사용 언어별 처리 가능
    private boolean errorCode;

    public CommonException(String message, HttpStatus status) {

        super(message);

        this.status = status;
    }
}