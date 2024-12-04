package org.koreait.global.configs;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        
        // resource dir의 properties로 MessageSource를 구현한 구현체
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        
        // addBasenames 메서드의 매개 변수가 가변형 (...) 이므로 더 필요하면 , 하고 추가 기입하면 됨
        ms.addBasenames("messages.commons", "messages.validations", "messages.errors");

        // 인코딩 설정을 UTF-8로 고정
        ms.setDefaultEncoding("UTF-8");
        
        // 메시지 코드를 발견하지 못하면 해당 코드를 메세지로 사용
        // th:text="#{해당코드}" 그대로 해당코드 텍스트를 메세지 코드로 사용해 출력
        ms.setUseCodeAsDefaultMessage(true);

        return ms;
    }
}