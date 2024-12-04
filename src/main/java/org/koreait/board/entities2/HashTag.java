package org.koreait.board.entities2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 연관 관계 Mapping
 * @ManyToMany
 */
@Data
@Entity
public class HashTag {

    @Id
    private String Tag;

    @ToString.Exclude
    // 관계의 주인은 BoardData - tags
    @ManyToMany(mappedBy = "tags")
    private List<BoardData> items;
}