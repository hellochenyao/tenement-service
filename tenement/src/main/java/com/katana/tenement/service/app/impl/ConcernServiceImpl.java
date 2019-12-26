package com.katana.tenement.service.app.impl;

import com.katana.tenement.dao.app.ConcernDao;
import com.katana.tenement.dao.app.ConcernRepo;
import com.katana.tenement.dao.app.vo.concern.ConcernFilterVo;
import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.domain.emuns.ConcernType;
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
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    public void saveUserConcern(int userid, int toUserid,ConcernType concernType) {
        ConcernEntity concern = concernRepo.findByUseridAndToUseridAndConcernType(userid,toUserid,concernType);
        if (concern != null) {
            throw new BusinessException("HAVE_CONCERN","您已关注过改用户，请勿重复关注！");
        }
        ConcernEntity concernEntity = new ConcernEntity();
        concernEntity.setCreateTime(LocalDateTime.now());
        concernEntity.setUpdateTime(LocalDateTime.now());
        concernEntity.setUserid(userid);
        concernEntity.setToUserid(toUserid);
        concernEntity.setConcernType(concernType);
        concernRepo.save(concernEntity);
        NoticeBo noticeBo = new NoticeBo();
        UserInfoVo userInfo = userInfoService.info(userid);
        noticeBo.setContent(userInfo.getNickName()+"关注了你");
        noticeBo.setToUserid(toUserid);
        noticeService.createNotice(noticeBo);
        WebSocketServer.sendInfo(null,"adminNotice",toUserid,-1);
    }

    @Override
    public void deletedUserConcern(int userid, int toUserid,ConcernType concernType) {
        ConcernEntity concernEntity = concernRepo.findByUseridAndToUseridAndConcernType(userid,toUserid,concernType);
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
    public Integer queryConcernNums(int userid,ConcernType concernType) {
        return concernRepo.findConcernNums(userid,concernType);
    }

    @Override
    public Integer queryAdmiresNums(int toUserid,ConcernType concernType) {
        return concernRepo.findAdmiresNums(toUserid,concernType);
    }

    @Override
    public Page<UserInfoEntity> findConcernList(ConcernFilterBo concernFilterBo) {
        ConcernFilterVo concernFilterVo = new ConcernFilterVo();
        BeanUtils.copyProperties(concernFilterBo,concernFilterVo);
        Page<UserInfoEntity> page = concernDao.findConcernUsers(concernFilterVo);
        return page;
    }

    @Override
    public Integer findIsConcern(int userid, int toUserid,ConcernType concernType) {
        ConcernEntity concernEntity = concernRepo.findByUseridAndToUseridAndConcernType(userid,toUserid,concernType);
        if(concernEntity!=null){
            return 1;
        }
        return 0;
    }

    @Override
    public List<ConcernEntity> findConcerns(int toUserid,ConcernType concernType) {
        return concernRepo.findByToUseridAndConcernType(toUserid,concernType);
    }
}
