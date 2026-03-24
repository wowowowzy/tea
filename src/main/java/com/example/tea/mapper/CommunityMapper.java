package com.example.tea.mapper;

import com.example.tea.entity.dto.Community.*;
import com.example.tea.entity.pojo.Community.Collect;
import com.example.tea.entity.pojo.Community.Like;
import com.example.tea.entity.pojo.Community.Post;
import com.example.tea.entity.vo.Community.MyCollectVO;
import com.example.tea.entity.vo.Community.MyPostVO;
import com.example.tea.entity.vo.Community.PostListPageVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 帖子Mapper
 */
@Mapper
public interface CommunityMapper {
    /**
     * 新增帖子
     */
    Long insert(Post post);

    /**
     * 根据ID查询帖子
     */
    PostWithUsernameDTO selectById(Long postId);

    @Select("select t.*,u.username,u.avatar as userImage from t_comment t left join sys_user u on t.user_id = u.id where post_id = #{postId}")
    List<CommentDTO> getCommentByPostId(Long postId);

    /**
     * 查询帖子总数
     */
    Long selectPostTotal();

    Page<PostListPageVO> getPostList(CommunityQueryDTO dto);

    List<MyPostVO> getMyPost(Long userId);

    void updateMyPost(UpdateMyPostDTO updateMyPost);

    @Delete("delete from t_post where id=#{postId} and user_id=#{currentId}")
    void deleteMyPost(Long postId, Long currentId);

    void comment(@Param(value = "dto") NewCommentDTO newCommentDTO,@Param("userId") Long userId);

    List<Long> getAssociationComment(Long id);

    void deleteComment(List<Long> list);

    @Update("UPDATE t_post SET comment_count = comment_count + 1 where id=#{postId}")
    void addCommentNum(Long postId);

    @Update("UPDATE t_post SET collect_count = collect_count + #{cancel} where id=#{id} ")
    void collectPost(Long id,Integer cancel);

    @Update("UPDATE t_comment SET like_count = like_count + #{cancel} where id=#{id} ")
    void likeComment(Long id,Integer cancel);

    @Select("select * from like_record where user_id=#{userId} and target_type=#{type} and target_id=#{id}")
    Like check(Long id, Integer type, Long userId);

    void addLike(Like build);

    void updateLike(Long id, Long userId, Integer type,Integer cancel);

    @Update("UPDATE t_post SET like_count = like_count + #{cancel} where id=#{id} ")
    void likePost(Long id,Integer cancel);

    @Update("update t_post set image =#{filePath} where id=#{postId}")
    void addImage(String filePath, Long postId);

    @Select("select * from collect_record where user_id= #{currentId} and post_id= #{id}")
    Collect checkCollect(Long id, Long currentId);

    @Insert("insert into collect_record(user_id, post_id, is_cancel, create_time) values (#{userId},#{postId},#{isCancel},#{createTime})")
    void addCollect(Collect build);


    @Update("update collect_record set is_cancel = #{cancel} ,create_time = now() where post_id= #{id} and user_id= #{currentId}")
    void updateCollect(Long id, Long currentId, Integer cancel);

    @Select("select post_id from collect_record where user_id= #{currentId} and is_cancel = 1")
    List<Integer> getCollect(Long currentId);

    List<MyCollectVO> getPostListByPostId(List<Integer> list);


    isCancelDTO getCancel(Long currentId, Long postId);
}
