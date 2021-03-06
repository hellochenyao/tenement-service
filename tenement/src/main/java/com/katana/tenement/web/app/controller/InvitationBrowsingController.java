package com.katana.tenement.web.app.controller;

import com.katana.tenement.dao.app.vo.tenementInvitation.InvitationUserInfoVo;
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
import com.katana.tenement.service.app.bo.tenementInvitation.InvitationUserInfoBo;
import com.katana.tenement.service.app.bo.tenementInvitation.TenementInvitationFilterBo;
import com.katana.tenement.service.app.bo.userBrowsing.UserBrowsingFilterBo;
import com.katana.tenement.web.app.api.invitation.*;
import com.katana.tenement.web.app.api.userBrowsing.RequestUserBrowsingFilterGet;
import com.katana.tenement.web.app.api.userinfo.ResponseUserInfoGet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;
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
                tenementInvitation.setLikeStatus(userLikeBo.getStatus());
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
    @RequestMapping(value = "/detail/{invitationId}", method = RequestMethod.PUT)
    public void detail(@PathVariable("invitationId") int invitationId,@PathVariable("userId") Integer userId) {
        InvitationBrowsingBo invitationBrowsingBo = new InvitationBrowsingBo();
        invitationBrowsingBo.setUserId(userId);
        invitationBrowsingBo.setInvitationId(invitationId);
        invitationBrowsingBo.setBrowsingTime(LocalDateTime.now());
        invitationBrowsingService.viewDetail(invitationBrowsingBo);
    }

    @ApiOperation("举报帖子")
    @RequestMapping(value = "/tip-offs/{invitationId}", method = RequestMethod.POST)
    public void  tipOffs(int invitationId) {

    }


    @ApiOperation("帖子下方留言")
    @RequestMapping(value = "/leave/words/{invitationId}", method = RequestMethod.POST)
    public ResponseWordPost  leaveWords(@RequestBody RequestLeaveWordsPost request, @PathVariable int invitationId,@PathVariable("userId") Integer userId) {
        UserMsgBo userMsgBo = new UserMsgBo();
        userMsgBo.setUserId(userId);
        userMsgBo.setInvitationId(invitationId);
        BeanUtils.copyProperties(request,userMsgBo);
        UserMsgEntity userMsg = invitationBrowsingService.leaveWord(userMsgBo);
        ResponseWordPost response = new ResponseWordPost();
        response.setMsgId(userMsg.getId());
        return response;
    }

    @ApiOperation("根据id按页数查找帖子的所有留言")
    @RequestMapping(value="/leave/words-items",method = RequestMethod.GET)
    public ResponseLeaveWordsGet findLeaveWord(RequestLeaveWordsFilterGet request){
        UserMsgFilterBo userMsgFilterBo = new UserMsgFilterBo();
        UserResponseMsgFilterBo userResponseMsgFilterBo = new UserResponseMsgFilterBo();
        BeanUtils.copyProperties(request,userMsgFilterBo);
        Page<UserMsgEntity> page = invitationBrowsingService.findLeaveWord(userMsgFilterBo);
        List<ResponseLeaveWordsGet.DetailWords> detailWordsList = new ArrayList<>();
        page.getData().forEach(e->{
            userResponseMsgFilterBo.setPid(e.getId());
            userResponseMsgFilterBo.setInvitationId(e.getInvitationId());
            userResponseMsgFilterBo.setPageNo(1);
            userResponseMsgFilterBo.setPageSize(5);
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
        response.setTotal(page.getTotal());
        return response;
    }

    @ApiOperation(value = "根据指定id获得帖子信息")
    @RequestMapping(value = "/find/invitation/{id}",method = RequestMethod.GET)
    public ResponseInvitationGet find(@PathVariable("id") int id){
      InvitationUserInfoBo invitationUserInfoBo = invitationBrowsingService.findByInvitation(id);
      ResponseInvitationGet response = new ResponseInvitationGet();
      BeanUtils.copyProperties(invitationUserInfoBo,response);
      return response;
    }

    @ApiOperation(value = "根据回复的id查找回复具体内容")
    @RequestMapping(value = "/find/main/response/msg/{id}/content",method = RequestMethod.GET)
    public ResponseUserMsgGet findMsg(@PathVariable("id") int msgId){
        ResponseUserMsgGet response = new ResponseUserMsgGet();
        UserMsgEntity userMsgEntity = invitationBrowsingService.getResponseMsgContent(msgId);
        UserInfoVo user = userInfoService.info(userMsgEntity.getUserId());
        response.setAvatar(user.getAvatar());
        response.setGender(user.getGender());
        BeanUtils.copyProperties(userMsgEntity,response);
        response.setCreateTime(DateUtils.getLocalDateTimeStr(userMsgEntity.getCreateTime()));
        return response;
    }
    @ApiOperation(value = "根据pid找某个帖子留言的所有回复内容")
    @RequestMapping(value = "/find/all/response/msg/{pid}",method = RequestMethod.GET)
    public ResponseLeaveWordsToUserGet findResponseToUser(RequestWordsToUserFilterGet req,@PathVariable("pid") int pid){
        UserResponseMsgFilterBo filterBo = new UserResponseMsgFilterBo();
        BeanUtils.copyProperties(req,filterBo);
        filterBo.setPid(pid);
        filterBo.setInvitationId(req.getInvitationId());
        Page<UserMsgEntity> responsePage = invitationBrowsingService.findResponseLeaveWords(filterBo);
        ResponseLeaveWordsToUserGet response = new ResponseLeaveWordsToUserGet();
        List<ResponseLeaveWordsToUserGet.Msgs> list = new ArrayList<>();
        responsePage.getData().forEach(resEntity->{
            ResponseLeaveWordsToUserGet.Msgs msgs = new ResponseLeaveWordsToUserGet.Msgs();
            msgs.setAnswerUserId(resEntity.getAnswerUserId());
            UserInfoVo user = userInfoService.info(resEntity.getUserId());
            msgs.setAnswerUserNickname(resEntity.getAnswerUserNickname());
            msgs.setNickname(resEntity.getNickname());
            msgs.setAvatar(user.getAvatar());
            msgs.setMsg(resEntity.getMsg());
            msgs.setCreateTime(DateUtils.getLocalDateTimeStr(resEntity.getCreateTime()));
            msgs.setGender(user.getGender());
            msgs.setUserId(resEntity.getUserId());
            list.add(msgs);
        });
        response.setData(list);
        response.setTotal(responsePage.getTotal());
        return response;
    }

    @ApiOperation(value = "根据帖子id查找所有留言")
    @RequestMapping(value = "/find/all/usermsg/{invitationid}",method = RequestMethod.GET)
    public ResponseAllLeaveWordsGet findAllMsg(@PathVariable("invitationid") int invitationid){
        List<UserMsgEntity> data = invitationBrowsingService.findAllUserMsgs(invitationid);
        ResponseAllLeaveWordsGet response = new ResponseAllLeaveWordsGet();
        List<ResponseAllLeaveWordsGet.Msg> list = new ArrayList<>();
        data.forEach(e->{
            ResponseAllLeaveWordsGet.Msg msg = new ResponseAllLeaveWordsGet.Msg();
            BeanUtils.copyProperties(e,msg);
            list.add(msg);
        });
        response.setData(list);
        return response;
    }

    @ApiOperation(value = "增加浏览记录")
    @RequestMapping(value = "/add/browsing/record/{invitationid}",method = RequestMethod.POST)
    public void addBrowsingRecord(@PathVariable int userId ,@PathVariable int invitationid){
        invitationBrowsingService.addBrowsingRecord(userId,invitationid);
    }

    @ApiOperation(value = "分页查询用户的浏览记录")
    @RequestMapping(value = "/query/browsing/records",method = RequestMethod.GET)
    public ResponseTenementInvitationGet queryUserBrowsingRecords(RequestUserBrowsingFilterGet request){
        UserBrowsingFilterBo filterBo = new UserBrowsingFilterBo();
        BeanUtils.copyProperties(request,filterBo);
        ResponseTenementInvitationGet response = new ResponseTenementInvitationGet();
        Page<InvitationUserInfoVo> page = invitationBrowsingService.findBrowsingInvitation(filterBo);
        List<ResponseTenementInvitationGet.TenementInvitation> list = new ArrayList<>();
        page.getData().forEach(e->{
            ResponseTenementInvitationGet.TenementInvitation tenementInvitation = new ResponseTenementInvitationGet.TenementInvitation();
            BeanUtils.copyProperties(e,tenementInvitation);
            tenementInvitation.setCreateTime(DateUtils.getLocalDateTimeStr(e.getCreateTime()));
            tenementInvitation.setUpdateTime(DateUtils.getLocalDateTimeStr(e.getUpdateTime()));
            tenementInvitation.setDesiredDate(DateUtils.getLocalDateTimeStr(e.getDesiredDate()));
            tenementInvitation.setLastLoginTime(DateUtils.getLocalDateTimeStr(e.getLastLoginTime()));
            list.add(tenementInvitation);
        });
        response.setData(list);
        response.setTotal(page.getTotal());
        return response;
    }

    @ApiOperation(value = "查找用户收到的留言")
    @RequestMapping(value = "/query/new/word",method =RequestMethod.GET )
    public ResponseUserMsgInfoGet getUserMsgInfo(RequestUserMsgInfoGet request){
        UserMsgResponseBo filterBo = new UserMsgResponseBo();
        BeanUtils.copyProperties(request,filterBo);
        Page<UserMsgEntity> page = invitationBrowsingService.findNewWord(filterBo);
        List<ResponseUserMsgInfoGet.UserMsgInfo> list = new ArrayList<>();
        page.getData().forEach(e->{
            ResponseUserMsgInfoGet.UserMsgInfo userMsgInfo = new ResponseUserMsgInfoGet.UserMsgInfo();
            BeanUtils.copyProperties(e,userMsgInfo);
            userMsgInfo.setAnswerUserId(e.getAnswerUserId());
            userMsgInfo.setAnswerUserNickName(e.getAnswerUserNickname());
            userMsgInfo.setContent(e.getMsg());
            userMsgInfo.setUserId(e.getUserId());
            UserInfoVo info = userInfoService.info(e.getUserId());
            userMsgInfo.setUserAvatr(info.getAvatar());
            userMsgInfo.setUserNickName(e.getNickname());
            userMsgInfo.setCreateTime(DateUtils.getLocalDateTimeStr(e.getCreateTime()));
            userMsgInfo.setGrade(info.getGender());
            if(e.getPid()==0){
                TenementInvitationEntity tenementInvitationEntity = tenementInvitationService.findInvitationById(e.getInvitationId());
                userMsgInfo.setResponseContent(tenementInvitationEntity.getContent());
            }else{
                userMsgInfo.setId(e.getPid());
                UserMsgEntity userMsgEntity = invitationBrowsingService.getResponseMsgContent(e.getPid());
                userMsgInfo.setResponseContent(userMsgEntity.getMsg());
            }
            list.add(userMsgInfo);
        });
        ResponseUserMsgInfoGet response = new ResponseUserMsgInfoGet();
        response.setData(list);
        response.setTotal(page.getTotal());
        return response;
    }
}
