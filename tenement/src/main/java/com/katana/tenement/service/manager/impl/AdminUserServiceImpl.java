package com.katana.tenement.service.manager.impl;

import com.katana.tenement.dao.manager.AdminUserDao;
import com.katana.tenement.dao.manager.AdminUserRepo;
import com.katana.tenement.domain.entity.AdminUserEntity;
import com.katana.tenement.framework.common.RedisClient;
import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.service.manager.AdminUserService;
import com.katana.tenement.service.manager.bo.AdminUserBo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    private final static String CODE_KEY = "auth_code_";
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private AdminUserRepo adminUserRepo;

    @Override
    public AdminUserEntity login(AdminUserBo adminUserBo) {
        if (StringUtils.isEmpty(adminUserBo.getAuthcodeuuid())) {
            throw new BusinessException("ADMIN_LOGIN_EXCEPTION", "请重新登录");
        }
        if (StringUtils.isEmpty(adminUserBo.getAuthcode())) {
            throw new BusinessException("ADMIN_LOGIN_AUTHCODE_ISNULL", "请输入验证码");
        }
        String cachAuthCode = redisClient.get(CODE_KEY + adminUserBo.getAuthcodeuuid());
        if (StringUtils.isEmpty(cachAuthCode)) {
            throw new BusinessException("ADMIN_LOGIN_AUTHCODE_EXPIRED", "验证码过期");
        }
        if (!adminUserBo.getAuthcode().toLowerCase().equals(cachAuthCode)) {
            throw new BusinessException("ADMIN_LOGIN_AUTHCODE_ERROR", "验证码错误");
        }
        redisClient.remove(CODE_KEY + adminUserBo.getAuthcodeuuid());
        AdminUserEntity adminUserEntity = adminUserRepo.findByUsernameAndPasswordAndStatus(adminUserBo.getUsername(), adminUserBo.getPassword(), 1);
        return adminUserEntity;
    }
}
