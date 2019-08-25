package com.katana.tenement.web.app.api.userLike;

import lombok.Data;

import java.util.List;

@Data
public class ResponseLikeUsersGet {

    private int likeInvitationId;

    private List<LikeUser> likeUsers;

    @Data
    public static class LikeUser{

        private int likeUserId;

        private String userName;

    }
}
