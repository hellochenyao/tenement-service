package com.katana.tenement.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 *评论实体
 */
@Entity
@Data
@Table(name = "user_msg")
public class UserMsgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //发消息者id
    @Column
    private int userId;

    //发消息者昵称
    @Column
    private String nickname;

    //回复对象id
    @Column
    private int answerUserId;

    //回复对象名称
    @Column
    private String answerUserNickname;

    //回复消息id
    @Column
    private int pid;

    //帖子
    @Column
    private int invitationId;

    //留言时间
    @Column
    private LocalDateTime createTime;

    //留言
    @Column
    private String msg;
}
