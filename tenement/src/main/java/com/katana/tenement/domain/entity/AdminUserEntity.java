package com.katana.tenement.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 管理员用户信息 实体
 * <p>
 * Created by zuzu on 2019/04/30.
 */

@Data
@Entity
@Table(name = "admin_user")
public class AdminUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //用户名
    @Column
    private String username;

    //密码
    @Column
    private String password;

    //账号状态
    @Column
    private Integer status;
}
