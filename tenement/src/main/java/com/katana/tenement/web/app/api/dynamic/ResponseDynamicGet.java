package com.katana.tenement.web.app.api.dynamic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResponseDynamicGet {

    private List<Dynamic> data;

    private int total;

    @Data
    public static class Dynamic{
        private int id;

        private Integer userId;

        private String userName;

        private String avatar;

        private Integer isLike;

        private List<Object> likes;

        private Integer circleId;

        private String circleName;

        private String content;

        private String image;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime createTime;
    }

}
