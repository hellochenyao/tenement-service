package com.katana.tenement.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_relation")
@Data
public class UserRelationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userid;

    private int friendId;

    private int type;//0待验证 1已验证 -1已拉黑
}
