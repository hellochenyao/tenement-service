package com.katana.tenement.service.app.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.katana.tenement.dao.app.UserRelationRepo;
import com.katana.tenement.domain.entity.UserInfoEntity;
import com.katana.tenement.domain.entity.UserRelationEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.framework.util.DateUtils;
import com.katana.tenement.service.app.UserRelationService;
import com.katana.tenement.service.app.bo.userRelation.UserRelationBo;
import com.katana.tenement.service.app.bo.userRelation.UserRelationFilterBo;
import com.katana.tenement.service.app.bo.userinfo.UserInfoBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRelationServiceImpl implements UserRelationService {

    @Autowired
    private UserRelationRepo userRelationRepo;

    @Override
    public void createFriend(UserRelationBo userRelationBo) {
        UserRelationEntity userRelationEntity = new UserRelationEntity();
        BeanUtils.copyProperties(userRelationBo,userRelationEntity);
        userRelationRepo.save(userRelationEntity);
    }

    @Override
    public Page<UserInfoBo> findFriend(UserRelationFilterBo userRelationFilterBo) {
        Sort sort =new Sort(Sort.Direction.DESC,"id");
        org.springframework.data.domain.Page<UserInfoEntity> userRelationLists = userRelationRepo.findByUseridAndType(userRelationFilterBo.getUserid(),userRelationFilterBo.getType(),PageRequest.of(userRelationFilterBo.getPageNo()-1,userRelationFilterBo.getPageSize(),sort));
        List<UserInfoBo> userRelationBos = new ArrayList<>();
        userRelationLists.forEach(e->{
           UserInfoBo userInfoBo = new UserInfoBo();
           BeanUtils.copyProperties(e,userInfoBo);
           userInfoBo.setLastLoginTime(DateUtils.getLocalDateTimeStr(e.getLastLoginTime()));
           userRelationBos.add(userInfoBo);
        });
        Page page = new Page();
        page.setData(userRelationBos);
        page.setTotal(userRelationBos.size());
        return page;
    }

    @Override
    public void deleteFriend(int userid, int friendId) {
        userRelationRepo.deleteByUserid(userid);
        userRelationRepo.deleteByUserid(friendId);
    }
}
