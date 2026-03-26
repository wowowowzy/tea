package com.example.tea.service.impl;

import com.example.tea.entity.dto.Community.*;
import com.example.tea.entity.pojo.Community.Collect;
import com.example.tea.entity.pojo.Community.Like;
import com.example.tea.entity.pojo.Community.Post;
import com.example.tea.entity.pojo.PageResult;
import com.example.tea.entity.vo.Community.MyCollectVO;
import com.example.tea.entity.vo.Community.MyPostVO;
import com.example.tea.entity.vo.Community.PostDetailVO;
import com.example.tea.entity.vo.Community.PostListPageVO;
import com.example.tea.mapper.CommunityMapper;
import com.example.tea.service.CommunityService;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import com.github.houbb.sensitive.word.core.SensitiveWordHelper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityMapper communityMapper;

    /**
     * 发布新帖子
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPost(PostCreateDTO dto) {
        if (!SensitiveWordHelper.contains(dto.getContent())
        &&!SensitiveWordHelper.contains(dto.getTitle())) {
            Post post = Post.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .userId(ThreadLocalUserIdUtil.getCurrentId())
                    .build();
            communityMapper.insert(post);
            return post.getId();
        }else {
            String word = new StringBuffer().append(SensitiveWordHelper.findAll(dto.getTitle()))
                    .append(SensitiveWordHelper.findAll(dto.getContent()))
                    .append("这些是违禁词不允许发布！").toString();
            throw new RuntimeException(word);
        }
    }

    /**
     * 查询帖子详情
     */
    @Override
    public PostDetailVO getPostDetail(Long postId) {
        // 1. 查询帖子基础信息
        PostWithUsernameDTO post = communityMapper.selectById(postId);
        List<CommentDTO> comments = communityMapper.getCommentByPostId(postId,ThreadLocalUserIdUtil.getCurrentId());

        if (post == null|| post.getStatus().equals(0)) {
            throw new RuntimeException("帖子不存在或已删除");
        }
        PostDetailVO detailVO = PostDetailVO.builder().build();
        BeanUtils.copyProperties(post,detailVO);
        detailVO.setPostId(post.getId());
        detailVO.setCommentDTOS(comments);
        isCancelDTO isCancel = communityMapper.getCancel(ThreadLocalUserIdUtil.getCurrentId(),postId);
        detailVO.setLikeCancel(
                Optional.ofNullable(isCancel)
                        .map(isCancelDTO::getLikeCancel)
                        .orElse(-1)
        );

        detailVO.setCollectCancel(
                Optional.ofNullable(isCancel)
                        .map(isCancelDTO::getCollectCancel)
                        .orElse(-1)
        );
        return detailVO;
    }

    @Override
    public PageResult getPostList(CommunityQueryDTO dto) {
        PageHelper.startPage(
                dto.getPage()==null ? 1 : dto.getPage(),
                dto.getPageSize()==null ? 10 : dto.getPageSize()
        );
        CommunityQueryWithUserIdDTO build = CommunityQueryWithUserIdDTO
                .builder().userId(ThreadLocalUserIdUtil.getCurrentId()).build();
        BeanUtils.copyProperties(dto,build);
        Page<PostListPageVO> page = communityMapper.getPostList(build);
        return PageResult.builder()
                .total(page.getTotal())
                .records(page.getResult())
                .build();
    }

    @Override
    public List<MyPostVO> getMyPost() {
        Long userId = ThreadLocalUserIdUtil.getCurrentId();
        return communityMapper.getMyPost(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMyPost(UpdateDTO updateMyPost) {
        if (!SensitiveWordHelper.contains(updateMyPost.getContent())
                &&!SensitiveWordHelper.contains(updateMyPost.getTitle())) {
            UpdateMyPostDTO updateMyPostDTO = UpdateMyPostDTO.builder().build();
            BeanUtils.copyProperties(updateMyPost,updateMyPostDTO);
            updateMyPostDTO.setUserId(ThreadLocalUserIdUtil.getCurrentId());
            communityMapper.updateMyPost(updateMyPostDTO);
        }else {
            String word = new StringBuffer().append(SensitiveWordHelper.findAll(updateMyPost.getTitle()))
                    .append(SensitiveWordHelper.findAll(updateMyPost.getContent()))
                    .append("这些是违禁词不允许发布！").toString();
            throw new RuntimeException(word);
        }
    }

    @Override
    public void deleteMyPost(Long postId) {
        communityMapper.deleteMyPost(postId,ThreadLocalUserIdUtil.getCurrentId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void comment(NewCommentDTO newCommentDTO) {
        if(SensitiveWordHelper.contains(newCommentDTO.getContent())){
            String string = new StringBuilder().append("包含违禁词")
                    .append(SensitiveWordHelper.findAll(newCommentDTO.getContent())).toString();
            throw new RuntimeException(string);
        }else {
            communityMapper.comment(newCommentDTO,ThreadLocalUserIdUtil.getCurrentId());
            communityMapper.addCommentNum(newCommentDTO.getPostId());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long id) {
        List<Long> integers = communityMapper.getAssociationComment(id);
        integers.add(id);
        communityMapper.deleteComment(integers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void switchLikeComment(Long commentId, Integer cancel) {
        //cancel=-1时为取消点赞
        //查看是否是新纪录
        Like like =  communityMapper.check(commentId,Like.TYPE_COMMENT,ThreadLocalUserIdUtil.getCurrentId());
        if(like == null){
            //新纪录一定是点赞 cancel=1
            communityMapper.addLike(Like.builder()
                    .userId(ThreadLocalUserIdUtil.getCurrentId())
                    .targetId(commentId)
                    .targetType(Like.TYPE_COMMENT)
                    .createTime(LocalDateTime.now())
                    .isCancel(1).build());

            communityMapper.likeComment(commentId,cancel);
        }else {
            //老记录则传cancel=1为点赞,-1为取消
            communityMapper.updateLike(commentId, ThreadLocalUserIdUtil.getCurrentId(), Like.TYPE_COMMENT, cancel);
            communityMapper.likeComment(commentId,cancel);
        }

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void switchLikePost(Long postId, Integer cancel) {
        //查看是否是新纪录
        Like like =  communityMapper.check(postId,Like.TYPE_POST,ThreadLocalUserIdUtil.getCurrentId());
        if(like == null){
            //新纪录一定是点赞 cancel=1
            communityMapper.addLike(Like.builder()
                    .userId(ThreadLocalUserIdUtil.getCurrentId())
                    .targetId(postId)
                    .targetType(Like.TYPE_POST)
                    .createTime(LocalDateTime.now())
                    .isCancel(1).build());
            communityMapper.likePost(postId,cancel);
        }else {
            //老记录则传cancel=1为点赞,-1为取消
            communityMapper.updateLike(postId, ThreadLocalUserIdUtil.getCurrentId(), Like.TYPE_POST, cancel);
            communityMapper.likePost(postId,cancel);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void switchCollect(Long id, Integer cancel) {
        //查看是否是新纪录
         Collect collect = communityMapper.checkCollect(id,ThreadLocalUserIdUtil.getCurrentId());
        //新纪录一定是点赞 cancel=1
         if (collect == null){
             communityMapper.addCollect(Collect.builder()
                     .userId(ThreadLocalUserIdUtil.getCurrentId())
                     .postId(id)
                     .createTime(LocalDateTime.now())
                     .isCancel(1).build());
             communityMapper.collectPost(id,cancel);
         }else {//老记录则传cancel=1为点赞,-1为取消
             communityMapper.updateCollect(id, ThreadLocalUserIdUtil.getCurrentId(), cancel);
             communityMapper.collectPost(id,cancel);
         }
    }

    @Override
    public List<MyCollectVO> getCollect() {
        try {
            List<Integer> postIdList = communityMapper.getCollect(ThreadLocalUserIdUtil.getCurrentId());
            return communityMapper.getPostListByPostId(postIdList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
