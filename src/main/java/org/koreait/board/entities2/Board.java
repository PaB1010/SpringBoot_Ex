package org.koreait.board.entities2;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.koreait.global.entities.BaseEntity;

import java.util.List;

/**
 * 연관 관계 Mapping
 * @ManyToOne
 *
 * One 담당
 */
@Data
@Entity
public class Board extends BaseEntity {

    // 게시판 아이디
    @Id
    private String bid;

    // 게시판명
    @Column(length = 50, nullable = false)
    private String bname;

    @ToString.Exclude // ToString 메서드에서 배제
    // 외래키는 Many인 BoardData에 있으므로 관계의 주인(BoardData)의
    // 외래키  참조인 Column(board)을 명시하는것이 필수적
    @OneToMany(mappedBy = "board", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<BoardData> items;
}