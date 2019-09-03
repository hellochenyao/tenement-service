package com.katana.tenement.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "concern")
public class ConcernEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userid;

    private int toUserid;//关注用户的

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
