package com.katana.tenement.service.app.impl;

import com.katana.tenement.dao.app.UserCollectionDao;
import com.katana.tenement.dao.app.UserCollectionRepo;
import com.katana.tenement.dao.app.vo.userCollection.InvitationUserInfoVo;
import com.katana.tenement.dao.app.vo.userCollection.UserCollectionFilterVo;
import com.katana.tenement.domain.entity.UserCollectionEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.service.app.UserCollectionService;
import com.katana.tenement.service.app.bo.userCollection.UserCollectionBo;
import com.katana.tenement.service.app.bo.userCollection.UserCollectionFilterBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserCollectionServiceImpl implements UserCollectionService {

    @Autowired
    private UserCollectionRepo userCollectionRepo;

    @Autowired
    private UserCollectionDao userCollectionDao;

    @Override
    public void saveUserCollection(UserCollectionBo userCollectionBo) {
        UserCollectionEntity userCollectionEntity = new UserCollectionEntity();
        BeanUtils.copyProperties(userCollectionBo,userCollectionEntity);
        userCollectionEntity.setCollectTime(LocalDateTime.now());
        userCollectionRepo.save(userCollectionEntity);
    }

    @Override
    public UserCollectionEntity queryCollectStatus(int userId, int invitationId) {
        return userCollectionRepo.findByUserIdAndInvitationId(userId,invitationId);
    }

    @Override
    public Page<InvitationUserInfoVo> queryAllCollectionInvitations(UserCollectionFilterBo filterBo) {
        UserCollectionFilterVo filterVo = new UserCollectionFilterVo();
        BeanUtils.copyProperties(filterBo,filterVo);
        return userCollectionDao.findCollections(filterVo);
    }
}
