package com.katana.tenement.web.app.controller;

import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.domain.entity.UserInfoEntity;
import com.katana.tenement.domain.entity.UserLikeEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.UserLikeService;
import com.katana.tenement.service.app.bo.UserLike.UserLikeBo;
import com.katana.tenement.service.app.bo.UserLike.UserLikeFilterBo;
import com.katana.tenement.web.app.api.userLike.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "用户点赞帖子模块")
@RequestMapping("/app/tenement/{userId}/like")
public class UserLikeController {

    @Autowired
    UserLikeService userLikeService;

    @Autowired
    UserInfoService userInfoService;

    @ApiOperation("点赞或踩帖子")
    @RequestMapping(value = "/give/invitation",method = RequestMethod.POST)
    public void saveUserLikeStatus(@RequestBody RequestLikeStatusPost request){
        UserLikeBo userLikeBo = new UserLikeBo();
        BeanUtils.copyProperties(request,userLikeBo);
        userLikeService.updateInvitationSupportNums(userLikeBo.getLikeInvitationId(),userLikeBo.getLikeUserId(),userLikeBo.getStatus());
    }

    @ApiOperation("帖子ID查找所有点赞或踩的用户")
    @RequestMapping(value = "/find/opt/user",method = RequestMethod.GET)
    public ResponseLikeUsersGet findLikeUsers(RequestLikeUsersFilterGet request){
        UserLikeFilterBo userLikeFilterBo = new UserLikeFilterBo();
        ResponseLikeUsersGet response = new ResponseLikeUsersGet();
        BeanUtils.copyProperties(request,userLikeFilterBo);
        Page<UserLikeEntity> page = userLikeService.getUserLikeListByInvitationId(userLikeFilterBo);
        List<ResponseLikeUsersGet.LikeUser> list = new ArrayList<>();
        try{
            page.getData().forEach(e->{
                ResponseLikeUsersGet.LikeUser likeUser = new ResponseLikeUsersGet.LikeUser();
                likeUser.setLikeUserId(e.getLikeUserId());
                UserInfoVo userInfoVo = userInfoService.info(e.getLikeUserId());
                likeUser.setUserName(userInfoVo.getNickName());
                list.add(likeUser);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        response.setLikeUsers(list);
        response.setLikeInvitationId(request.getLikeInvitationId());
        return response;
    }

    @ApiOperation("根据用户id查找所有的点赞或踩操作帖子")
    @RequestMapping(value = "/find/opt/invitation",method = RequestMethod.GET)
    public ResponseLikeInvitationsGet findLikeInvitations(RequestLikeInvitationsFilterGet request){
        UserLikeFilterBo userLikeFilterBo = new UserLikeFilterBo();
        ResponseLikeInvitationsGet response = new ResponseLikeInvitationsGet();
        BeanUtils.copyProperties(request,userLikeFilterBo);
        Page<UserLikeEntity> page = userLikeService.getInvitationLikeListByUserId(userLikeFilterBo);
        List<ResponseLikeInvitationsGet.LikeInvitation> list = new ArrayList<>();
        page.getData().forEach(e->{
            ResponseLikeInvitationsGet.LikeInvitation likeInvitation = new ResponseLikeInvitationsGet.LikeInvitation();
            likeInvitation.setInvitationId(e.getLikeInvitationId());
            list.add(likeInvitation);
        });
        response.setLikeInvitations(list);
        response.setLikeUserId(request.getLikeUserId());
        return response;
    }

    @ApiOperation("根据用户id和帖子id查找")
    @RequestMapping(value = "/find/userLike",method = RequestMethod.GET)
    public ResponseUserLikeGet findUserLike(int invitationId, int userId){
        ResponseUserLikeGet response = new ResponseUserLikeGet();
        UserLikeBo userLikeBo = userLikeService.findUserLikeByInvitationIdAndUserId( invitationId,  userId);
        BeanUtils.copyProperties(userLikeBo,response);
        return response;
    }

    @ApiOperation("查找所有点赞我的帖子用户")
    @RequestMapping(value = "/find/like/invitation/users",method = RequestMethod.GET)
    public ResponseGiveLikeUsersGet findGiveLikeUsers()
}
