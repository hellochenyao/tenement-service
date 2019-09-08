package com.katana.tenement.web.app.controller;

import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.framework.constant.ConstantConfig;
import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.framework.util.DateUtils;
import com.katana.tenement.framework.util.FileUploadUtils;
import com.katana.tenement.service.app.TenementInvitationService;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.bo.tenementInvitation.TenementInvitationBo;
import com.katana.tenement.service.app.bo.tenementInvitation.TenementInvitationPutBo;
import com.katana.tenement.web.app.api.invitation.RequestInvitationPut;
import com.katana.tenement.web.app.api.invitation.RequestTenementInvitationPost;
import com.katana.tenement.web.app.api.invitation.ResponseHousingResourcePost;
import com.katana.tenement.web.main.utils.PathUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;

@RestController
@Api(tags = "App-用户贴子管理模块")
@RequestMapping("/app/operation/{userId}")
public class AppInvitationOperateController {


    @Autowired
    private TenementInvitationService tenementInvitationService;

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("根据类型发布帖子")
    @RequestMapping(value = "/item/{type}/publish", method = RequestMethod.POST)
    public void publishInvitation(@RequestBody RequestTenementInvitationPost request, @PathVariable("userId") Integer userId,@PathVariable("type") Integer type) {
        if (StringUtils.isEmpty(request.getTitle())) {
            throw new BusinessException("PARAM_ERROR", "帖子标题不能为空");
        }
        if (StringUtils.isEmpty(request.getContent())) {
            throw new BusinessException("PARAM_ERROR", "帖子内容不能为空");
        }
        if(type!=0&&type!=1&&type!=2){
            throw new BusinessException("PARAM_ERROR", "帖子类型出错");
        }
        TenementInvitationBo tenementInvitationBo = new TenementInvitationBo();
        BeanUtils.copyProperties(request, tenementInvitationBo);
        UserInfoVo userInfoVo = userInfoService.info(userId);
        tenementInvitationBo.setPublisher(userInfoVo.getNickName());
        tenementInvitationBo.setCreateTime(LocalDateTime.now());
        tenementInvitationBo.setUpdateTime(LocalDateTime.now());
        tenementInvitationBo.setUserId(userId);
        if(request.getDesiredDate()!=null&&StringUtils.isNotEmpty(request.getDesiredDate())){
            tenementInvitationBo.setDesiredDate(DateUtils.getLocalDate(request.getDesiredDate(), "yyyy-MM-dd"));
        }
        tenementInvitationBo.setType(type);
        tenementInvitationService.create(tenementInvitationBo);
    }


    @ApiOperation("上传房源图片或视频 0图片 1视频")
    @RequestMapping(value = "/housing-resource/{fileType}/upload", method = RequestMethod.POST)
    public ResponseHousingResourcePost upload(MultipartFile fileResource, @PathVariable("fileType") Integer fileType,@PathVariable("userId") int userId) {
        String path = PathUtils.getJarPath() + File.separator + ConstantConfig.UPLOAD_DIRECTORY_NAME + File.separator + ConstantConfig.UPLOAD_HOUSING_DIRECTORY_NAME+File.separator+userId;
        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        String imgUrl = FileUploadUtils.upload(fileResource, path, fileType);
        ResponseHousingResourcePost response = new ResponseHousingResourcePost();
        response.setResourceUrl(ConstantConfig.UPLOAD_HOUSING_DIRECTORY_NAME +File.separator+userId + File.separator + imgUrl);
        return response;
    }


    @ApiOperation("更新帖子信息")
    @RequestMapping(value = "/invitation/{invitationId}", method = RequestMethod.PUT)
    public void updateInfo(@PathVariable int invitationId, @RequestBody RequestInvitationPut request) {
        TenementInvitationPutBo tenementInvitationPutBo = new TenementInvitationPutBo();
        tenementInvitationPutBo.setId(invitationId);
        BeanUtils.copyProperties(request,tenementInvitationPutBo);
        tenementInvitationPutBo.setDesiredDate(DateUtils.getLocalDate(request.getDesiredDate(), "yyyy-MM-dd"));
        tenementInvitationService.updateInvitation(tenementInvitationPutBo);
    }

    @ApiOperation("删除帖子")
    @RequestMapping(value = "/delete/{invitationId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int invitationId) {
        tenementInvitationService.deleteInvitation(invitationId);
    }

}
