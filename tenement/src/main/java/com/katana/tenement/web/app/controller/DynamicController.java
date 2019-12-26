package com.katana.tenement.web.app.controller;

import com.katana.tenement.dao.app.LikeDynamicRepo;
import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.domain.entity.DynamicEntity;
import com.katana.tenement.domain.entity.LikeDynamicEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.service.app.DynamicService;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.bo.dynamic.DynamicBo;
import com.katana.tenement.service.app.bo.dynamic.DynamicFilterBo;
import com.katana.tenement.web.app.api.dynamic.RequestCreateDynamicPost;
import com.katana.tenement.web.app.api.dynamic.RequestDynamicFilterGet;
import com.katana.tenement.web.app.api.dynamic.ResponseDynamicGet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "APP动态模块")
@RequestMapping(value = "/app/user/dynamic/{userId}")
public class DynamicController {
    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private LikeDynamicRepo likeDynamicRepo;

    @ApiOperation(value = "用户发动态")
    @PostMapping(value = "/create/dynamic")
    public void createDynamic(@RequestBody RequestCreateDynamicPost request){
        DynamicBo dynamicBo = new DynamicBo();
        BeanUtils.copyProperties(request,dynamicBo);
        dynamicService.createDynamic(dynamicBo);
    }

    @ApiOperation(value = "用户查动态")
    @GetMapping(value = "/query/dynamic")
    public ResponseDynamicGet queryDynamic(RequestDynamicFilterGet request,@PathVariable int userId){
        DynamicFilterBo dynamicFilterBo = new DynamicFilterBo();
        BeanUtils.copyProperties(request,dynamicFilterBo);
        dynamicFilterBo.setUserId(userId);
        ResponseDynamicGet responseDynamicGet = new ResponseDynamicGet();
        org.springframework.data.domain.Page<DynamicEntity> pageData = dynamicService.query(dynamicFilterBo);
        List<ResponseDynamicGet.Dynamic> dynamicV  = pageData.stream().map(e->{
            ResponseDynamicGet.Dynamic dynamic = new ResponseDynamicGet.Dynamic();
            BeanUtils.copyProperties(e,dynamic);
            UserInfoVo userInfoVo = userInfoService.info(e.getUserId());
            dynamic.setUserName(userInfoVo.getNickName());
            dynamic.setAvatar(userInfoVo.getAvatar());
            return dynamic;
        }).collect(Collectors.toList());
        responseDynamicGet.setData(dynamicV);
        responseDynamicGet.setTotal((int)pageData.getTotalElements());
        return responseDynamicGet;
    }
}
