package com.katana.tenement.dao.app;

import com.katana.tenement.dao.app.vo.privateMsg.PrivateMsgUserReceiveFilterVo;
import com.katana.tenement.domain.entity.PrivateMsgEntity;
import com.katana.tenement.framework.dto.page.Page;


public interface PrivateMsgDao {
    Page<PrivateMsgEntity> findConnectMsg(PrivateMsgUserReceiveFilterVo privateFilter);

    void deleteMsg(int userid,int receiveUserid);
}
