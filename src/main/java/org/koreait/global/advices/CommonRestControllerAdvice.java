package org.koreait.global.advices;

import org.koreait.global.exceptions.CommonException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @RestControllerAdvice
 *
 * 모든 @RestController Annotation 적용된
 * 모든 RestController Bean 요청 메서드 실행 전 - AOP Programing
 *
 */
@RestControllerAdvice(annotations = RestController.class)
public class CommonRestControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e) {

        // 추후 포켓몬에서 제대로 적용
        return e.getMessage();
    }
}