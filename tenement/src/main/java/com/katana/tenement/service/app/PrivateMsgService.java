package com.katana.tenement.service.app;

import com.katana.tenement.domain.entity.PrivateMsgEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgBo;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgFilterBo;

public interface PrivateMsgService {

    void saveMsg(PrivateMsgBo privateMsgBo);

    Page<PrivateMsgEntity> findHistoryMsg(PrivateMsgFilterBo privateMsgFilterBo);

    Page<PrivateMsgEntity> findUserReceiveMsg(PrivateMsgFilterBo privateMsgFilterBo);

}
