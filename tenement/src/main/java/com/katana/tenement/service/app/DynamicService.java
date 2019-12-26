package com.katana.tenement.service.app;

import com.katana.tenement.domain.entity.DynamicEntity;
import com.katana.tenement.service.app.bo.dynamic.DynamicBo;
import com.katana.tenement.service.app.bo.dynamic.DynamicFilterBo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DynamicService {

    void createDynamic(DynamicBo dynamicBo);

    Page<DynamicEntity> query(DynamicFilterBo dynamicFilterBo);
}
