package org.koreait.global.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 사이트 모든 페이지 공통 처리 부분
 *
 */

@Component
public class CommonInterceptor implements HandlerInterceptor {

    /**
     * Controller Bean 요청 메서드 실행 완료 & ModelAndView 반환 직후 호출
     * 
     * View(응답 template)에서 공유할 데이터 처리할 때 주로 사용(ModelAndView를 사용해서)
     * 처리에는 관여 X, 값만 추가
     *
     */
    /*
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        modelAndView.addObject("commonMessage", "공통 메세지!");
    }
     */
}