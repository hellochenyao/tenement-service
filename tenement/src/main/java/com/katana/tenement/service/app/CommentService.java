package com.katana.tenement.service.app;

import com.katana.tenement.domain.entity.CommentEntity;
import com.katana.tenement.service.app.bo.comment.CommentBo;
import com.katana.tenement.service.app.bo.comment.CommentFilterBo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    CommentEntity createComment(CommentBo commentBo);

    Page<CommentEntity> queryComment(CommentFilterBo commentFilterBo, Pageable pageable);
}
