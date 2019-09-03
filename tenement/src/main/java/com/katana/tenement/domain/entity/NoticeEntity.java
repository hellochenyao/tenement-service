package com.katana.tenement.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 系统通知公告 实体
 * <p>
 * Created by zuzu on 2019/04/30.
 */
@Entity
@Table(name = "admin_notice")
@Data
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int toUserid;//用户id发给默认 -1发给全部

    @Column(columnDefinition = "text")
    private String content;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
