package org.koreait.member.repositories;

import org.koreait.member.entities.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * JpaRepository Interface
 *
 */
public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {

    // Member findByEmail(String email);

    // Null 가능성이 있으니 Optional 로 정의
    Optional<Member> findByEmail(String email);

    //List<Member> findByNameContainingOrderByRegDtDesc(String keyword, Pageable pageable);

    Page<Member> findByNameContainingOrderByRegDtDesc(String keyword, Pageable pageable);

    // From Member <- Member(Entity명) Table명과 헷갈리면 안됨
    // LIKE :key -> keyword로 대체

    /**
     * JPQL - Entity 기준의 SQL문, 절대 Table과 Table Column이 아님
     *
     */
    @Query("SELECT m FROM Member m WHERE m.name LIKE :key ORDER BY m.regDt DESC")
    List<Member> getMembers(@Param("key") String keyword);
}