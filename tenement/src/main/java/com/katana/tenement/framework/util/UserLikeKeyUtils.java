package com.katana.tenement.framework.util;
public class UserLikeKeyUtils {

    public static final String RD_MAP_USER_LIKE_KEY = "RD_MAP_USER_LIKE_KEY";
    public static final String RD_MAP_USER_LIKE_COUNT_KEY = "RD_MAP_USER_LIKE_COUNT_KEY";

    public static String getKey(int likeInvitationId,int likeUserId){
        StringBuilder sb = new StringBuilder();
        sb.append(likeInvitationId);
        sb.append(":");
        sb.append(likeUserId);
        return sb.toString();
    }

}
