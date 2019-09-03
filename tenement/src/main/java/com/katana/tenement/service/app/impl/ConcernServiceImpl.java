package com.katana.tenement.service.app.impl;

import com.katana.tenement.dao.app.ConcernDao;
import com.katana.tenement.dao.app.ConcernRepo;
import com.katana.tenement.dao.app.vo.concern.ConcernFilterVo;
import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.domain.entity.ConcernEntity;
import com.katana.tenement.domain.entity.NoticeEntity;
import com.katana.tenement.domain.entity.UserInfoEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.framework.websocket.WebSocketServer;
import com.katana.tenement.service.app.ConcernService;
import com.katana.tenement.service.app.NoticeService;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.bo.concern.ConcernFilterBo;
import com.katana.tenement.service.app.bo.notice.NoticeBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConcernServiceImpl implements ConcernService {

    @Autowired
    private ConcernRepo concernRepo;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ConcernDao concernDao;


    @Override
    public void saveUserConcern(int userid, int toUserid) {
        ConcernEntity concernEntity = new ConcernEntity();
        concernEntity.setCreateTime(LocalDateTime.now());
        concernEntity.setUpdateTime(LocalDateTime.now());
        concernEntity.setUserid(userid);
        concernEntity.setToUserid(toUserid);
        concernRepo.save(concernEntity);
        NoticeBo noticeBo = new NoticeBo();
        UserInfoVo userInfo = userInfoService.info(userid);
        noticeBo.setContent(userInfo.getNickName()+"关注了你");
        noticeBo.setToUserid(toUserid);
        noticeService.createNotice(noticeBo);
        WebSocketServer.sendInfo(null,"adminNotice",toUserid,-1);
    }

    @Override
    public void deletedUserConcern(int userid, int toUserid) {
        ConcernEntity concernEntity = concernRepo.findByUseridAndToUserid(userid,toUserid);
        if(concernEntity!=null){
            concernRepo.delete(concernEntity);
            UserInfoVo userInfo = userInfoService.info(userid);
            NoticeBo noticeBo = new NoticeBo();
            noticeBo.setToUserid(toUserid);
            noticeBo.setContent(userInfo.getNickName()+"取关了你");
            noticeService.createNotice(noticeBo);
            WebSocketServer.sendInfo(null,"adminNotice",toUserid,-1);
        }else{
            throw new BusinessException("CANCEL_ERROR","取关失败，你还未关注过改用户!");
        }
    }

    @Override
    public Integer queryConcernNums(int userid) {
        return concernRepo.findConcernNums(userid);
    }

    @Override
    public Integer queryAdmiresNums(int toUserid) {
        return concernRepo.findAdmiresNums(toUserid);
    }

    @Override
    public Page<UserInfoEntity> findConcernList(ConcernFilterBo concernFilterBo) {
        ConcernFilterVo concernFilterVo = new ConcernFilterVo();
        BeanUtils.copyProperties(concernFilterBo,concernFilterVo);
        Page<UserInfoEntity> page = concernDao.findConcernUsers(concernFilterVo);
        return page;
    }
}
