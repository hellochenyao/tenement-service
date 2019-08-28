package com.katana.tenement.web.app.controller;

import com.katana.tenement.dao.app.vo.privateMsg.PrivateMsgUserReceiveFilterVo;
import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.domain.emuns.MessageTypeEnum;
import com.katana.tenement.domain.emuns.RentTypeEnum;
import com.katana.tenement.domain.entity.PrivateMsgEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.framework.util.DateUtils;
import com.katana.tenement.service.app.PrivateMsgService;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgBo;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgFilterBo;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgReceiveUserFilterBo;
import com.katana.tenement.web.app.api.privateMsg.RequestPrivateMsgFilterGet;
import com.katana.tenement.web.app.api.privateMsg.RequestPrivateMsgReceiveFilterGet;
import com.katana.tenement.web.app.api.privateMsg.ResponsePrivateMsgGet;
import com.katana.tenement.web.app.api.privateMsg.ResponseReceiveMsgGet;
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

@RestController
@RequestMapping("/app/tenement/{userId}")
@Api(tags = "APP-私信模块")
public class PrivateMsgController {

    @Autowired
    private PrivateMsgService privateMsgService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/find/msg",method = RequestMethod.GET)
    @ApiOperation("查找历史消息")
    public ResponsePrivateMsgGet findMsg(@PathVariable("userId") Integer userId, RequestPrivateMsgFilterGet requestPrivateMsgFilterGet){
        PrivateMsgFilterBo privateMsgFilterBo = new PrivateMsgFilterBo();
        BeanUtils.copyProperties(requestPrivateMsgFilterGet,privateMsgFilterBo);
        privateMsgFilterBo.setUserid(userId);
        Page<PrivateMsgEntity> page = privateMsgService.findHistoryMsg(privateMsgFilterBo);
        ResponsePrivateMsgGet responsePrivateMsgGet = new ResponsePrivateMsgGet();
        List<ResponsePrivateMsgGet.Message> messages = new ArrayList<>();
        page.getData().forEach(e->{
            ResponsePrivateMsgGet.Message message = new ResponsePrivateMsgGet.Message();
            BeanUtils.copyProperties(e,message);
            UserInfoVo userInfo = userInfoService.info(e.getUserid());
            UserInfoVo toUserInfo = userInfoService.info(e.getReceiveUserid());
            message.setReceiveUserNickName(toUserInfo.getNickName());
            message.setType(MessageTypeEnum.getEnumType(e.getType()).getValue());
            message.setReceiveUserAvatar(toUserInfo.getAvatar());
            message.setUserAvatar(userInfo.getAvatar());
            message.setUserNickName(userInfo.getNickName());
            message.setCreateTime(DateUtils.getLocalDateTimeStr(e.getCreateTime()));
            messages.add(message);
        });
        responsePrivateMsgGet.setMessages(messages);
        responsePrivateMsgGet.setTotal(page.getTotal());
        return responsePrivateMsgGet;

    }

    @RequestMapping(value = "/find/user/receive/last/msg",method = RequestMethod.GET)
    @ApiOperation("查找用户收到的最后一条消息和联系人")
    public ResponseReceiveMsgGet getUserReceiveMsgAndUser(@PathVariable("userId") Integer userId, RequestPrivateMsgReceiveFilterGet requestPrivateMsgFilterGet){
        PrivateMsgReceiveUserFilterBo privateMsgFilterBo = new PrivateMsgReceiveUserFilterBo();
        BeanUtils.copyProperties(requestPrivateMsgFilterGet,privateMsgFilterBo);
        privateMsgFilterBo.setUserid(userId);
        Page<PrivateMsgEntity> page = privateMsgService.findUserReceiveMsg(privateMsgFilterBo);
        ResponseReceiveMsgGet response = new ResponseReceiveMsgGet();
        List<ResponseReceiveMsgGet.Message> messages = new ArrayList<>();
        page.getData().forEach(e->{
            int noReadNums = 0;
            ResponseReceiveMsgGet.Message message = new ResponseReceiveMsgGet.Message();
            BeanUtils.copyProperties(e,message);
            Integer fromUserId;
            if(e.getUserid()==userId){
                fromUserId = e.getReceiveUserid();
            }else{
                fromUserId = e.getUserid();
                noReadNums = privateMsgService.findNoReadNums(e.getUserid(),userId,-1);
            }
            UserInfoVo fromUserInfo = userInfoService.info(fromUserId);
            message.setType(MessageTypeEnum.getEnumType(e.getType()).getValue());
            message.setFromUserAvatar(fromUserInfo.getAvatar());
            message.setFromUserid(fromUserId);
            message.setFromUserNickName(fromUserInfo.getNickName());
            message.setCreateTime(DateUtils.getLocalDateTimeStr(e.getCreateTime()));
            message.setNoReadNums(noReadNums);
            messages.add(message);
        });
        response.setMessages(messages);
        response.setTotal(page.getTotal());
        return response;

    }
}
