package org.koreait.question.controllres;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/question")
@SessionAttributes({"requestQuestion", "testField"})
// requestQuestion 이름인 값은 session 범위에서 관리
public class QuestionController {

    // SessionAttribute 사용을 위해 step1의 매개변수가 아닌 이곳에 생성자 함수 기입
    @ModelAttribute("requestQuestion")
    public RequestQuestion requestQuestion() {

        return new RequestQuestion();
    }

    @GetMapping("/step1")
    public String step1() {

        return "question/step1";
    }

    @PostMapping("/step2")
    public String step2(RequestQuestion form) {

        // {} = 대체될 부분들
        log.info("step : {} / {}", 1, form.toString());
        /*
        Attribute 설정 없이 Request 범위일때
        step : 2 / RequestQuestion(q1=null, q2=null, q3=null, q4=답4, q5=답5, q6=답6, q7=null, q8=null, q9=null)
        -> step1의 값들이 날아가있음

        @SessionAttribute시
        step : 2 / RequestQuestion(q1=답1, q2=답2, q3=답3, q4=답4, q5=답5, q6=답6, q7=null, q8=null, q9=null)
        ->  step1의 값 유지
         */

        return "question/step2";
    }

    @PostMapping("/step3")
    public String step3(RequestQuestion form) {

        log.info("step : {} / {}", 2, form.toString());

        return "question/step3";
    }

    @PostMapping("/save")
    public String save(RequestQuestion form, SessionStatus status) {  // 양식 작업 완료!

        log.info("step : {} / {}", 3, form.toString());
        
        // 더이상 Model을 통한 값 추가를 방지! -> 처리 완료 시점에 적용
        // true / false 값이 들어있음
        status.setComplete();

        return "question/step3";
    }

    // template 없이 test 값 확인하려 임시로 넣은 Annotation
    @ResponseBody
    @GetMapping("/test1")
    public void test1(@SessionAttribute("requestQuestion") RequestQuestion form) {

        log.info("test : {} / {}", 1, form.toString());
    }

    /*
    // Session 직접 조회 (비권장) -> Model 이용한 조회 권장
    // class 상단 @SessionAttributes에 testField
    @ResponseBody
    @GetMapping("/test2")
    public void test2(HttpSession session) {

        session.setAttribute("testField", "테스트 값...");
    }
*/

    @ResponseBody
    @GetMapping("/test2")
    public void test(Model model) {
        model.addAttribute("testField", "테스트 값2...");
    }

    @ResponseBody
    @GetMapping("/test3")
    public void test3(@SessionAttribute("testField") String value) {
        // Session 값을 조회해 value에 대입 (형변환도 자동)

        log.info("testField : " + value);
        // testField : 테스트 값...
    }
}