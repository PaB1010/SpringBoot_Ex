package org.koreait.jpaex;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.board.entities2.Board;
import org.koreait.board.entities2.BoardData;
import org.koreait.board.entities2.HashTag;
import org.koreait.board.entities2.QBoardData;
import org.koreait.board.repositories.BoardDataRepository;
import org.koreait.board.repositories.BoardRepository;
import org.koreait.board.repositories.HashTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles({"default", "test"})
@Transactional
public class Ex08 {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardDataRepository boardDataRepository;

    @Autowired
    private HashTagRepository hashTagRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private JPAQueryFactory factory;

    @BeforeEach
    void init() {

        List<HashTag> tags = new ArrayList<>();

        for (int i = 1; i <= 5 ; i++) {

            HashTag tag = new HashTag();

            tag.setTag("태그" + i);

            tags.add(tag);
        }

        hashTagRepository.saveAllAndFlush(tags);

        Board board = new Board();
        board.setBid("freetalk");
        board.setBname("자유게시판");
        boardRepository.saveAndFlush(board);

        List<BoardData> items = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {

            BoardData item = new BoardData();

            item.setBoard(board);

            item.setSubject("제목" + i);
            item.setContent("내용" + i);

            // 관계 주입 (외래키)
            item.setTags(tags);

            items.add(item);
        }

        boardDataRepository.saveAllAndFlush(items);

        em.clear();
    }

    @Test
    void test1() {

        BoardData item = boardDataRepository.findById(1L).orElse(null);

        // 2차 Query 수행 X
        Board board = item.getBoard();

        // 2차 Query 수행
        // 실제 Data를 사용하는 지금 수행
        System.out.println(board.getBname());

//        List<HashTag> tags = item.getTags();
//
//        tags.forEach(System.out::println);
        /*
        HashTag(Tag=태그1, items=null)
        HashTag(Tag=태그2, items=null)
        HashTag(Tag=태그3, items=null)
        HashTag(Tag=태그4, items=null)
        HashTag(Tag=태그5, items=null)
         */
    }

    /**
     * JPA 쓰는 곳 단골 면접 질문
     *
     * N + 1 문제 - 지연로딩에서 발생하는 비효율적 Query 해결 방법
     */
    @Test
    void test2() {

        // Query 1번 수행
        List<BoardData> items = boardDataRepository.findAll();

        // Query 5번 수행
        for (BoardData item : items) {

            Board board = item.getBoard();
            
            // 게시판명 조회
            System.out.println(board.getBname());
        }
    }

    // n + 1 문제 JPQL 문법으로 해결 (비권장)
    @Test
    void test3() {

        List<BoardData> items = boardDataRepository.getItems();

        for (BoardData item : items) {

            // 2차 Query 수행
            String bName = item.getBoard().getBname();

            System.out.println(bName);
        }
    }

    //  n + 1 문제 Querydsl - fetchJoin() 사용으로 해결 (권장)
    @Test
    void test4() {

        // 비표준 Lib
        QBoardData boardData = QBoardData.boardData;

        /*
        지연 로딩 (LAZY)
        List<BoardData> items = factory.selectFrom(boardData)
                .leftJoin(boardData.board)
                .fetch();
         */

        List<BoardData> items = factory.selectFrom(boardData)
                .leftJoin(boardData.board)
                .fetchJoin()    // boardData.board 처음부터 Join
                .leftJoin(boardData.member)
                .fetchJoin()    // boardData.member 처음부터 Join
                .fetch();
    }

    //  n + 1 문제 @EntityGraph Annotatio : Query Method 패턴에서 사용으로 해결
    @Test
    void test5() {

        boardDataRepository.findBySubjectContaining("");
    }

    // 영속성 전이 (Cascade), 고아 객체 삭제
    @Test
    void test6() {

        Board board = boardRepository.findById("freetalk").orElse(null);

        // boardRepository.delete(board);
        // boardRepository.flush();


        List<BoardData> items = board.getItems();

        items.remove(1);
        // boardRepository.flush();
    }
}