package org.koreait.member.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.koreait.member.controllers.RequestLogin;
import org.koreait.member.entities.Member;
import org.koreait.member.exceptions.MemberNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final HttpSession session;
    private final HttpServletResponse response;

    public void process(RequestLogin form) {

        // 임시 - CommonException 처리 test
        String email = form.getEmail();

        if (!email.equals("user01@test.org")) {

            throw new MemberNotFoundException();
        }


        /** DB에서 데이터를 가져왔다고 가정하고 로그인 기능 구현 */
        Member member = new Member();
        member.setSeq(1L);
        member.setEmail(form.getEmail());
        member.setName("이이름");
        member.setRegDt(LocalDateTime.now());


        /**
         *  Browser별로 유지할 수 있게 Session에 값을 부여해주는 것
         *  -> Login
         *
         *  loggedMember 속성값이 있으면 Login 상태라는 뜻
         */
        session.setAttribute("loggedMember", member);

        /**
         * 이메일 기억하기
         *
         * 체크되어 있으면 Cookie 값에 저장
         * 체크 해제되어 있으면 Cookie 삭제 요청
         */

        // 실습용 코드 - 임의로 쿠키 지정
        Cookie cookie = new Cookie("savedEmail", form.getEmail());
        
        if (form.isSaveEmail()) {
            // 한달
            cookie.setMaxAge(60 * 60 * 24 * 30);
        } else {
            cookie.setMaxAge(0);
        }

        response.addCookie(cookie);
    }
}