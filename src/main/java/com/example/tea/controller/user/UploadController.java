package com.example.tea.controller.user;


import com.example.tea.entity.pojo.Result;
import com.example.tea.mapper.CommunityMapper;
import com.example.tea.mapper.UserMapper;
import com.example.tea.utils.AliOssUtil;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;
@RestController
@RequestMapping("/api/user")
public class UploadController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommunityMapper communityMapper;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String name = UUID.randomUUID().toString() + extension;
            String filePath = aliOssUtil.upload(file.getBytes(), name);
            userMapper.setAvatar(filePath, ThreadLocalUserIdUtil.getCurrentId());
            return Result.success(filePath);
        } catch (Exception e) {
            return Result.error("文件上传失败");
        }
    }

    @PostMapping("/uploadPostImage")
    public Result upload(MultipartFile file,Long postId){
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String name = UUID.randomUUID().toString() + extension;
            String filePath = aliOssUtil.upload(file.getBytes(), name);
            communityMapper.addImage(filePath,postId);
            return Result.success(filePath);
        } catch (Exception e) {
            return Result.error("文件上传失败");
        }
    }
}
