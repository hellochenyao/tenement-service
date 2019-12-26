package com.katana.tenement.service.app.bo.tipoffs;

import com.katana.tenement.domain.emuns.TipOffsType;
import lombok.Data;


/**
 * Created by mumu on 2019/4/11.
 */
@Data
public class UserTipOffsBo {

    //举报类型
    private TipOffsType tipOffsType;

    //对象
    private int targetId;

    //帖子id
    private int invitationId;

    //详细信息
    private String remark;

    private int star;

    private String image;

}
