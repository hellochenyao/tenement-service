package com.katana.tenement.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDynamicEntity {

    @Column
    @Id
    private String id;

    @Column
    private Integer userId;

    @Column
    private Integer dynamicId;

    @CreationTimestamp
    private LocalDateTime createTime;

    public LikeDynamicEntity(Integer userId, Integer dynamicId) {
        this.id = userId + "_" + dynamicId;
        this.userId = userId;
        this.dynamicId = dynamicId;
    }
}
