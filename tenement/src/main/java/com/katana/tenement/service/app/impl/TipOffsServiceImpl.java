package com.katana.tenement.service.app.impl;

import com.katana.tenement.dao.app.TipOffsRecordRepo;
import com.katana.tenement.domain.entity.TipOffsRecordEntity;
import com.katana.tenement.service.app.TipOffsService;
import com.katana.tenement.service.app.bo.JwtStorageBo;
import com.katana.tenement.service.app.bo.tipoffs.UserTipOffsBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mumu on 2019/3/27.
 */
@Service
public class TipOffsServiceImpl implements TipOffsService {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TipOffsRecordRepo tipOffsRecordRepo;

    /**
     * 举报 用户/帖子
     *
     * @param userTipOffsBo
     */
    @Override
    public void tipOffs(UserTipOffsBo userTipOffsBo) {

        JwtStorageBo jwtStorageBo = (JwtStorageBo) request.getAttribute("userInfo");

        TipOffsRecordEntity tipOffsRecordEntity = new TipOffsRecordEntity();

        BeanUtils.copyProperties(userTipOffsBo,tipOffsRecordEntity);
        tipOffsRecordEntity.setUserId(jwtStorageBo.getUserId());
        tipOffsRecordRepo.save(tipOffsRecordEntity);

    }

}
