package com.katana.tenement.dao.app;

import com.katana.tenement.dao.app.vo.userMsg.UserMsgFilterVo;
import com.katana.tenement.domain.entity.UserMsgEntity;
import com.katana.tenement.framework.dto.page.Page;

/**
 * 用户帖子留言
 */
public interface UserMsgDao {
    Page<UserMsgEntity> findNewWord(UserMsgFilterVo filterVo);
}
