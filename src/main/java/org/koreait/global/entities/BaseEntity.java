package org.koreait.global.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Table 마다 공통인 부분
 * 추상 Class
 */
// 다른 Entity의 상위 Class임을 알려주는 Annotation, Table 순서는 변경 불가
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // Entity의 변화 감지를 확인
@Data
public abstract class BaseEntity {

    @Column(updatable = false)
    // @CreationTimestamp // hibernate 내에서만 적용되는 비표준 Annotation
    @CreatedDate
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime regDt;

    @Column(insertable = false)
    // @UpdateTimestamp // hibernate 내에서만 적용되는 비표준 Annotation
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime modDt;
}