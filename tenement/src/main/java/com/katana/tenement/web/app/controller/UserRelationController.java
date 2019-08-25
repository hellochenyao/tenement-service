package com.katana.tenement.web.app.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.service.app.UserRelationService;
import com.katana.tenement.service.app.bo.userRelation.UserRelationBo;
import com.katana.tenement.service.app.bo.userRelation.UserRelationFilterBo;
import com.katana.tenement.web.app.api.userRelation.RequestUserRelationFilterGet;
import com.katana.tenement.web.app.api.userRelation.RequestUserRelationPost;
import com.katana.tenement.web.app.api.userRelation.ResponseUserFriendGet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/app/user/relation")
@RestController
@Api(tags = "用户关系模块")
public class UserRelationController {
    @Autowired
    private UserRelationService userRelationService;

    @ApiOperation(value = "加好友")
    @RequestMapping(value = "/save/friends",method = RequestMethod.POST)
    public void saveFriends(@RequestBody RequestUserRelationPost request){
        UserRelationBo userBo = new UserRelationBo();
        BeanUtils.copyProperties(request,userBo);
        if(request.getType()==0||request.getType()==-1){
            userRelationService.createFriend(userBo);
        }else if(request.getType()==1){
            UserRelationBo friendBo = new UserRelationBo();
            friendBo.setUserid(request.getFriendId());
            friendBo.setFriendId(request.getUserid());
            friendBo.setType(request.getType());
            userRelationService.createFriend(userBo);
            userRelationService.createFriend(friendBo);
        }

    }

    @ApiOperation(value = "找好友")
    @RequestMapping(value = "/find/friends",method = RequestMethod.GET)

    public ResponseUserFriendGet find(RequestUserRelationFilterGet req){
        UserRelationFilterBo userRelationFilterBo = new UserRelationFilterBo();
        BeanUtils.copyProperties(req,userRelationFilterBo);
        Page page = userRelationService.findFriend(userRelationFilterBo);
        ResponseUserFriendGet response = new ResponseUserFriendGet();
        List<ResponseUserFriendGet.User> users = new ArrayList<>();
        page.getData().forEach(e->{
            ResponseUserFriendGet.User user = new ResponseUserFriendGet.User();
            BeanUtils.copyProperties(e,user);
            users.add(user);
        });
        response.setUsers(users);
        response.setTotal(users.size());
        return response;
    }
}
