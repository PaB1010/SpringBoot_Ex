package org.koreait;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Exam05Application {

	// 독립적인 Application으로 main 함수 존재 (자동 탑재 Server)
	// http://localhost:8080/
	public static void main(String[] args) {


		SpringApplication.run(Exam05Application.class, args);
	}
}