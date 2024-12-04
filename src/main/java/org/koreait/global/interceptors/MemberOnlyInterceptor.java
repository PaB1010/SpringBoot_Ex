package org.koreait.global.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.koreait.member.libs.MemberUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class MemberOnlyInterceptor implements HandlerInterceptor {

    private final MemberUtil memberUtil;

    /**
     * 
     * Controller Bean의 요청 메서드 실행 전 호출
     *
     * Controller Bean 요청 메서드를 시행하기 전 공통 처리 사항 정의
     *
     * 반환 값 true : Controller Bean 요청 메서드 실행
     * 반환 값 false : Controller Bean 요청 메서드 실행 X
     * 반환 값이 논리 값인 이유 = 화면에 대한 통제
     * 
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // System.out.println("preHandle() 호출!");

        // Login 상태일때만 mypage 노출(Controller Bean 요청 메서드 실행)
        if (memberUtil.isLogin()) {

            return true;
        }

        /**
         * 미 로그인 상태면 로그인 페이지 이동
         * 로그인 완료 후 mypage로 자동 이동
         */
        String url = String.format("%s%s?redirectUrl=%s", request.getContextPath(), "/member/login", "/mypage");

        response.sendRedirect(url);



        // return true;
        // mypage.html 정상 출력

        return false;
        // Controller Bean 요청 메서드 실행 X = 화면 보이지 않음 
    }
}