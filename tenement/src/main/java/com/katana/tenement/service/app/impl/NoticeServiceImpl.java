package com.katana.tenement.service.app.impl;

import com.katana.tenement.dao.app.NoticeRepo;
import com.katana.tenement.domain.entity.NoticeEntity;
import com.katana.tenement.service.app.NoticeService;
import com.katana.tenement.service.app.bo.notice.NoticeBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeRepo noticeRepo;

    @Override
    public void createNotice(NoticeBo noticeBo) {
        NoticeEntity noticeEntity = new NoticeEntity();
        BeanUtils.copyProperties(noticeBo,noticeEntity);
        noticeEntity.setCreateTime(LocalDateTime.now());
        noticeEntity.setUpdateTime(LocalDateTime.now());
        noticeRepo.save(noticeEntity);
    }
}
