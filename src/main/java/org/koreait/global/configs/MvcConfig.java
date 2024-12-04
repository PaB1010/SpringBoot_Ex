package org.koreait.global.configs;

import lombok.RequiredArgsConstructor;
import org.koreait.etc.Greet;
import org.koreait.member.validators.JoinValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @EnableWebMvc = 불필요
// @RequiredArgsConstructor
@Configuration
@EnableScheduling // 스케쥴링 설정 자동화
@EnableJpaAuditing // Entity 변화 감지 Listeners 설정 자동화
public class MvcConfig implements WebMvcConfigurer {

    // 전역 검증용 의존 주입
    // private final JoinValidator joinValidator;

    /**
     * 모든 @Valid가 적용된 커맨드 객체 검증에 사용
     * 이런 방식은 적합X 단순 교육용
     * @return
     */

    /*
    @Override
    public Validator getValidator() {
        return joinValidator;
    }
     */

    @Bean
    // prod 가 들어오면 GreetProd 생성
    // @Profile("prod")
    // Bean이 충돌될때 가장 먼저 선택
    // @Primary
    public Greet greetProd() {

        System.out.println("prod profile!");
        Greet greet = new Greet();
        greet.setName("prod에서..");

        return greet;
    }

//    @Bean
//    @Profile("dev")
//    public Greet greetDev() {
//
//        System.out.println("dev profile!");
//        Greet greet = new Greet();
//        greet.setName("dev에서..");
//
//        return greet;
//    }
}