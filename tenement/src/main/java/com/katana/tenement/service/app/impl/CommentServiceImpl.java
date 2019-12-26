package com.katana.tenement.service.app.impl;

import com.katana.tenement.dao.app.CommentRepo;
import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.domain.entity.CommentEntity;
import com.katana.tenement.service.app.CommentService;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.bo.comment.CommentBo;
import com.katana.tenement.service.app.bo.comment.CommentFilterBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public CommentEntity createComment(CommentBo commentBo) {
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(commentBo,commentEntity);
        if(commentBo.getMessagedId()!=0){
            UserInfoVo userInfoVo = userInfoService.info(commentBo.getCommentedId());
           String content = commentEntity.setNewContent(commentBo.getContent(),userInfoVo.getNickName());
           commentEntity.setContent(content);
        }
       return commentRepo.save(commentEntity);
    }

    @Override
    public Page<CommentEntity> queryComment(CommentFilterBo commentFilterBo, Pageable pageable) {
        return commentRepo.findByDynamicId(commentFilterBo.getDynamicId(),pageable);
    }
}
