package com.katana.tenement.service.app;

import com.katana.tenement.domain.entity.ConcernEntity;
import com.katana.tenement.domain.entity.UserInfoEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.service.app.bo.concern.ConcernFilterBo;

public interface ConcernService {

    void saveUserConcern(int userid,int toUserid);

    void deletedUserConcern(int userid,int toUserid);

    Integer queryConcernNums(int userid);

    Integer queryAdmiresNums(int toUserid);

    Page<UserInfoEntity> findConcernList(ConcernFilterBo concernFilterBo);

    Integer findIsConcern(int userid,int toUserid);
}
