package com.katana.tenement.service.app.impl;

import com.katana.tenement.dao.app.PrivateMsgDao;
import com.katana.tenement.dao.app.PrivateMsgRepo;
import com.katana.tenement.dao.app.UserRelationRepo;
import com.katana.tenement.dao.app.vo.privateMsg.PrivateMsgUserReceiveFilterVo;
import com.katana.tenement.domain.entity.PrivateMsgEntity;
import com.katana.tenement.domain.entity.UserRelationEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.service.app.PrivateMsgService;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgBo;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgFilterBo;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgReceiveUserFilterBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrivateMsgServiceImpl implements PrivateMsgService {

    @Autowired
    private PrivateMsgRepo privateMsgRepo;

    @Autowired
    private UserRelationRepo userRelationRepo;

    @Autowired
    private PrivateMsgDao privateMsgDao;

    @Override
    public void saveMsg(PrivateMsgBo privateMsgBo) {
        UserRelationEntity userRelation = userRelationRepo.findByUseridAndFriendId(privateMsgBo.getUserid(),privateMsgBo.getReceiveUserid());
        if(userRelation!=null && userRelation.getType()==-1){
            throw new BusinessException("RELATION_ERROR","发送方已被接收方拉黑！");
        }
        PrivateMsgEntity privateMsgEntity = new PrivateMsgEntity();
        BeanUtils.copyProperties(privateMsgBo,privateMsgEntity);
        privateMsgRepo.save(privateMsgEntity);
    }

    @Override
    public Page<PrivateMsgEntity> findHistoryMsg(PrivateMsgFilterBo privateMsgFilterBo) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        PageRequest pageRequest = PageRequest.of(privateMsgFilterBo.getPageNo()-1,privateMsgFilterBo.getPageSize(),sort);
        org.springframework.data.domain.Page<PrivateMsgEntity> page = privateMsgRepo.findHistoryMsg(privateMsgFilterBo.getUserid(),privateMsgFilterBo.getReceiveUserid(),pageRequest);
        Page pageData = new Page();
        pageData.setData(page.getContent());
        pageData.setTotal(page.getContent().size());
        return pageData;
    }

    @Override
    public Page<PrivateMsgEntity> findUserReceiveMsg(PrivateMsgReceiveUserFilterBo privateMsgFilterBo) {
        PrivateMsgUserReceiveFilterVo privateVo = new PrivateMsgUserReceiveFilterVo();
        BeanUtils.copyProperties(privateMsgFilterBo,privateVo);
        Page<PrivateMsgEntity> page = privateMsgDao.findConnectMsg(privateVo);
        return page;
    }

    @Override
    public Integer findNoReadNums(int userid, int receiveUserid, int readType) {

        return privateMsgRepo.findNoReadNums(userid,receiveUserid,readType);
    }
}
