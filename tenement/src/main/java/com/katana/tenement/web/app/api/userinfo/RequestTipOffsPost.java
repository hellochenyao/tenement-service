package com.katana.tenement.web.app.api.userinfo;

import lombok.Data;


/**
 * Created by mumu on 2019/4/11.
 */
@Data
public class RequestTipOffsPost {


    //举报类型
    private int tipOffsId;

    //对象
    private int targetId;

    //帖子id
    private int invitationId;

    //详细信息
    private String remark;


}
