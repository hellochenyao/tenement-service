package com.katana.tenement.dao.app;

import com.katana.tenement.dao.app.vo.userLike.GiveUserLikeFilterVo;
import com.katana.tenement.dao.app.vo.userLike.GiveUserLikeVo;
import com.katana.tenement.framework.dto.page.Page;

public interface UserLikeDao {

    public Page< GiveUserLikeVo> findGiveLikeUser(GiveUserLikeFilterVo filterVo);

}
