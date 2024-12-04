package org.koreait.jpaex;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.member.entities.Member;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@SpringBootTest
@ActiveProfiles({"default", "test"})
public class Ex03 {

    @PersistenceContext
    private EntityManager em;

    private Member member;

    @BeforeEach
    void init() {
        member = new Member();

        member.setSeq(1L);
        member.setEmail("user01@test.org");
        member.setName("사용자01");
        member.setPassword("12345678");
        member.setRegDt(LocalDateTime.now());

        // 영속 상태 - 변화 감지 상태
        em.persist(member);

        em.flush();

        // 영속성 비우기 (관리되는 Entity 모두 제거)
        em.clear();
    }

    /**
     * 영속성 Context - 1차 Cache
     *
     */
    @Test
    void test1() {

        // 기본 키로 조회하는 메서드
        Member member2 = em.find(Member.class, 1L);
        // DB 직접 SQL 실행 -> Entity -> 영속성 Context에 보관(1차 캐시)

        System.out.println(member2);
        // 첫번쨰 조회이므로 SELECT

        System.out.println(member2 == member);
        // false = 새로 만든 별개의 객체임

        Member member3 = em.find(Member.class, 1L);
        // 영속성 Context에서 바로 조회 (1차 cache 사용)

        System.out.println(member3);
        // 이미 1차 cache이므로 SELECT Query 생성 X

        System.out.println(member3 == member2);
        // true = member2떄와 같은 객체임 1차 cache로 사용, 싱글톤
    }
}