package org.koreait.global.validators;

/**
 * 휴대폰 번호(Mobile) 검증
 * 전역에서 필요할때만 꺼내 쓸 수 있도록
 * Interface로 구축
 */
public interface MoblieValidator {

    default boolean checkMobile(String num) {

        /**
         * 01010001000
         * 010-1000-1000
         * 010.1000.1000
         * 010 1000 1000
         *
         * 1) 숫자로만 통일성 있게 변환
         * 2) 패턴
         */

        // 정규 표현식 : 숫자(\\D)가 아닌 문자는 모두 제거
        num = num.replaceAll("\\D", "");

        // 정규 표현식 : 자리수
        String pattern = "^01[016]\\d{3,4}\\d{4}$";

        return num.matches(pattern);
    }
}