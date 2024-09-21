package com.wen.file.controller;

import cn.hutool.json.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.wen.common.domain.post.vo.MediaVO;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/oss")
public class OssController {

    private String accessKeyId = "LTAI5tQHEjpLjQrreBFgeJib";
    private String accessKeySecret = "h7NH86mZ21dyrT4DCZOjQ5RhOyCYL4";
    private String bucket = "hhjs-guangzhou";
    private String endpoint = "oss-cn-guangzhou.aliyuncs.com";

    @CrossOrigin
    @RequestMapping("/getOssPolicy")
    public Map<String,String> getOssPolicy(){ // 获取阿里云OSS的上传策略

        String host = "https://" + bucket + "." + endpoint; // 存储桶域名
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date()); // 格式化当前时间
        // 设置上传到OSS文件的前缀，可置空此项。置空后，文件将上传至Bucket的根目录下。
        String dir = "paplanet/"+format+"/";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("OSSAccessKeyId", accessKeyId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

//            System.out.println(new JSONObject(respMap).toStringPretty());
            return respMap;
        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return null;
    }

    @CrossOrigin
    @DeleteMapping("/delete")
    public String deleteFile(@RequestParam("url") String url) {
        System.out.println("Full URL: " + url);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 使用已定义的 extractObjectNameFromUrl 方法提取 objectName
            String objectName = extractObjectNameFromUrl(url);
            if (objectName != null) {
                // 删除指定的文件
                ossClient.deleteObject(bucket, objectName);
                System.out.println("Deleted object: " + objectName);
                return "File deleted successfully: " + objectName;
            } else {
                return "Invalid URL format: " + url;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "File deletion failed: " + e.getMessage();
        } finally {
            ossClient.shutdown();
        }
    }

    @CrossOrigin
    @DeleteMapping("/deleteFiles")
    public String deleteFiles(@RequestBody List<String> urls) {
        System.out.println("URLs to delete: " + urls);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            for (String url : urls) {
                // 复用 extractObjectNameFromUrl 方法提取 objectName
                String objectName = extractObjectNameFromUrl(url);
                if (objectName != null) {
                    // 删除指定的文件
                    ossClient.deleteObject(bucket, objectName);
                    System.out.println("Deleted object: " + objectName);
                } else {
                    System.out.println("Invalid URL format: " + url);
                    return "One or more URLs have an invalid format.";
                }
            }
            return "Files deleted successfully.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "File deletion failed: " + e.getMessage();
        } finally {
            ossClient.shutdown();
        }
    }

    // 提前定义的 extractObjectNameFromUrl 方法（只需定义一次）
    private String extractObjectNameFromUrl(String url) {
        try {
            java.net.URL netUrl = new java.net.URL(url);
            return netUrl.getPath().substring(1); // 去掉前导的 "/"
        } catch (Exception e) {
            System.out.println("Error extracting object name from URL: " + e.getMessage());
            return null;
        }
    }

}

