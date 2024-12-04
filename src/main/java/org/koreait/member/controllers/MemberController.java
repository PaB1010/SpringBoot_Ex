package org.koreait.member.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.koreait.global.exceptions.CommonException;
import org.koreait.global.exceptions.scripts.AlertRedirectException;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Member;
import org.koreait.member.exceptions.MemberNotFoundException;
import org.koreait.member.services.LoginService;
import org.koreait.member.validators.JoinValidator;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j // log 변수 생성(Lombok)
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final JoinValidator joinValidator;

    private final LoginService loginService;

    private final MessageSource messageSource;


    /*
    // 회원 가입 양식
    @GetMapping("/join")
    public String join(Model model) {


//         비어있는 requestJoin이라도 있어야 Get방식에서도 join 페이지 접근 가능
//         joinForm의
//         <form method="POST" th:action="@{/member/join}" autocomplete="off"
//                     th:object="${requestJoin}">
//         여기서 th:ojbect에 requestJoin이 있어야만 하기 때문
        RequestJoin requestJoin = new RequestJoin();
        model.addAttribute("requestJoin", requestJoin);

        return "member/joinForm";
    }
    */

    // 속성명 apples, MemberController 내에서 전역 유지
    @ModelAttribute("apples")
    public List<String> apples() {

        return List.of("사과1", "사과2", "사과3");
    }

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form) {
        // @ModelAttribute = RequestJoin - requestJoin 비어있는 객체 생성
        // RequestJoin(email=null, password=null, confirmPassword=null, name=null, mobile=null, agree=false)

        return "member/joinForm";
    }


    /**
     * 회원 가입 처리 - 레코드 클래스
     * @Valid : 검증할 커맨드 객체임을 표시
     * @Valid가 있으면 반드시 Errors 객체는 커맨드 객체 바로 다음에 붙어서 나와야함
     *
     * @return
     */
    // 회원 가입 처리 - 레코드 클래스
    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors) {


        // 커맨드 객체 검증 (전역 Validator 있을시 해당 코드는 없어도 됨)
        joinValidator.validate(form, errors);

        // 검증 실패! - reject, rejectValue가 한번이라도 호출 됨
        if (errors.hasErrors()) {

            // 검증 실패시 사용자에게 양식을 다시 보여주고, 검증 실패 정보를 제공해야 함
            return "member/joinForm";
        }

        // 검증 성공시 - 가입 처리 서비스 호출
        return "redirect:/member/login";

        /*
        @Slf4j - Lombok (로그 레벨 출력)

        log.error(form.toString());
        log.warn(form.toString());
        log.info(form.toString());
        log.debug(form.toString());
        log.trace(form.toString());
         */

    }

    @GetMapping("/login")
    public String login(@ModelAttribute RequestLogin form, @CookieValue(name="savedEmail", required = false) String savedEmail) {
        /*
        if (true) {
            throw new AlertRedirectException("테스트 예외", "/member/join", HttpStatus.BAD_REQUEST);
        }
        */
        // @CookieValue = Cookie 단일 조회
        if (savedEmail != null) { // Cookie 값이 있다면
            form.setEmail(savedEmail);
            form.setSaveEmail(true);
        }

        return "member/login";
    }

    @PostMapping("/login")
    public String loginPS(@Valid RequestLogin form, Errors errors /*, HttpServletRequest request, HttpSession session*/ ) {
        // Session 가져오는 기능 2번
        // 매개변수에 HttpSession session

        if (errors.hasErrors()) {

            return "member/login";
        }

        // Session 가져오는 기능 1번
        // 매개 변수에 HttpServletRequest request 하고 getSession()
        // HttpSession session = request.getSession();

        // 검증에 이상이 없는 상태 -> Login 처리

        loginService.process(form);
        
        return "redirect:/";
    }

    // Get, Post 모두 상관없도록 RequestMappin
    @RequestMapping("/logout")
    public String logout(HttpSession session) {

        // Session 비우기
        session.invalidate();

        return "redirect:/member/login";
    }

    /**
     * MemberController 공통 검증
     * @Valid가 붙어있는 커맨드 객체 공통 검증 처리
     *
     * @param binder
     */

    /*
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(joinValidator);
    }
     */

    /*
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

        // 내가 정의한 예외(CommonException)일 경우 정의한 예외로 대체
        if (e instanceof CommonException commonException) {

            status = commonException.getStatus();
            if (commonException.isErrorCode()) {

                message = messageSource.getMessage(message, null, request.getLocale());
            }
        }

        // 응답 코드(Enum이니 상수)를 숫자(정수)로 가져옴
        // response.setStatus(status.value());

        // model.addAttribute("message", e.getMessage());
        
        // ModelAndView 객체로 응답 설정
        ModelAndView mv = new ModelAndView();

        mv.setStatus(status);
        mv.addObject("message", message);
        mv.setViewName("error/errorPage");

        return mv;
    }
     */

    @ResponseBody // 일반 Controller 내에서도 Rest 형식으로 변환
    @GetMapping("/test")
    public List<Member> test() {

        List<Member> members = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {

            Member member = new Member();

            member.setSeq((long)i);
            member.setName("사용자" + i);
            member.setEmail("user" + i + "@test.org");
            member.setPassword("123455678");
            member.setAuthority(Authority.USER);
            member.setRegDt(LocalDateTime.now());
            member.setModDt(LocalDateTime.now());

            members.add(member);
        }

        return members;
    }

}