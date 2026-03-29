package com.example.tea.service;

import com.example.tea.entity.dto.Community.*;
import com.example.tea.entity.pojo.PageResult;
import com.example.tea.entity.vo.Community.MyCollectVO;
import com.example.tea.entity.vo.Community.MyPostVO;
import com.example.tea.entity.vo.Community.PostDetailVO;

import java.util.List;

public interface CommunityService {
    /**
     * 发布新帖子
     */
    Long createPost(PostCreateDTO dto);

    /**
     * 查询帖子详情
     */
    PostDetailVO getPostDetail(Long postId);

    PageResult getPostList(CommunityQueryDTO communityQueryDTO);

    List<MyPostVO> getMyPost();

    void updateMyPost(UpdateDTO updateMyPost);

    void deleteMyPost(Long postId);

    void comment(NewCommentDTO newCommentDTO);

    void deleteComment(Long id);

    void switchLikeComment(Long id,Integer cancel);

    void switchLikePost(Long id,Integer cancel);

    void switchCollect(Long id, Integer cancel);

    List<MyCollectVO> getCollect();

    List<MaybeLikeDTO> maybeLike();
}
