package com.katana.tenement.service.manager.impl;

import com.katana.tenement.dao.app.DynamicRepo;
import com.katana.tenement.domain.entity.DynamicEntity;
import com.katana.tenement.service.manager.DynamicService;
import com.katana.tenement.service.manager.bo.dynamic.DynamicBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "DynamicServiceManager")
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private DynamicRepo dynamicRepo;

    @Override
    public void createDynamic(DynamicBo dynamicBo) {
        DynamicEntity dynamicEntity = new DynamicEntity();
        BeanUtils.copyProperties(dynamicBo,dynamicEntity);
        dynamicRepo.save(dynamicEntity);
    }
}
