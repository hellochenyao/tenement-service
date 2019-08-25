package com.katana.tenement.service.app.bo.tipoffs;

import lombok.Data;


/**
 * Created by mumu on 2019/4/11.
 */
@Data
public class UserTipOffsBo {


    //举报类型 1 用户 2 帖子
    private int type;

    //举报类型
    private int tipOffsId;

    //对象
    private int targetId;

    //帖子id
    private int invitationId;

    //详细信息
    private String remark;

}
