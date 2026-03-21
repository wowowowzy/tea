package com.example.tea.controller.user;

import com.example.tea.entity.dto.Community.CommunityQueryDTO;
import com.example.tea.entity.dto.Community.NewCommentDTO;
import com.example.tea.entity.dto.Community.PostCreateDTO;
import com.example.tea.entity.dto.Community.UpdateDTO;
import com.example.tea.entity.pojo.Result;
import com.example.tea.entity.vo.Community.PostDetailVO;
import com.example.tea.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    /**
     * 发布新帖子
     *
     * @param dto
     * @return
     */
    @PostMapping("createPost")
    public Result createPost(@RequestBody PostCreateDTO dto) {
        try {
            communityService.createPost(dto);
            return Result.success("发布成功");
        } catch (Exception e) {
            return Result.error("发布失败：" + e.getMessage());
        }
    }

    /**
     * 查询单篇帖子详情
     *
     * @return
     */
    @GetMapping("/getPostDetail")
    public Result getPostDetail(Long postId) {
        try {
            PostDetailVO detailVO = communityService.getPostDetail(postId);
            return Result.success(detailVO);
        } catch (Exception e) {
            return Result.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 分页查询帖子
     *
     * @return
     */
    @GetMapping("/getPostList")
    public Result getPostList(CommunityQueryDTO communityQueryDTO) {
        return Result.success(communityService.getPostList(communityQueryDTO));
    }

    /**
     * 查询个人帖子
     *
     * @return
     */
    @GetMapping("/getMyPost")
    public Result getMyPost() {
        return Result.success(communityService.getMyPost());
    }

    /**
     * 修改帖子
     *
     * @return
     */
    @PutMapping("updateMyPost")
    public Result updateMyPost(@RequestBody UpdateDTO updateDTO) {
        try {
            communityService.updateMyPost(updateDTO);
            return Result.success("发布成功");
        } catch (Exception e) {
            return Result.error("发布失败：" + e.getMessage());
        }
    }

    /**
     * 删帖
     *
     * @return
     */
    @DeleteMapping("/deleteMyPost")
    public Result deleteMyPost(Long postId) {
        communityService.deleteMyPost(postId);
        return Result.success();
    }

    /**
     * 评论
     *
     * @param newCommentDTO
     * @return
     */
    @PostMapping("comment")
    public Result comment(@RequestBody NewCommentDTO newCommentDTO) {
        try {
            communityService.comment(newCommentDTO);
            return Result.success("发布成功");
        } catch (Exception e) {
            return Result.error("发布失败：" + e.getMessage());
        }
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @DeleteMapping("/deleteComment")
    public Result deleteComment(@RequestParam("commentId") Long id){
        communityService.deleteComment(id);
        return Result.success();
    }

    /**
     * 点赞评论(cancel=1为点赞，-1为取消)
     * @param id
     * @return
     */
    @PostMapping("/switchLikeComment")
    public Result switchlikeComment(@RequestParam(value = "commentId") Long id,
                                    @RequestParam(value = "cancel",required = false,defaultValue = "1")Integer cancel){
        communityService.switchLikeComment(id,cancel);
        return Result.success();
    }

    /**
     * 点赞文章(cancel=1为点赞，-1为取消)
     * @param id
     * @return
     */
    @PostMapping("/switchLikePost")
    public Result switchLikePost(@RequestParam(value = "postId") Long id,
                                 @RequestParam(value = "cancel",required = false,defaultValue = "1")Integer cancel){
        communityService.switchLikePost(id,cancel);
        return Result.success();
    }
}
