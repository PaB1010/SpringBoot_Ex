package org.koreait.global.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * File 관련 설정을 application.yml 에서 가져와서
 * @value 로 변수에 주입
 *
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileUploadConfig implements WebMvcConfigurer {

    /*
    @Value("${file.upload.path}")
    private String filePath;

    @Value("${file.upload.url}")
    private String fileUrl;
     */

    /**
     * addResuorceHandlers 이용한 application.yml 설정 주입
     *
     */
    private final FileProperties properties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(properties.getUrl() + "**")
                .addResourceLocations("file:///" + properties.getPath());
    }

    /*
    // File 정적 경로 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(fileUrl + "**")
                // /를 3개써야 한개만 제거되고 두개 남아 //가 됨
                .addResourceLocations("file:///" + filePath);
    }
     */
}