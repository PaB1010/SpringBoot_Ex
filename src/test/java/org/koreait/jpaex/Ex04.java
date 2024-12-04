package org.koreait.jpaex;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.member.entities.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles({"default", "test"})
public class Ex04 {

    @PersistenceContext
    private EntityManager em;

    /*
    @BeforeEach
    void init() {

        // 수동 관리
        em.getTransaction().begin();
    }

    @AfterEach
    void destroy() {

        // 수동 관리
        em.getTransaction().commit();
    }
     */

    @Test
    void test1() {

        for (int i = 1; i < 10; i++) {

            Member member = new Member();

            // SEQ는 @GeneratedValue 해놨으므로 더이상 setSeq 하지 않음
            member.setEmail("user" + i + "@test.org");
            member.setPassword("12345678");
            member.setName("사용자" + i);

            em.persist(member);
        }

        em.flush();

        em.clear();

        Member member1 = em.find(Member.class, 1L);
        Member member2 = em.find(Member.class, 2L);

        System.out.println(member1);
        // Member(seq=1, email=user1@test.org, password=12345678, name=사용자1)

        System.out.printf("member1.regDt : %s, member1.modDt : %s%n", member1.getRegDt(), member1.getModDt());
        // member1.regDt : 2024-11-27T15:03:26.795957, member1.modDt : null

        System.out.println(member2);
        // Member(seq=2, email=user2@test.org, password=12345678, name=사용자2)

        System.out.printf("member2.regDt : %s, member2.modDt : %s%n", member2.getRegDt(), member2.getModDt());
        //member2.regDt : 2024-11-27T15:03:26.808950, member2.modDt : null

        member1.setName("(수정)사용자1");

        em.flush();

        em.clear();

        member1 = em.find(Member.class, 1L);

        System.out.printf("member1.regDt : %s, member1.modDt : %s%n", member1.getRegDt(), member1.getModDt());
        // member1.regDt : 2024-11-27T15:03:26.795957, member1.modDt : 2024-11-27T15:03:26.861980
    }
}