package org.koreait.global.configs;

import lombok.RequiredArgsConstructor;
import org.koreait.global.interceptors.CommonInterceptor;
import org.koreait.global.interceptors.MemberOnlyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    // 바로 의존 주입
    private final CommonInterceptor commonInterceptor;
    private final MemberOnlyInterceptor memberOnlyInterceptor;

    // 설정
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // Interceptor 객체 입력받음
        // 매개변수는 HandlerInterceptor interceptor
        // 어떤 Interceptor가 올지 모르니다형성
        registry.addInterceptor(commonInterceptor); // "/**" 생략해도 모든 범위에 적용됨
                // .addPathPatterns("/**");
                // 모든 경로에 적용

        registry.addInterceptor(memberOnlyInterceptor)
                .addPathPatterns("/mypage/**");
                // mypage를 포함한 모든 하위 경로에 적용
    }
}