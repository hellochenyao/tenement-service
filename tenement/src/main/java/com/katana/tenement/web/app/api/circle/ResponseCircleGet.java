package com.katana.tenement.web.app.api.circle;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResponseCircleGet {

    private List<Circle> circles;

    private int total;

    @Data
    public static class Circle{
        private int id;

        private String circleName;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime createTime;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime updateTime;
    }

}
