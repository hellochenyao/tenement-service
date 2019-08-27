package com.katana.tenement.dao.app;

import com.katana.tenement.dao.app.vo.privateMsg.PrivateMsgFilterVo;
import com.katana.tenement.domain.entity.PrivateMsgEntity;
import com.katana.tenement.framework.dto.page.Page;


public interface PrivateMsgDao {
    Page<PrivateMsgEntity> findUserPrivateMsg(PrivateMsgFilterVo privateFilter);
}
