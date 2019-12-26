package com.katana.tenement.web.app.api.dynamic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResponseLikeGet {

    private List<LikeDynamic> data;

    private Integer total;

    @Data
    public static class LikeDynamic{

        private String id;

        private Integer userId;

        private String userName;

        private String avatar;

        private Integer dynamicId;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime createTime;
    }

}
