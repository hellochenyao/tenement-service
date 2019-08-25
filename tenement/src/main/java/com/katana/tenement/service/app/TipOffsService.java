package com.katana.tenement.service.app;


import com.katana.tenement.domain.entity.TipOffsRecordEntity;
import com.katana.tenement.service.app.bo.JwtStorageBo;
import com.katana.tenement.service.app.bo.tipoffs.UserTipOffsBo;
import org.springframework.beans.BeanUtils;

/**
 * 举报相关 service
 * Created by mumu on 2019/3/27.
 */
public interface TipOffsService {

    void tipOffs(UserTipOffsBo userTipOffsBo);

}
