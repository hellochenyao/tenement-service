package com.katana.tenement.web.app.controller;

import com.katana.tenement.dao.app.vo.userCollection.InvitationUserInfoVo;
import com.katana.tenement.domain.entity.UserCollectionEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.framework.util.DateUtils;
import com.katana.tenement.service.app.UserCollectionService;
import com.katana.tenement.service.app.bo.userCollection.UserCollectionBo;
import com.katana.tenement.service.app.bo.userCollection.UserCollectionFilterBo;
import com.katana.tenement.web.app.api.userCollection.RequestCollectionFilterGet;
import com.katana.tenement.web.app.api.userCollection.ResponseCollectionStatusGet;
import com.katana.tenement.web.app.api.userCollection.ResponseTenementInvitationGet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

@RestController
@Api(tags = "App-用户收藏模块")
@RequestMapping(value = "/app/collect/invitation/{userId}/item")
public class UserCollectionController {

    @Autowired
    private UserCollectionService userCollectionService;

    @ApiOperation("收藏帖子")
    @RequestMapping(value = "/save/{invitationId}",method = RequestMethod.POST)
    public ResponseCollectionStatusGet collectInvitation(@PathVariable int userId,@PathVariable int invitationId){
        UserCollectionBo userCollectionBo = new UserCollectionBo();
        userCollectionBo.setUserId(userId);
        userCollectionBo.setInvitationId(invitationId);
        ResponseCollectionStatusGet response = new ResponseCollectionStatusGet();
        Boolean haveCollect = userCollectionService.saveUserCollection(userCollectionBo);
        response.setCollectStatus(haveCollect);
        return response;
    }

    @ApiOperation("查询该用户是否收藏帖子")
    @RequestMapping(value = "/query/collect/status",method = RequestMethod.GET)
    public ResponseCollectionStatusGet isCollectInvitation(int invitationId,@PathVariable int userId){
        UserCollectionEntity userCollect = userCollectionService.queryCollectStatus(userId,invitationId);
        ResponseCollectionStatusGet response = new ResponseCollectionStatusGet();
        if(userCollect!=null){
            response.setCollectStatus(true);
        }else{
            response.setCollectStatus(false);
        }
        return response;
    }

    @ApiOperation("分页查询用户收藏的所有帖子")
    @RequestMapping(value = "/find/all",method =RequestMethod.GET )
    public ResponseTenementInvitationGet findAllCollections(RequestCollectionFilterGet request){
        UserCollectionFilterBo filterBo = new UserCollectionFilterBo();
        BeanUtils.copyProperties(request,filterBo);
        Page<InvitationUserInfoVo> page = userCollectionService.queryAllCollectionInvitations(filterBo);
        ResponseTenementInvitationGet response = new ResponseTenementInvitationGet();
        List<ResponseTenementInvitationGet.TenementInvitation> list = new ArrayList<>();
        page.getData().forEach(e->{
            ResponseTenementInvitationGet.TenementInvitation invitation = new ResponseTenementInvitationGet.TenementInvitation();
            BeanUtils.copyProperties(e,invitation);
            invitation.setCreateTime(DateUtils.getLocalDateTimeStr(e.getCreateTime()));
            invitation.setUpdateTime(DateUtils.getLocalDateTimeStr(e.getUpdateTime()));
            invitation.setDesiredDate(DateUtils.getLocalDateTimeStr(e.getDesiredDate()));
            invitation.setLastLoginTime(DateUtils.getLocalDateTimeStr(e.getLastLoginTime()));
            list.add(invitation);
        });
        response.setData(list);
        response.setTotal(page.getTotal());
        return response;
    }
}
