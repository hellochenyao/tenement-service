package com.katana.tenement.service.app;

import com.katana.tenement.domain.entity.CircleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CircleService {

    void createCircle(CircleEntity circleEntity);

    Page<CircleEntity> findCircle(int id, Pageable pageable);
}
