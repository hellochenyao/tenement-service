package com.katana.tenement.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 *用户浏览记录实体
 */
@Entity
@Data
@Table(name = "user_browsing_record")
public class UserBrowsingRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int userId;

    @Column
    private int invitationId;

    @Column
    private LocalDateTime browsingTime;
}
