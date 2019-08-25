package com.katana.tenement.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 *用户收藏实体
 */
@Entity
@Data
@Table(name = "user_collection")
public class UserCollectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int userId;

    @Column
    private int invitationId;

    @Column
    private LocalDateTime collectTime;
}
