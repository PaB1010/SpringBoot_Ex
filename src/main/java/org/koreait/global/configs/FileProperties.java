package org.koreait.global.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * application.yml 설정을 주입받은 Data 클래스
 *
 */
@Data
@ConfigurationProperties(prefix = "file.upload")
public class FileProperties {

    // file.upload.path
    private String path;

    // file.upload.url
    private String url;

}