package com.katana.tenement.domain.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "dynamic ")
public class DynamicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Integer userId;

    @Column(name = "circle_id")
    private Integer circleId;

    @Column(name = "circle_name")
    private String circleName;

    @Column(name = "content",columnDefinition = "text")
    private String content;

    @Lob
    @Column(columnDefinition = "text")
    private String image;

    @Column
    @CreationTimestamp
    private LocalDateTime createTime;

}
