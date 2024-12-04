package org.koreait.member.controllers;

import jakarta.validation.Valid;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.koreait.global.exceptions.BadRequestException;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rest Controller
 * 
 * 주로 JSON 문자열로 반환
 *
 */
@Slf4j
@RestController // 반환값이 JSON, void면 응답 body 없는 상태
@RequestMapping("/api/member")
public class ApiMemberController {

    // Data Class를 getter 이용해 JSON 문자열로 변환해 반환
    // produces = 응답 Body Content Type 변경
    // consumes = 요청 Body content Type 변경
    // MediaType Enum 상수 사용
    // @GetMapping(path = "/test1", produces = MediaType.TEXT_XML_VALUE)
    @GetMapping("/test1")
    public Member test1() {

        Member member = new Member();

        member.setSeq(1L);
        member.setName("이이름");
        member.setEmail("user01@test.org");
        member.setPassword("123455678");
        member.setAuthority(Authority.USER);
        member.setRegDt(LocalDateTime.now());
        member.setModDt(LocalDateTime.now());

        return member;
    }

    @GetMapping("/test2")
    public List<Member> test2() {

        List<Member> members = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {

            Member member = new Member();

            member.setSeq((long) i);
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

    /**
     * @param form
     * @RequestBody 커맨드 객체 앞에 적용하면 요청 Body Data 형식이 applicaion/json 임을 알게 됨
     * <p>
     * 기본 값은 json이 아닌 xml
     */
    @PostMapping("/test3")
    @ResponseStatus(HttpStatus.CREATED) // 201로 응답코드 변경
    public void test3(@RequestBody @Valid RequestLogin form, Errors errors) {

        if (errors.hasErrors()) {

            String message = errors.getAllErrors().stream().flatMap(c -> Arrays.stream(c.getCodes())).collect(Collectors.joining(","));

            throw new BadRequestException(message);
        }
    }

    /*
    @ExceptionHandler(Exception.class)
    public String errorHandler() {

        return null;
    }
     */

    /*
    public ResponseEntity<Void> test3(@RequestBody RequestLogin form) {
        // log.info(form.toString());

        // 응답 Data 없음
        return ResponseEntity.noContent().build();
    }
     */

    /*
    // Body 없는 경우 <Void>
    public ResponseEntity<Void> test3(@RequestBody RequestLogin form) {
        // log.info(form.toString());

        // 응답 Data 상세 설정
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("KOREAIT", "FIGHTING!")
                // 응답 Body Data가 없는 경우 build()
                .build();
    }
     */

    /*
    // ResponseEntity<Body Data 자료형>
    public ResponseEntity<RequestLogin> test3(@RequestBody RequestLogin form) {
        // log.info(form.toString());

        // 응답 Data 상세 설정
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("KOREAIT", "FIGHTING!")
                .body(form);
    }
     */

    /*
    public void test3(@RequestBody RequestLogin form) {

        log.info(form.toString());
    }
     */
}