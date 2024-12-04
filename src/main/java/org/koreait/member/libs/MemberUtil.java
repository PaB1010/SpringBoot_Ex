package org.koreait.member.libs;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 권한 여부, 회원 정보 조회 등
 *
 */
@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;

    // 로그인 여부 체크 편의 기능
    public boolean isLogin() {

        return session.getAttribute("loggedMember") != null;
    }
}