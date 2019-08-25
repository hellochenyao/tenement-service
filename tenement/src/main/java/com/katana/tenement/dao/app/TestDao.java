package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.TenementInvitationEntity;

import java.util.List;

public interface TestDao {
    List<TenementInvitationEntity> test( Integer id ,String city)throws InterruptedException;
}
