package com.example.tea.service.impl;

import com.example.tea.entity.dto.Community.*;
import com.example.tea.entity.pojo.Community.Post;
import com.example.tea.entity.pojo.PageResult;
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
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityMapper communityMapper;

    /**
     * 发布新帖子
     */
    @Override
    public void createPost(PostCreateDTO dto) {
        if (!SensitiveWordHelper.contains(dto.getContent())
        &&!SensitiveWordHelper.contains(dto.getTitle())) {
            communityMapper.insert(Post.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .userId(ThreadLocalUserIdUtil.getCurrentId())
                    .build());
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
        List<CommentDTO> comments = communityMapper.getCommentByPostId(postId);

        if (post == null || post.getStatus() == 0) {
            throw new RuntimeException("帖子不存在或已删除");
        }
        PostDetailVO detailVO = PostDetailVO.builder().build();
        BeanUtils.copyProperties(post,detailVO);
        detailVO.setPostId(post.getId());
        detailVO.setCommentDTOS(comments);
        return detailVO;
    }

    @Override
    public PageResult getPostList(CommunityQueryDTO dto) {
        PageHelper.startPage(
                dto.getPage()==null ? 1 : dto.getPage(),
                dto.getPageSize()==null ? 10 : dto.getPageSize()
        );
        Page<PostListPageVO> page = communityMapper.getPostList(dto);
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


}
