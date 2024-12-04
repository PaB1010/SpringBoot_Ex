package org.koreait.member.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.koreait.global.entities.BaseEntity;
import org.koreait.member.constants.Authority;

import java.time.LocalDateTime;

@Data
@Entity // @Entity = 영속성 관리 가능 형태, 반드시 기본키 명시 필수! (독립적인 존재 구분)
// @Table(name="KT_MEMBER")
public class Member extends BaseEntity {

    @Id // 기본키
    @GeneratedValue // 주로 기본키에 추가, 숫자가 알아서 증감
    private Long seq;

    @Column(length=65, nullable = false, unique = true)
    private String email;

    @JsonIgnore // JSON 문자열 변환 배제 (민감 데이터)
    @Column(length=65, nullable = false)
    private String password;

    @Column(length=40, nullable = false)
    private String name;

    /**
     * Large OBject - 여러줄
     * Oracle 기준 - CLOB, mySQL 기준 - tinytext
     */
    // @Lob
    @Transient // DB Column 생성 X, Entity 내부적으로만 사용되는 변수
    private String introduction;

    // Enum 상수
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Authority authority;

    /*
    private int num;
    // Not Null

    private Integer num2;
    // Not Null X

    @Temporal(TemporalType.DATE)
    private Date birthDt;
     */

    @OneToOne // 외래키 이름 : address_seq, 이름 변경 원하면 @JoinColumn 으로 변경
    private Address address;
}