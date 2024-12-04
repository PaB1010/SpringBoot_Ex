package org.koreait.board.entities2;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.global.entities.BaseEntity;
import org.koreait.member.entities.Member;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 연관 관계 Mapping
 * @ManyToOne
 *
 * Many 담당
 */
@Data
@Entity
public class BoardData extends BaseEntity {

    // 게시글 번호
    // 자동 증감 번호
    @Id @GeneratedValue
    private Long seq;

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;

    // 외래키 자동 추가, BoardDate 조회시 같이 조회
    // foreign key (board_bid)
    // FetchType.LAZY = 지연 로딩
    @ManyToOne(fetch = FetchType.LAZY)
    // 외래키 이름 직접 지정
    @JoinColumn(name="bid")
    private Board board;

    // foreign key (member_seq)
    // FetchType.LAZY = 지연 로딩
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToMany
    private List<HashTag> tags;
}