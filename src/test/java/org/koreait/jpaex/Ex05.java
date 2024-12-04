package org.koreait.jpaex;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Member;
import org.koreait.member.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

/**
 * 회원 10명 추가
 */
@SpringBootTest
@ActiveProfiles({"default", "test"})
public class Ex05 {

    @Autowired
    private MemberRepository repository;

    @BeforeEach
    void init() {

        List<Member> members = new ArrayList<>();

        for (int i = 1; i <= 10 ; i++) {

            Member member = new Member();

            member.setEmail("user" + i + "test.org");
            member.setPassword("1234");
            member.setName("사용자" + i);
            member.setAuthority(Authority.USER);
            members.add(member);

            // persist()와 동일한 역할, 영속성 안에 넣어줌
            // repository.save(member);
            // 많은 데이터니까 saveAll 사용 권장, 주석
        }
        /*
         repository.saveAll(members);
         DB에 영구 반영
         repository.flush();
         */
        repository.saveAllAndFlush(members);
    }

    @Test
    void test1() {
        List<Member> members = repository.findAll();

        members.forEach(System.out::println);
    }

    @Test
    void test2() {

        // Null 일 경우 Throw
        Member member = repository.findByEmail("user200@test.org").orElseThrow(RuntimeException::new);
        System.out.println(member);
    }
}