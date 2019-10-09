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
    public Boolean saveUserCollection(UserCollectionBo userCollectionBo) {
        UserCollectionEntity userCollect = userCollectionRepo.findByUserIdAndInvitationId(userCollectionBo.getUserId(),userCollectionBo.getInvitationId());
        if(userCollect==null){
            userCollect = new UserCollectionEntity();
            BeanUtils.copyProperties(userCollectionBo,userCollect);
            userCollect.setCollectTime(LocalDateTime.now());
            userCollectionRepo.save(userCollect);
            return true;
        }else{
            userCollectionRepo.delete(userCollect);
            return false;
        }
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
