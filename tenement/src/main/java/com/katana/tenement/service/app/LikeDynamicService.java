package com.katana.tenement.service.app;

import com.katana.tenement.domain.entity.LikeDynamicEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LikeDynamicService {

     void LikeDynamic(Integer userId,Integer dynamicId);

     Page<LikeDynamicEntity> query(Integer dynamicId,Integer pageNo,Integer pageSize);

     List<LikeDynamicEntity> queryState(Integer userId,Integer dynamicId);
}
