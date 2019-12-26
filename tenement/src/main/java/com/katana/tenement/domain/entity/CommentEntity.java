package com.katana.tenement.domain.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //动态id
    @Column(name = "dynamic_id")
    private int dynamicId;

    @Lob
    @Column(columnDefinition = "text")
    private String content;

    //评论人的id,
    @Column(name = "critics_id")
    private int userId;


    //被评论的人的id
    @Column(name = "commented_id")
    private int commentedId;

    //评论的消息的id
    @Column(name = "messaged_id")
    private int messagedId;

    @Column(name = "create_time", updatable = false)
    @CreationTimestamp
    private LocalDateTime createTime;

    //拆分获取前一个评论的前部分内容
    public String[] getFirstComment() {
        return this.getContent().split("//", -1);
    }


    public static final String COMMENT_SPACER = "//@";

    //组装评论
    public String setNewContent(String comment , String userName) {
        String newComment = comment + this.COMMENT_SPACER + userName  + ":" +this.getFirstComment()[0];
        return newComment;
    }
}
