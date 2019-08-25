package com.katana.tenement.web.app.controller;

import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.domain.entity.TenementInvitationEntity;
import com.katana.tenement.domain.entity.UserLikeEntity;
import com.katana.tenement.domain.entity.UserMsgEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.framework.util.DateUtils;
import com.katana.tenement.service.app.InvitationBrowsingService;
import com.katana.tenement.service.app.TenementInvitationService;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.UserLikeService;
import com.katana.tenement.service.app.bo.UserLike.UserLikeBo;
import com.katana.tenement.service.app.bo.invitationBrowsing.*;
import com.katana.tenement.service.app.bo.tenementInvitation.TenementInvitationFilterBo;
import com.katana.tenement.web.app.api.invitation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zuzu on 2019/5/9.
 */

@RestController
@Api(tags = "App-租房帖子浏览模块")
@RequestMapping("/app/tenement/{userId}")
public class InvitationBrowsingController {

    @Autowired
    private TenementInvitationService tenementInvitationService;

    @Autowired
    private InvitationBrowsingService invitationBrowsingService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserLikeService userLikeService;


    @ApiOperation("根据类型查找帖子")
    @RequestMapping(value = "/invitations", method = RequestMethod.GET)
    public ResponseTenementInvitationGet queryPublishedInvitation(RequestTenementInvitationGet request, @PathVariable("userId") Integer userId) {
        TenementInvitationFilterBo tenementInvitationFilterBo = new TenementInvitationFilterBo();
        BeanUtils.copyProperties(request, tenementInvitationFilterBo);
        Page<TenementInvitationEntity> page = tenementInvitationService.findInvitations(tenementInvitationFilterBo);
        ResponseTenementInvitationGet response = new ResponseTenementInvitationGet();
        List<ResponseTenementInvitationGet.TenementInvitation> list = new ArrayList<>();
        page.getData().forEach(e -> {
            ResponseTenementInvitationGet.TenementInvitation tenementInvitation = new ResponseTenementInvitationGet.TenementInvitation();
            Integer msgNums = invitationBrowsingService.findLeaveMsgNumsByInvitationId(e.getId());
            UserLikeBo userLikeBo = userLikeService.findUserLikeByInvitationIdAndUserId(e.getId(),userId);
            BeanUtils.copyProperties(e, tenementInvitation);
            if(userLikeBo!=null){
                tenementInvitation.setStatus(userLikeBo.getStatus());
            }
            tenementInvitation.setCreateTime(DateUtils.getLocalDateTimeStr(e.getCreateTime()));
            tenementInvitation.setUpdateTime(DateUtils.getLocalDateTimeStr(e.getUpdateTime()));
            tenementInvitation.setDesiredDate(DateUtils.getLocalDateStr(e.getDesiredDate()));
            UserInfoVo userInfoVo = userInfoService.info(e.getUserId());
            tenementInvitation.setLastLoginTime(userInfoVo.getLastLoginTime());
            tenementInvitation.setPublisher(e.getPublisher());
            tenementInvitation.setAvatar(userInfoVo.getAvatar());
            tenementInvitation.setGender(userInfoVo.getGender());
            tenementInvitation.setLeaveMsgNums(msgNums);
            list.add(tenementInvitation);
        });
        response.setData(list);
        response.setTotal(page.getTotal());
        return response;
    }



    @ApiOperation("查看帖子详情 更新帖子浏览次数")
    @RequestMapping(value = "/detail/{invitationId}", method = RequestMethod.POST)
    public void detail(int invitationId,@PathVariable("userId") Integer userId) {
        InvitationBrowsingBo invitationBrowsingBo = new InvitationBrowsingBo();
        invitationBrowsingBo.setUserId(userId);
        invitationBrowsingBo.setInvitationId(invitationId);
        invitationBrowsingBo.setBrowsingTime(LocalDateTime.now());
        invitationBrowsingService.viewDetail(invitationBrowsingBo);
    }

    @ApiOperation("收藏帖子")
    @RequestMapping(value = "/collect/{invitationId}", method = RequestMethod.POST)
    public void collect(int invitationId,@PathVariable("userId") Integer userId) {
        UserCollectionBo userCollectionBo = new UserCollectionBo();
        userCollectionBo.setUserId(userId);
        userCollectionBo.setInvitationId(invitationId);
        userCollectionBo.setCollectTime(LocalDateTime.now());
        invitationBrowsingService.collectInvitation(userCollectionBo);
    }


