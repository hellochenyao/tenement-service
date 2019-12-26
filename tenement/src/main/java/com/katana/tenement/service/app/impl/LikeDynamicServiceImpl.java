package com.katana.tenement.service.app.impl;

import com.katana.tenement.dao.app.LikeDynamicRepo;
import com.katana.tenement.domain.entity.LikeDynamicEntity;
import com.katana.tenement.service.app.LikeDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeDynamicServiceImpl implements LikeDynamicService {

    @Autowired
    private LikeDynamicRepo likeDynamicRepo;

    @Override
    public void LikeDynamic(Integer userId, Integer dynamicId) {
        LikeDynamicEntity likeDynamicEntity = new LikeDynamicEntity(
                userId,
                dynamicId
        );
        Optional<LikeDynamicEntity> optionalLikeDynamicEntity = likeDynamicRepo.findById(likeDynamicEntity.getId());
        if(!optionalLikeDynamicEntity.isPresent()){
            likeDynamicRepo.save(likeDynamicEntity);
        }else{
            likeDynamicRepo.deleteById(likeDynamicEntity.getId());
        }
    }

    @Override
    public Page<LikeDynamicEntity> query(Integer dynamicId, Integer pageNo, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);
        return likeDynamicRepo.findByDynamicId(dynamicId,pageable);
    }

    @Override
    public List<LikeDynamicEntity> queryState(Integer userId, Integer dynamicId) {
        return likeDynamicRepo.findByUserIdAndDynamicId(userId,dynamicId);
    }
}
