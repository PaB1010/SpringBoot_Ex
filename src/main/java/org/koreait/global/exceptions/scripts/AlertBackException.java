package org.koreait.global.exceptions.scripts;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * 예외 발생하면 이후 이전 Page로 돌아가도록 응답
 * Alert('메시지'); -> target .. history.back()
 *
 */
@Getter @Setter
public class AlertBackException extends AlertException {

    private String target;



    // target - 알람창의 위치
    public AlertBackException(String message, HttpStatus status, String target) {

        super(message, status);

        this.target = target;
    }

    public AlertBackException(String message, HttpStatus status) {

        this(message, status, "self");
    }
}