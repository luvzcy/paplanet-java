package com.wen.file.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vdo")
public class VodController {

    private String accessKeyId = "LTAI5tQHEjpLjQrreBFgeJib";
    private String accessKeySecret = "h7NH86mZ21dyrT4DCZOjQ5RhOyCYL4";

    @CrossOrigin
    @GetMapping("/getUploadAuth")
    public Map<String, Object> getUploadAuth(@RequestParam("title") String title, @RequestParam("filename") String filename) {

        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(profile);
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle(title); // 视频标题
        request.setFileName(filename); // 上传文件名称

        try {
            CreateUploadVideoResponse response = defaultAcsClient.getAcsResponse(request);
            Map<String, Object> result = new HashMap<>();
            result.put("UploadAddress", response.getUploadAddress());
            result.put("UploadAuth", response.getUploadAuth());
            result.put("VideoId", response.getVideoId());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取上传凭证失败");
        }
    }

    //根据VideoId获取上传凭证
    @CrossOrigin
    @GetMapping("/getUploadAuthByVideoId")
    public Map<String, Object> getUploadAuthByVideoId(@RequestParam("videoId") String videoId) {
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(profile);
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setVideoId(videoId); // 设置视频ID

        try {
            RefreshUploadVideoResponse response = defaultAcsClient.getAcsResponse(request);
            Map<String, Object> result = new HashMap<>();
            result.put("UploadAddress", response.getUploadAddress());
            result.put("UploadAuth", response.getUploadAuth());
            result.put("VideoId", videoId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取上传凭证失败");
        }
    }

    @CrossOrigin
    @DeleteMapping("/deleteVideo")
    public Map<String, Object> deleteVideo(@RequestParam("videoId") String videoId) {
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(profile);
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(videoId); // 设置要删除的视频 ID

        try {
            DeleteVideoResponse response = defaultAcsClient.getAcsResponse(request);
            Map<String, Object> result = new HashMap<>();
            result.put("Message", "视频删除成功");
            result.put("VideoId", videoId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除视频失败");
        }
    }

}
