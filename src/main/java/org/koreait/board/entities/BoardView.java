package org.koreait.board.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

/**
 * 복합키
 * uid + seq
 */
@Data
@Entity
@IdClass(BoardViewId.class)
public class BoardView {

    // 회원 Unique ID
    @Id
    // uid가 Oracle 예약어라서
    @Column(name="_UID")
    private int uid;

    // 게시글 번호
    @Id
    private long seq;
}