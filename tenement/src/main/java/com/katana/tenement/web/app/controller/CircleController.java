package com.katana.tenement.web.app.controller;

import com.katana.tenement.domain.entity.CircleEntity;
import com.katana.tenement.service.app.CircleService;
import com.katana.tenement.web.app.api.circle.RequestCreateCirclePost;
import com.katana.tenement.web.app.api.circle.ResponseCircleGet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/app/user/circle/{userId}")
@Api(tags = "APP圈子模块")
public class CircleController {

    @Autowired
    private CircleService circleService;

    @ApiOperation("用户新建圈子")
    @PostMapping(value = "/create")
    public void createCircle(RequestCreateCirclePost request){
        CircleEntity circleEntity = new CircleEntity();
        BeanUtils.copyProperties(request,circleEntity);
        circleService.createCircle(circleEntity);
    }

    @ApiOperation("用户查看圈子")
    @GetMapping(value = "/query")
    public ResponseCircleGet getCircle(
            @ApiParam("圈子") @RequestParam(required = false,defaultValue = "0") int circleId,
            @PathVariable int userId,
            @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") int pageNo,
            @ApiParam("页数") @RequestParam(required = false,defaultValue = "10") int pageSize
    ){
        Sort sort = new Sort(Sort.Direction.ASC,"create_time");
        Pageable pageable = PageRequest.of((pageNo-1)*pageSize,pageSize,sort);
        Page<CircleEntity> circleEntities = circleService.findCircle(circleId,pageable);
        List<ResponseCircleGet.Circle> circles = circleEntities.stream().map(v->{
            ResponseCircleGet.Circle circle = new ResponseCircleGet.Circle();
            BeanUtils.copyProperties(v,circle);
            return  circle;
        }).collect(Collectors.toList());
        ResponseCircleGet responseCircleGet = new ResponseCircleGet();
        responseCircleGet.setCircles(circles);
        responseCircleGet.setTotal((int)circleEntities.getTotalElements());
        return responseCircleGet;
    }
}
