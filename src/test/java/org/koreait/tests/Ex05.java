package org.koreait.tests;

import com.github.javafaker.Faker;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Member;
import org.koreait.member.entities.QMember;
import org.koreait.member.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootTest
@ActiveProfiles({"default", "test"})
public class Ex05 {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private JPAQueryFactory factory;

    @BeforeEach
    void init() {
        Faker faker = new Faker(Locale.KOREA);
        List<Member> members = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Member member = new Member();
            member.setEmail(faker.internet().emailAddress());
            member.setPassword(faker.regexify("[a-zA-Z0-9]{8,16}"));
            member.setAuthority(Authority.USER);
            member.setName(faker.name().fullName());
            members.add(member);
        }

        repository.saveAllAndFlush(members);
    }

    @Test
    void test1() {

        QMember member = QMember.member;

        BooleanBuilder andBuilder = new BooleanBuilder();

        andBuilder.and(member.name.contains("민"))
                .and(member.email.contains("@"))
                .and(member.regDt.after(LocalDateTime.now().minusDays(1L)));

        JPAQuery<Member> query = factory.selectFrom(member)
                // .where(member.name.contains("민"))
                .where(andBuilder)
                .offset(3) // 시작 위치 0부터 시작, 3 = 2번째 페이지
                .limit(3) // 한 페이지당 조회 개수
                .orderBy(member.regDt.desc(), member.email.asc()); // 1차정렬, 2차정렬
        /*
        where
            m1_0.name like ? escape '!'
            and m1_0.email like ? escape '!'
            and m1_0.reg_dt>?
        order by
         */

        // fetch() = 전체
        List<Member> members = query.fetch();

        members.forEach(System.out::println);
    }

    @Test
    void test2() {

        QMember member = QMember.member;

        BooleanBuilder andBuilder = new BooleanBuilder();

        andBuilder.and(member.name.contains("민"))
                .and(member.email.contains("@"))
                .and(member.regDt.after(LocalDateTime.now().minusDays(1L)));

        // QuerydslPredicateExecuter Interface 로
        // 매개변수에 조건식 추가 가능해짐
        // List<Member> members = (List<Member>)repository.findAll(member.name.contains("민"));
        List<Member> members = (List<Member>)repository.findAll(andBuilder);

        members.forEach(System.out::println);
    }
}