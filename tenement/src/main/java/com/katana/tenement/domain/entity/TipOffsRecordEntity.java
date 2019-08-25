package com.katana.tenement.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户信息 实体
 * <p>
 * Created by mumu on 2019/3/11.
 */
@Data
@Entity
@Table(name = "tip_offs_record")
public class TipOffsRecordEntity {

    //用户唯一id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //举报人id
    @Column
    private int userId;

    //对象id
    @Column
    private int targetId;

    //举报类型
    @Column
    private int tipOffsId;

    //帖子id
    @Column
    private int invitationId;

    //详细信息
    @Column
    private String remark;

}
