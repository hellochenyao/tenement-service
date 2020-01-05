package com.katana.tenement.domain.entity;

import com.katana.tenement.domain.emuns.ConcernType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "concern")
@NoArgsConstructor
@AllArgsConstructor
public class ConcernEntity {
    @Id
    private String id;

    private Integer userid;

    private Integer toUserid;//关注用户的

    @Enumerated(value = EnumType.STRING)
    private ConcernType concernType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public ConcernEntity( Integer userid, Integer toUserid, ConcernType concernType, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = userid+"_"+toUserid;
        this.userid = userid;
        this.toUserid = toUserid;
        this.concernType = concernType;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
