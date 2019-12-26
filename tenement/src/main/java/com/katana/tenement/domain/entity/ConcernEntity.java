package com.katana.tenement.domain.entity;

import com.katana.tenement.domain.emuns.ConcernType;
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

    private Integer userid;

    private Integer toUserid;//关注用户的

    @Enumerated(value = EnumType.STRING)
    private ConcernType concernType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
