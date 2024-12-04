package org.koreait.jpaex;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.Test;
import org.koreait.member.entities.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

// ({application.yml, application-test.yml})
@ActiveProfiles({"default", "test"})
@SpringBootTest
@Transactional
public class Ex01 {

    // @Autowired 도 가능하지만 좀 더 범위가 상세한 Annotation 사용
    @PersistenceContext
    // Entity 관리 Manager 객체 의존 주입
    private EntityManager em;

    @Test
    void test1() {

        Member member = new Member();

        member.setSeq(1L);
        member.setEmail("user01@test.org");
        member.setName("사용자01");
        member.setPassword("12345678");
        member.setRegDt(LocalDateTime.now());

        // Entity를 영속성 Context(관리 메모리)에 넣어줌
        // -> 변화 감지를 할 수 있는 상태가 됨)
        em.persist(member);

        // DB에 영구 반영 = SQL 실행 - INSERT Query
        em.flush();

        member.setName("(수정)사용자01");

        // 변화 감지에 따라서 UPDATA Query 실행
        em.flush();

        /**
         * 삭제 X, 영속성 Context(변화 감지 메모리)에서
         * 상태를 제거 상태로 변경
         * 
         * flush()해야 삭제
         */
        em.remove(member);
        
        // 변화 감지에 따라 DELETE Query 실행
        em.flush();
    }
}