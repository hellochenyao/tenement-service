package com.katana.tenement.service.app.impl;

import com.katana.tenement.dao.app.*;
import com.katana.tenement.domain.entity.*;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.service.app.InvitationBrowsingService;
import com.katana.tenement.service.app.bo.invitationBrowsing.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvitationBrowsingServiceImpl implements InvitationBrowsingService {

    @Autowired
    private UserBrowsingRecordRepo userBrowsingRecordRepo;

    @Autowired
    private UserCollectionRepo userCollectionRepo;

    @Autowired
    private TenementInvitationRepo tenementInvitationRepo;

    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private UserMsgRepo userMsgRepo;

    @Override
    public void viewDetail(InvitationBrowsingBo invitationBrowsingBo) {
        UserBrowsingRecordEntity userBrowsingRecordEntity = new UserBrowsingRecordEntity();
        BeanUtils.copyProperties(invitationBrowsingBo,userBrowsingRecordEntity);
        userBrowsingRecordRepo.save(userBrowsingRecordEntity);
    }

    @Override
    public void collectInvitation(UserCollectionBo userCollectionBo) {
        UserCollectionEntity userCollectionEntity = new UserCollectionEntity();
        BeanUtils.copyProperties(userCollectionBo,userCollectionEntity);
        userCollectionRepo.save(userCollectionEntity);
    }

    @Override
    public void leaveWord(UserMsgBo userMsgBo) {
        TenementInvitationEntity tenementInvitationEntity = tenementInvitationRepo.findById(userMsgBo.getInvitationId()).orElse(null);
        if(tenementInvitationEntity == null){
            throw new BusinessException("NO_INVITATION","该帖子不存在");
        }
        UserInfoEntity answerUserInfo = userInfoRepo.findById(userMsgBo.getResponseUserId());
        UserInfoEntity userInfo = userInfoRepo.findById(userMsgBo.getUserId());
        UserMsgEntity userMsgEntity = new UserMsgEntity();
        userMsgEntity.setAnswerUserId(answerUserInfo.getId());
        userMsgEntity.setCreateTime(LocalDateTime.now());
        userMsgEntity.setAnswerUserNickname(answerUserInfo.getNickName());
        userMsgEntity.setNickname(userInfo.getNickName());
        userMsgEntity.setPid(userMsgBo.getAnswerMsgId());
        BeanUtils.copyProperties(userMsgBo,userMsgEntity);
        userMsgRepo.save(userMsgEntity);
    }

    @Override
    public Page<UserMsgEntity> findLeaveWord(UserMsgFilterBo userMsgFilterBo) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        org.springframework.data.domain.Page<UserMsgEntity> pageData = userMsgRepo.findByInvitationId(userMsgFilterBo.getInvitationId(), PageRequest.of(userMsgFilterBo.getPageNo()-1,userMsgFilterBo.getPageSize(),sort));
        Page<UserMsgEntity> page = new Page<>();
        page.setTotal(pageData.getContent().size());
        page.setData(pageData.getContent());
        return page;
    }

    @Override
    public Page<UserMsgEntity> findResponseLeaveWords(UserResponseMsgFilterBo userResponseMsgFilterBo) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        org.springframework.data.domain.Page<UserMsgEntity> pageData = userMsgRepo.findByPid(userResponseMsgFilterBo.getPid(), PageRequest.of(userResponseMsgFilterBo.getPageNo()-1,userResponseMsgFilterBo.getPageSize(),sort));
        Page<UserMsgEntity> page = new Page<>();
        page.setTotal(pageData.getContent().size());
        page.setData(pageData.getContent());
        return page;
    }

    @Override
    public Integer findLeaveMsgNumsByInvitationId(int id) {
        List<UserMsgEntity> list = userMsgRepo.findByInvitationId(id);
        return list.size();
    }
}
