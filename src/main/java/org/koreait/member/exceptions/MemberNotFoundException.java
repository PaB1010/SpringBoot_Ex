package org.koreait.member.exceptions;

import org.koreait.global.exceptions.CommonException;
import org.koreait.global.exceptions.scripts.AlertBackException;
import org.springframework.http.HttpStatus;

/**
 * 회원 조회 실패 Error
 * 응답 코드 - 404
 *
 */
public class MemberNotFoundException extends AlertBackException {

    public MemberNotFoundException() {

        super("NotFound.member", HttpStatus.NOT_FOUND);

        setErrorCode(true);
    }
}
