package com.katana.tenement.service.app.impl;

import com.alibaba.fastjson.JSONObject;
import com.katana.tenement.dao.app.TenementInvitationDao;
import com.katana.tenement.dao.app.TenementInvitationRepo;
import com.katana.tenement.dao.app.vo.tenementInvitation.TenementInvitationFilterVo;
import com.katana.tenement.domain.entity.ConcernEntity;
import com.katana.tenement.domain.entity.TenementInvitationEntity;
import com.katana.tenement.domain.entity.UserMsgEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.framework.util.DateUtils;
import com.katana.tenement.framework.websocket.WebSocketServer;
import com.katana.tenement.service.app.ConcernService;
import com.katana.tenement.service.app.TenementInvitationService;
import com.katana.tenement.service.app.bo.tenementInvitation.TenementInvitationBo;
import com.katana.tenement.service.app.bo.tenementInvitation.TenementInvitationFilterBo;
import com.katana.tenement.service.app.bo.tenementInvitation.TenementInvitationPutBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;


@Service
public class TenementInvitationServiceImpl implements TenementInvitationService {

    @Autowired
    private TenementInvitationRepo tenementInvitationRepo;

    @Autowired
    private TenementInvitationDao tenementInvitationDao;


    @Autowired
    private ConcernService concernService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${amap.key}")
    private String amapKey;

    @Override
    public Page<TenementInvitationEntity> findInvitations(TenementInvitationFilterBo tenementInvitationFilterBo) {
        TenementInvitationFilterVo tenementInvitationFilterVo = new TenementInvitationFilterVo();
        BeanUtils.copyProperties(tenementInvitationFilterBo, tenementInvitationFilterVo);
        Page<TenementInvitationEntity> pageData = tenementInvitationDao.findInvitation(tenementInvitationFilterVo);
        return pageData;
    }

    @Override
    public void create(TenementInvitationBo tenementInvitationBo) {
        TenementInvitationEntity tenementInvitationEntity = new TenementInvitationEntity();
        BeanUtils.copyProperties(tenementInvitationBo, tenementInvitationEntity);
//        String httpAddr = "https://restapi.amap.com/v3/geocode/regeo?key={key}&location={location}";
//        Map<String,String> param = new HashMap<>();
//        param.put("location",tenementInvitationBo.getLocation());
//        param.put("key",amapKey);
//        String response = restTemplate.getForObject(httpAddr,String.class,param);
//        Object resObj = JSONObject.parseObject(response);
//        String city = ((JSONObject) resObj).getJSONObject("regeocode").getJSONObject("addressComponent").getString("city");
        tenementInvitationEntity.setCity(tenementInvitationBo.getLocation().split(",")[0]);
        TenementInvitationEntity responseInvitation = tenementInvitationRepo.save(tenementInvitationEntity);
        List<ConcernEntity> concernEntityList = concernService.findConcerns(tenementInvitationBo.getUserId());
        concernEntityList.forEach(e->{
            WebSocketServer.sendInfo(responseInvitation.getId(),"concernInvitation",e.getUserid(),-1);
        });
    }

    @Override
    public void updateInvitation(TenementInvitationPutBo tenementInvitationPutBo) {
        TenementInvitationEntity tenementInvitationEntity = tenementInvitationRepo.findById(tenementInvitationPutBo.getId()).get();
        BeanUtils.copyProperties(tenementInvitationPutBo,tenementInvitationEntity);
        tenementInvitationRepo.save(tenementInvitationEntity);
    }

    @Override
    public void deleteInvitation(int id) {
        TenementInvitationEntity tenementInvitationEntity = tenementInvitationRepo.findById(id).orElse(null);
        if(tenementInvitationEntity!=null){
            tenementInvitationRepo.delete(tenementInvitationEntity);
        }
    }

    @Override
    public Page<UserMsgEntity> findUserMsgs(int invitationId) {
        return null;
    }
}
