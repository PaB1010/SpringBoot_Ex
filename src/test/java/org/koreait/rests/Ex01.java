package org.koreait.rests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.member.controllers.ApiMemberController;
import org.koreait.member.controllers.RequestLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * REST API TEST
 *
 */
@SpringBootTest
// @AutoConfigureMockMvc // Mock Mvc(Server 켜지 않고 test) 가능
@ActiveProfiles({"default", "test"})
public class Ex01 {

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {

        // 모든 Controller Bean을 생성하고 Test
        // mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        // 계속 Bean이 생성되므로 Test 연산이 느려짐

        // 현재 Test 하는 ApiMemberController 에만 한정 짓는 것이 연산 이득
        mockMvc = MockMvcBuilders.standaloneSetup(ApiMemberController.class).build();
    }

    @Test
    void test1() throws Exception {

            // perform.(요청이 왔을때 어떻게 할지)
            // andDo & andExpect & andExpectAll & andReturn
            mockMvc.perform(get("/api/member/test1"))
                    .andDo(print());

    }

    @Test
    void test2() throws Exception {

        // ApiMemberController.test3
        mockMvc.perform(post("/api/member/test3")
                        // Content-type : application/x-www-form-urlencoded
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "user01@test.org")
                .param("password", "1235678")
                .param("saveEmail", "true")
        ).andDo(print());

        /**
         * 요청 Body Data 형식
         * email = user01@test.org & password = 12345678 & saveEmail = true
         */
    }

    /**
     * JSON 문자열 형태로 요청 Body에 직접 Data 실어서 보내기
     * @throws Exception
     */
    @Test
    void test3() throws Exception {

        RequestLogin form = new RequestLogin();

        // form.setEmail("user01@test.org");
        form.setPassword("12345678");
        form.setSaveEmail(true);

        // JAVA 객체 <-> JSON 문자열
        ObjectMapper om = new ObjectMapper();

        // JAVA 객체를 JSON으로 바꿔주는 편의 기능
        String json = om.writeValueAsString(form);

        System.out.println(json);

        mockMvc.perform(post("/api/member/test3")
                // Content-type : application/json
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andDo(print());
    }
}