    @ApiOperation("举报帖子")
    @RequestMapping(value = "/tip-offs/{invitationId}", method = RequestMethod.POST)
    public void  tipOffs(int invitationId) {

    }


    @ApiOperation("帖子下方留言")
    @RequestMapping(value = "/leave/words/{invitationId}", method = RequestMethod.POST)
    public void  leaveWords(@RequestBody RequestLeaveWordsPost request, @PathVariable int invitationId,@PathVariable("userId") Integer userId) {
        UserMsgBo userMsgBo = new UserMsgBo();
        userMsgBo.setUserId(userId);
        userMsgBo.setInvitationId(invitationId);
        BeanUtils.copyProperties(request,userMsgBo);
        invitationBrowsingService.leaveWord(userMsgBo);
    }

    @ApiOperation("根据id查找帖子的所有留言")
    @RequestMapping(value="/leave/words-items",method = RequestMethod.GET)
    public ResponseLeaveWordsGet findLeaveWord(RequestLeaveWordsFilterGet request){
        UserMsgFilterBo userMsgFilterBo = new UserMsgFilterBo();
        UserResponseMsgFilterBo userResponseMsgFilterBo = new UserResponseMsgFilterBo();
        BeanUtils.copyProperties(request,userMsgFilterBo);
        BeanUtils.copyProperties(request,userResponseMsgFilterBo);
        Page<UserMsgEntity> page = invitationBrowsingService.findLeaveWord(userMsgFilterBo);
        List<ResponseLeaveWordsGet.DetailWords> detailWordsList = new ArrayList<>();
        page.getData().forEach(e->{
            userResponseMsgFilterBo.setPid(e.getId());
            UserInfoVo userInfoVo = userInfoService.info(e.getUserId());
            Page<UserMsgEntity> responsePage = invitationBrowsingService.findResponseLeaveWords(userResponseMsgFilterBo);
            List<ResponseLeaveWordsGet.DetailWords.ResDetailWords> resDetailList = new ArrayList<>();
            responsePage.getData().forEach(resEntity->{
                ResponseLeaveWordsGet.DetailWords.ResDetailWords responseDetailWords = new ResponseLeaveWordsGet.DetailWords.ResDetailWords();
                responseDetailWords.setAnswerUserId(resEntity.getAnswerUserId());
                UserInfoVo userRe = userInfoService.info(resEntity.getUserId());
                responseDetailWords.setMsg(resEntity.getMsg());
                responseDetailWords.setUserId(resEntity.getUserId());
                responseDetailWords.setAvatar(userRe.getAvatar());
                responseDetailWords.setLastLoginTime(userRe.getLastLoginTime());
                responseDetailWords.setGender(userRe.getGender());
                responseDetailWords.setNickname(resEntity.getNickname());
                responseDetailWords.setAnswerUserNickname(resEntity.getAnswerUserNickname());
                responseDetailWords.setCreateTime(DateUtils.getLocalDateTimeStr(resEntity.getCreateTime()));
                resDetailList.add(responseDetailWords);
            });
            ResponseLeaveWordsGet.DetailWords detailWords = new ResponseLeaveWordsGet.DetailWords();
            detailWords.setUserId(e.getUserId());
            detailWords.setCreateTime(DateUtils.getLocalDateTimeStr(e.getCreateTime()));
            detailWords.setId(e.getId());
            detailWords.setLastLoginTime(userInfoVo.getLastLoginTime());
            detailWords.setGender(userInfoVo.getGender());
            detailWords.setMsg(e.getMsg());
            detailWords.setNickname(e.getNickname());
            detailWords.setResTotal(responsePage.getTotal());
            detailWords.setResDetail(resDetailList);
            detailWords.setAvatar(userInfoVo.getAvatar());
            if(e.getPid()==0){
                detailWordsList.add(detailWords);
            }
        });
        ResponseLeaveWordsGet response = new ResponseLeaveWordsGet();
        response.setDetails(detailWordsList);
        response.setTotal(detailWordsList.size());
        return response;
    }

}
