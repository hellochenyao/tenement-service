package com.katana.tenement.domain.entity;

import com.katana.tenement.domain.emuns.UserLikeEnum;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户点赞 实体
 *
 */
@Data
@Table(name = "user_like")
@Entity
public class UserLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int likeUserId;

    @Column
    private int likeInvitationId;

    @Column
    private Integer status = UserLikeEnum.PLAIN.getCode();

    @Column
    private LocalDateTime createTime;

    @Column
    private LocalDateTime updateTime;
}
