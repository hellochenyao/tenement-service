package com.katana.tenement.service.app.impl;

import com.alibaba.fastjson.JSONObject;
import com.katana.tenement.dao.app.TenementInvitationDao;
import com.katana.tenement.dao.app.TenementInvitationRepo;
import com.katana.tenement.dao.app.vo.tenementInvitation.InvitationLogFilterVo;
import com.katana.tenement.dao.app.vo.tenementInvitation.InvitationUserInfoVo;
import com.katana.tenement.dao.app.vo.tenementInvitation.TenementInvitationFilterVo;
import com.katana.tenement.domain.emuns.ConcernType;
import com.katana.tenement.domain.entity.ConcernEntity;
import com.katana.tenement.domain.entity.TenementInvitationEntity;
import com.katana.tenement.domain.entity.UserMsgEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.framework.util.DateUtils;
import com.katana.tenement.framework.websocket.WebSocketServer;
import com.katana.tenement.service.app.ConcernService;
import com.katana.tenement.service.app.TenementInvitationService;
import com.katana.tenement.service.app.bo.tenementInvitation.InvitationPublishLogBo;
import com.katana.tenement.service.app.bo.tenementInvitation.TenementInvitationBo;
import com.katana.tenement.service.app.bo.tenementInvitation.TenementInvitationFilterBo;
import com.katana.tenement.service.app.bo.tenementInvitation.TenementInvitationPutBo;
import okhttp3.OkHttpClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
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

    @Value("${qqmap.key}")
    private String amapKey;


    @Override
    public Page<TenementInvitationEntity> findInvitations(TenementInvitationFilterBo tenementInvitationFilterBo) {
        TenementInvitationFilterVo tenementInvitationFilterVo = new TenementInvitationFilterVo();
        BeanUtils.copyProperties(tenementInvitationFilterBo, tenementInvitationFilterVo);
        Page<TenementInvitationEntity> pageData = tenementInvitationDao.findInvitation(tenementInvitationFilterVo);
        return pageData;
    }

    @Override
    public TenementInvitationEntity create(TenementInvitationBo tenementInvitationBo) {
        TenementInvitationEntity tenementInvitationEntity = new TenementInvitationEntity();
        BeanUtils.copyProperties(tenementInvitationBo, tenementInvitationEntity);
        tenementInvitationEntity.setStatus(1);
//        String httpAddr = "https://restapi.amap.com/v3/geocode/regeo?key={key}&location={location}";
//        Map<String,String> param = new HashMap<>();
//        param.put("location",tenementInvitationBo.getLocation());
//        param.put("key",amapKey);
//        String response = restTemplate.getForObject(httpAddr,String.class,param);
//        Object resObj = JSONObject.parseObject(response);
//        String city = ((JSONObject) resObj).getJSONObject("regeocode").getJSONObject("addressComponent").getString("city");
        TenementInvitationEntity responseInvitation = tenementInvitationRepo.save(tenementInvitationEntity);
        List<ConcernEntity> concernEntityList = concernService.findConcerns(tenementInvitationBo.getUserId(), ConcernType.USER);
        concernEntityList.forEach(e->{
            WebSocketServer.sendInfo(responseInvitation.getId(),"concernInvitation",e.getUserid(),-1);
        });
        return responseInvitation;
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

    @Override
    public Page<InvitationUserInfoVo> findPublishLog(InvitationPublishLogBo logBo) {
        InvitationLogFilterVo filterVo = new InvitationLogFilterVo();
        BeanUtils.copyProperties(logBo,filterVo);
        return tenementInvitationDao.findPublishLog(filterVo);
    }

    /**
     * 顶贴
     *
     */
    @Override
    public void refreshInvitation(int invitationId) {
        TenementInvitationEntity tenementInvitationEntity = tenementInvitationRepo.findById(invitationId).orElse(null);
        if(tenementInvitationEntity!=null){
            tenementInvitationEntity.setUpdateTime(LocalDateTime.now());
        }else{
            throw new BusinessException("INVITATION_NOT_FOUND","帖子不存在");
        }
        tenementInvitationRepo.save(tenementInvitationEntity);
    }

    /**
     * 修改帖子状态
     * @param invitationId
     * @param status
     */
    @Override
    public void setInvitationStatus(int invitationId, int status) {
        TenementInvitationEntity tenementInvitationEntity = tenementInvitationRepo.findById(invitationId).orElse(null);
        if(tenementInvitationEntity!=null){
            tenementInvitationEntity.setStatus(status);
        }else{
            throw new BusinessException("INVITATION_NOT_FOUND","帖子不存在");
        }
        tenementInvitationRepo.save(tenementInvitationEntity);
    }

    /**
     * 查找帖子
     * @param id
     * @return
     */
    @Override
    public TenementInvitationEntity findInvitationById(int id) {
        TenementInvitationEntity tenementInvitationEntity = tenementInvitationRepo.findById(id).orElse(null);
        return tenementInvitationEntity;
    }
}
