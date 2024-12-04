package org.koreait.global.advices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.global.exceptions.CommonException;
import org.koreait.global.exceptions.scripts.AlertBackException;
import org.koreait.global.exceptions.scripts.AlertException;
import org.koreait.global.exceptions.scripts.AlertRedirectException;
import org.koreait.member.libs.MemberUtil;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @ControllerAdvice
 *
 * 모든 @Controller Annotation 적용된
 * 모든 Controller Bean 요청 메서드 실행 전 - AOP Programing
 *
 */
@ControllerAdvice(annotations = Controller.class)
@RequiredArgsConstructor
public class CommonControllerAdvice {

    private final MessageSource messageSource;

    private final HttpServletRequest request;

    private final MemberUtil memberUtil;


    /**
     * org.koreait package를 포함한 모든 하위 패키지의 Controller에서 공유할 값
     *
     * @ModelAttribute를 @ControllerAdvice에서 쓰는 방법
     * @ModelAttribute를 @Controller에서 사용시 해당 Controller 내부가 범위
     *
     * @return
     */
    @ModelAttribute("isLogin")
    public boolean isLogin() {

        return memberUtil.isLogin();
    }

    // 예외가 발생할 수 있는 Class 클래스 객체를 매개변수에
    // {배열 형태}로 여러개
    // @ExceptionHandler({MemberNotFoundException.class, IllegalArgumentException.class})
    // Exception.class 하면 모든 예외가 포함
    @ExceptionHandler(Exception.class)
    // 모든 예외에 적용되도록 매개변수에 다형성 이용한 Exception e 사용
    public ModelAndView errorHandler(Exception e, Model model, HttpServletRequest request) {

        e.printStackTrace();

        // 기본 응답 코드 = 500
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        // 기본 메세지
        String message = e.getMessage();

        // 공통적인 errorPage
        String tpl = "error/errorPage";

        // ModelAndView 객체로 응답 설정하기 위해
        ModelAndView mv = new ModelAndView();

        // 내가 정의한 예외(CommonException)일 경우 정의한 예외로 대체
        if (e instanceof CommonException commonException) {

            status = commonException.getStatus();
            if (commonException.isErrorCode()) {

                message = messageSource.getMessage(message, null, request.getLocale());
            }

            // 계속 추가될거니까 Buffer 할당
            StringBuffer sb = new StringBuffer(1000);

            // AlertException 인지 체크 후 JavaScript 실행 alert 출력
            if (e instanceof AlertException) {

                sb.append(String.format("alert('%s');", message));

                // JavaScript 실행해 alert 출력
                tpl = "common/_execute_script";
            }

            if (e instanceof AlertBackException alertBackException) {

                String target = alertBackException.getTarget();

                sb.append(String.format("%s.history.back();", target));
            }

            if (e instanceof AlertRedirectException alertRedirectException) {

                String target = alertRedirectException.getTarget();
                String url = alertRedirectException.getUrl();

                // replace 사용해서 이동해야 기록이 남지 않아 다시 POST 되지 않음
                sb.append(String.format("%s.location.replace('%s%s');", target, request.getContextPath() ,url));
            }

            mv.addObject("script", sb.toString());
        }

        // 응답 코드(Enum이니 상수)를 숫자(정수)로 가져옴
        // response.setStatus(status.value());
        // model.addAttribute("message", e.getMessage());

        mv.setStatus(status);
        mv.addObject("message", message);
        mv.setViewName(tpl);

        return mv;
    }
}