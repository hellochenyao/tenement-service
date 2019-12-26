package com.katana.tenement.web.app.controller;

import com.katana.tenement.dao.app.CommentRepo;
import com.katana.tenement.dao.app.DynamicRepo;
import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.domain.entity.CommentEntity;
import com.katana.tenement.domain.entity.DynamicEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.service.app.CommentService;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.bo.comment.CommentBo;
import com.katana.tenement.service.app.bo.comment.CommentFilterBo;
import com.katana.tenement.web.app.api.comment.RequestCommentFilterGet;
import com.katana.tenement.web.app.api.comment.RequestCreateCommentPost;
import com.katana.tenement.web.app.api.comment.ResponseCommentGet;
import com.katana.tenement.web.app.api.comment.ResponseCommentPost;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Api(tags = "APP评论模块")
@RequestMapping(value = "/app/user/comment/{userId}")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private DynamicRepo dynamicRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("用户新建评论")
    @PostMapping(value = "/create/comment")
    public ResponseCommentPost createComment(@RequestBody RequestCreateCommentPost request, @PathVariable int userId){
        CommentBo commentBo = new CommentBo();
        commentBo.setUserId(userId);
        BeanUtils.copyProperties(request,commentBo);
        if(request.getMessagedId()==0){
            Optional<DynamicEntity> optional = dynamicRepo.findById(request.getDynamicId());
            if(!optional.isPresent()){
                throw new BusinessException("DYNAMIC_NOT_EXIST","动态不存在！");
            }
            commentBo.setCommentedId(optional.get().getUserId());
        }else{
            Optional<CommentEntity> optional = commentRepo.findById(request.getMessagedId());
            if(!optional.isPresent()){
                throw new BusinessException("COMMENT_NOT_EXIST","评论不存在！");
            }
            commentBo.setCommentedId(optional.get().getUserId());
        }
        CommentEntity commentEntity = commentService.createComment(commentBo);
        ResponseCommentPost responseCommentPost = new ResponseCommentPost();
        BeanUtils.copyProperties(commentEntity,responseCommentPost);
        return responseCommentPost;
    }

    @ApiOperation("用户查看评论")
    @GetMapping(value = "/query/comment")
    public ResponseCommentGet queryComment(RequestCommentFilterGet requestCommentFilterGet){
        CommentFilterBo commentFilterBo = new CommentFilterBo();
        commentFilterBo.setDynamicId(requestCommentFilterGet.getDynamicId());
        Sort sort = new Sort(Sort.Direction.ASC,"createTime");
        Pageable pageable = PageRequest.of(requestCommentFilterGet.getPageNo()-1,requestCommentFilterGet.getPageSize(),sort);
        org.springframework.data.domain.Page<CommentEntity> pageData = commentService.queryComment(commentFilterBo,pageable);
        ResponseCommentGet response = new ResponseCommentGet();
        List<ResponseCommentGet.Comment> commentList = pageData.stream().map(e->{
            ResponseCommentGet.Comment data = new ResponseCommentGet.Comment();
            UserInfoVo userInfoVo = userInfoService.info(e.getUserId());
            BeanUtils.copyProperties(e,data);
            data.setAvatar(userInfoVo.getAvatar());
            data.setUserName(userInfoVo.getNickName());
            return data;
        }).collect(Collectors.toList());
        response.setComments(commentList);
        response.setTotal((int)pageData.getTotalElements());
        return response;
    }

}
