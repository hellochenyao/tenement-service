package com.katana.tenement.web.app.controller;

import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.domain.entity.LikeDynamicEntity;
import com.katana.tenement.service.app.LikeDynamicService;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.web.app.api.dynamic.ResponseLikeGet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "APP用户点赞动态")
@RequestMapping("/app/like/{userId}/dynamic")
public class LikeDynamicController {

    @Autowired
    private LikeDynamicService likeDynamicService;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/create/{dynamicId}")
    @ApiOperation("用户点赞或取消点赞")
    public void create(@PathVariable Integer userId,@PathVariable Integer dynamicId){
        likeDynamicService.LikeDynamic(userId,dynamicId);
    }

    @GetMapping("/query/{dynamicId}")
    @ApiOperation("用户分页查询点赞")
    public ResponseLikeGet queryLike(@ApiParam("页数") @RequestParam(required = false,defaultValue = "1") Integer pageNo,
                                     @ApiParam("每页个数") @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                     @PathVariable Integer dynamicId){
        Page<LikeDynamicEntity> pageData = likeDynamicService.query(dynamicId,pageNo,pageSize);
        ResponseLikeGet response = new ResponseLikeGet();
        List<ResponseLikeGet.LikeDynamic> likeDynamics = new ArrayList<>();
        likeDynamics = pageData.stream().map(e->{
            ResponseLikeGet.LikeDynamic likeDynamic = new ResponseLikeGet.LikeDynamic();
            BeanUtils.copyProperties(e,likeDynamic);
            UserInfoVo userInfoVo = userInfoService.info(e.getUserId());
            likeDynamic.setAvatar(userInfoVo.getAvatar());
            likeDynamic.setUserName(userInfoVo.getNickName());
            return likeDynamic;
        }).collect(Collectors.toList());
        response.setData(likeDynamics);
        response.setTotal((int)pageData.getTotalElements());
        return response;
    }

    @ApiOperation(value = "判断用户是否点赞动态")
    @GetMapping(value = "/{dynamicId}/state")
    public Boolean find(@PathVariable Integer dynamicId,@PathVariable Integer userId){
        return likeDynamicService.queryState(userId,dynamicId).size()>0;
    }

}
