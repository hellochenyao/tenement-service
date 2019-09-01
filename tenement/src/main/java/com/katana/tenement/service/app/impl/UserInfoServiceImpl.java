package com.katana.tenement.service.app.impl;

import com.alibaba.fastjson.JSON;
import com.katana.tenement.dao.app.UserInfoDao;
import com.katana.tenement.dao.app.UserInfoRepo;
import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.domain.entity.UserInfoEntity;
import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.framework.util.DateUtils;
import com.katana.tenement.framework.util.EmojiFilterUtils;
import com.katana.tenement.service.app.WxAuthService;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.bo.JwtStorageBo;
import com.katana.tenement.service.app.bo.userinfo.UserLoginBo;
import com.katana.tenement.service.app.bo.userinfo.UserModifyBo;
import com.katana.wx.weapp.user.response.SessionKeyResponse;
import com.katana.wx.weapp.user.response.WxUserInfo;
import com.katana.wx.weapp.user.response.WxUserPhone;
import com.katana.wx.weapp.user.utils.WechatBizDataCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by mumu on 2019/3/27.
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Value("${weapp.appid}")
    private String weappAppid;

    @Value("${weapp.secret}")
    private String weappSecret;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private WxAuthService wxAuthService;

    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 微信小程序 -- 登录
     *
     * @param jsCode
     * @return
     */
    @Override
    public UserLoginBo login(String jsCode) {
        //获取sessionKey+openid
        SessionKeyResponse sessionKey = wxAuthService.getSessionKey(weappAppid, weappSecret, jsCode);

        UserInfoEntity userInfo = userInfoRepo.findByOpenId(sessionKey.getOpenid());
        UserLoginBo userLoginBo = new UserLoginBo();

        if (userInfo == null) {
            //首次登陆
            List<UserInfoEntity > lastUser = userInfoDao.findRecords();
            Integer userId = lastUser.get(0).getId()+1;
            userInfo = new UserInfoEntity();
            userInfo.setId(userId);
            userInfo.setOpenId(sessionKey.getOpenid());
            userInfo.setCreateTime(LocalDateTime.now());
            userLoginBo.setIsNew(1);
        }

        userInfo.setLastLoginTime(LocalDateTime.now());
        userInfoRepo.save(userInfo);

        userLoginBo.setSessionKey(sessionKey.getSession_key());
        userLoginBo.setOpenId(sessionKey.getOpenid());
        userLoginBo.setUserId(userInfo.getId());

        return userLoginBo;
    }

    /**
     * 解析 微信用户信息数据
     *
     * @param encryptedData
     * @param iv
     * @return
     */
    @Override
    public UserInfoVo wxInfo(String encryptedData, String iv) {

        UserInfoVo userInfoVo = new UserInfoVo();

        JwtStorageBo jwtStorageBo = (JwtStorageBo) request.getAttribute("userInfo");
        WxUserInfo wxUserInfo = null;
        try {
            //解密用户信息
            String decodeStr = WechatBizDataCrypt.decode(jwtStorageBo.getSessionKey(), encryptedData, iv);
            wxUserInfo = JSON.parseObject(decodeStr, WxUserInfo.class);
            if (wxUserInfo == null || wxUserInfo.getWatermark() == null
                    || !weappAppid.equals(wxUserInfo.getWatermark().getAppid())) {
                //解密失败，用户信息不合格
                throw new BusinessException("INVALID_ENCRYPT_DATA", "无效的加密数据");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("INVALID_ENCRYPT_DATA", "无效的加密数据");
        }
        //存库
        UserInfoEntity userInfo = userInfoRepo.findByOpenId(wxUserInfo.getOpenId());

        if (userInfo == null) {
            userInfo = new UserInfoEntity();
            userInfo.setCreateTime(LocalDateTime.now());

        }

        //更新信息
        userInfo.setNickName(wxUserInfo.getNickName());
        userInfo.setAvatar(wxUserInfo.getAvatarUrl());
        userInfo.setOpenId(wxUserInfo.getOpenId());
        userInfo.setUnionId(wxUserInfo.getUnionId());
        userInfo = userInfoRepo.save(userInfo);

        //微信用户昵称要是含有表情的话，过滤掉，不然保存到数据库中会报错，数据库编码为utf-8,要保存表情等特殊字符的话需要utf8mb4
        EmojiFilterUtils.filterEmoji("");

        BeanUtils.copyProperties(userInfo, userInfoVo);
        userInfoVo.setLastLoginTime(DateUtils.getLocalDateTimeStr(userInfo.getLastLoginTime()));
        userInfoVo.setCreateTime(DateUtils.getLocalDateTimeStr(userInfo.getCreateTime()));
        return userInfoVo;
    }


    /**
     * 更新微信绑定电话
     *
     * @param encryptedData
     * @param iv
     */
    @Override
    public void wxPhoneNum(String encryptedData, String iv) {

        JwtStorageBo jwtStorageBo = (JwtStorageBo) request.getAttribute("userInfo");
        WxUserPhone wxUserPhone = null;

        try {
            //解密用户信息
            String decodeStr = WechatBizDataCrypt.decode(jwtStorageBo.getSessionKey(), encryptedData, iv);
            wxUserPhone = JSON.parseObject(decodeStr, WxUserPhone.class);
            if (wxUserPhone == null || wxUserPhone.getWatermark() == null
                    || !weappAppid.equals(wxUserPhone.getWatermark().getAppid())) {
                //解密失败，用户信息不合格
                throw new BusinessException("INVALID_ENCRYPT_DATA", "无效的加密数据");
            }

            // FIXME: 2019/4/16  存入电话信息
            UserInfoEntity userInfo = userInfoRepo.findByOpenId(jwtStorageBo.getOpenId());
            if (userInfo == null) {
                throw new BusinessException("NO_SUCH_WX_USER", "您未授权微信登陆！");
            }
            userInfo.setPhone(wxUserPhone.getPurePhoneNumber());
            userInfoRepo.save(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("INVALID_ENCRYPT_DATA", "无效的加密数据");
        }

    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfoVo info(int userId) {

        UserInfoEntity userInfoEntity = userInfoRepo.findById(userId);

        if (userInfoEntity == null) {
            throw new BusinessException(BusinessException.Option.NO_SUCH_USER);
        }

        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfoEntity, userInfoVo);
        userInfoVo.setLastLoginTime(DateUtils.getLocalDateTimeStr(userInfoEntity.getLastLoginTime()));
        userInfoVo.setCreateTime(DateUtils.getLocalDateTimeStr(userInfoEntity.getCreateTime()));

        return userInfoVo;
    }

    /**
     * 修改用户信息
     *
     * @param userModifyBo
     */
    @Override
    public void modify(UserModifyBo userModifyBo) {

        JwtStorageBo jwtStorageBo = (JwtStorageBo) request.getAttribute("userInfo");

        UserInfoEntity userInfo = userInfoRepo.findById(jwtStorageBo.getUserId());

        if (userInfo == null) {
            throw new BusinessException("NO_SUCH_USER","该用户不存在！");
        }
        userInfo.setGender(userModifyBo.getGender());
        userInfo.setSchool(userModifyBo.getSchool());
        userInfo.setGrade(userModifyBo.getGrade());
        userInfo.setEduBack(userModifyBo.getEduBack());
        userInfo.setOccupation(userModifyBo.getOccupation());
        userInfo.setIsWorker(userModifyBo.getIsWorker());

        userInfoRepo.save(userInfo);

    }

    @Override
    public void updateLastLoginTime(int id) {
        UserInfoEntity userInfo = userInfoRepo.findById(id);
        userInfo.setLastLoginTime(LocalDateTime.now());
        userInfoRepo.save(userInfo);
    }
}
