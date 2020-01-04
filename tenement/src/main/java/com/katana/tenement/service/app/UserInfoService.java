package com.katana.tenement.service.app;


import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.domain.entity.UserInfoEntity;
import com.katana.tenement.service.app.bo.userinfo.UserLoginBo;
import com.katana.tenement.service.app.bo.userinfo.UserModifyBo;
import com.katana.tenement.service.app.bo.tipoffs.UserTipOffsBo;
import org.springframework.data.domain.Page;

/**
 * 微信相关 service
 * Created by mumu on 2019/3/27.
 */
public interface UserInfoService {

    Page<UserInfoEntity> findFriends(String content,int pageNo,int pageSize);

    UserLoginBo login(String jsCode);

    UserInfoVo wxInfo(String encryptedData, String iv);

    void wxPhoneNum(String encryptedData, String iv);

    UserInfoVo info(int userId);

    void modify(UserModifyBo userModifyBo);

    void updateLastLoginTime(int id);

}
