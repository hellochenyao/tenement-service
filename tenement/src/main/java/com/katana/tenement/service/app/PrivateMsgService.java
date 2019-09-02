package com.katana.tenement.service.app;

import com.katana.tenement.domain.entity.PrivateMsgEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgBo;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgFilterBo;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgReceiveUserFilterBo;

public interface PrivateMsgService {

    void saveMsg(PrivateMsgBo privateMsgBo);

    Page<PrivateMsgEntity> findHistoryMsg(PrivateMsgFilterBo privateMsgFilterBo);

    Page<PrivateMsgEntity> findUserReceiveMsg(PrivateMsgReceiveUserFilterBo privateMsgFilterBo);

    Integer findNoReadNums(int userid,int receiveUserid,int readType);

    void deleteAllMsg(int userid,int receiveUserid);
}
