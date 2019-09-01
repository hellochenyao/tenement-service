package com.katana.tenement.web.app.controller;

import com.katana.tenement.dao.app.vo.privateMsg.PrivateMsgUserReceiveFilterVo;
import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.domain.emuns.MessageTypeEnum;
import com.katana.tenement.domain.emuns.RentTypeEnum;
import com.katana.tenement.domain.entity.PrivateMsgEntity;
import com.katana.tenement.framework.constant.ConstantConfig;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.framework.util.DateUtils;
import com.katana.tenement.framework.util.FileUploadUtils;
import com.katana.tenement.framework.websocket.WebSocketServer;
import com.katana.tenement.service.app.PrivateMsgService;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgBo;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgFilterBo;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgReceiveUserFilterBo;
import com.katana.tenement.web.app.api.invitation.ResponseHousingResourcePost;
import com.katana.tenement.web.app.api.privateMsg.*;
import com.katana.tenement.web.main.utils.PathUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/app/tenement/{userId}")
@Api(tags = "APP-私信模块")
@Slf4j
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
            message.setType(MessageTypeEnum.getEnumByCode(e.getType()).getValue());
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
            if(e.getUserid().equals(userId)){
                fromUserId = e.getReceiveUserid();
            }else{
                fromUserId = e.getUserid();
                noReadNums = privateMsgService.findNoReadNums(e.getUserid(),userId,-1);
            }
            PrivateMsgFilterBo filterBo = new PrivateMsgFilterBo();
            filterBo.setReceiveUserid(e.getReceiveUserid());
            filterBo.setUserid(e.getUserid());
            filterBo.setPageNo(1);
            filterBo.setPageSize(10);
            Page<PrivateMsgEntity> msgEntityPage = privateMsgService.findHistoryMsg(filterBo);
            if(msgEntityPage.getTotal()>0){
                LocalDateTime create = msgEntityPage.getData().get(0).getCreateTime();
                message.setCreateTime(DateUtils.getLocalDateTimeStr(create));
                message.setContent(msgEntityPage.getData().get(0).getContent());
                message.setDesc(msgEntityPage.getData().get(0).getDescText());
            }
            UserInfoVo fromUserInfo = userInfoService.info(fromUserId);
            message.setType(MessageTypeEnum.getEnumByCode(e.getType()).getValue());
            message.setFromUserAvatar(fromUserInfo.getAvatar());
            message.setFromUserid(fromUserId);
            message.setFromUserNickName(fromUserInfo.getNickName());
            message.setNoReadNums(noReadNums);
            messages.add(message);
        });
        response.setMessages(messages);
        response.setTotal(page.getTotal());
        return response;

    }
    @RequestMapping(value = "/picture/message/upload/{fileType}",method = RequestMethod.POST)
    @ApiOperation("发聊天的图片 0图片 1视频")
    public ResponseHousingResourcePost upload(MultipartFile fileResource,@PathVariable("fileType") int fileType, @PathVariable("userId") int userId, @RequestParam int toUserid) {
        String path = PathUtils.getJarPath() + File.separator + ConstantConfig.UPLOAD_DIRECTORY_NAME + File.separator + ConstantConfig.UPLOAD_PICTURE+File.separator+"message"+File.separator+userId+"-"+toUserid;
        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        String imgUrl = FileUploadUtils.upload(fileResource, path,fileType);
        ResponseHousingResourcePost response = new ResponseHousingResourcePost();
        response.setResourceUrl("message"+File.separator+userId+"-"+toUserid+ File.separator+ imgUrl);
        return response;
    }

    @RequestMapping(value = "/private/send/message",method = RequestMethod.POST)
    @ApiOperation("发消息")
    public void saveMessage(@RequestBody RequestPrivateMsgPost request,@PathVariable("userId") Integer userId){
        PrivateMsgBo privateMsgBo = new PrivateMsgBo();
        int type =MessageTypeEnum.getEnumByValue(request.getType()).getCode();
        BeanUtils.copyProperties(request,privateMsgBo);
        privateMsgBo.setUpdateTime(LocalDateTime.now());
        privateMsgBo.setCreateTime(LocalDateTime.now());
        privateMsgBo.setUserid(userId);
        privateMsgBo.setContent(request.getContent());
        privateMsgBo.setIsRead(-1);
        privateMsgBo.setType(type);
        try{
        privateMsgService.saveMsg(privateMsgBo);
        }catch (Exception e){
            log.info(e.getMessage());
            e.printStackTrace();
            WebSocketServer.sendInfo(request.getMsgId(),"error",-1,userId);
            throw new BusinessException("SEND_ERROR","消息发送失败");
        }
        WebSocketServer.sendInfo(request.getMsgId(),"newMsg",request.getReceiveUserid(),userId);
        WebSocketServer.sendInfo(request.getMsgId(),"success",-1,userId);
    }
}
