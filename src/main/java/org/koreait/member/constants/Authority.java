package org.koreait.member.constants;

import jakarta.persistence.Enumerated;

/**
 * Member 권한 Enum Class
 */
public enum Authority {

    // 일반 회원
    USER,

    // 부관리자
    MANAGER,

    // 최고 관리자
    ADMIN
}
