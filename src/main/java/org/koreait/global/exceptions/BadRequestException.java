package org.koreait.global.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Error Code 가장 자주 쓰이는 400대 (BAD_REQUEST)
 *
 */
public class BadRequestException extends CommonException {

  /**
   * '잘못된 요청입니다' 라는 문구로 고정,
   *  응답 코드 - 400
   */
  public BadRequestException() {

    this("BadRequest");

    // Error Code, Message 연동
    setErrorCode(true);
  }

  public BadRequestException(String message) {

      super(message, HttpStatus.BAD_REQUEST);
    }
}
