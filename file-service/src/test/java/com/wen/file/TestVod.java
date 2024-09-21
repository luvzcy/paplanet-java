//package com.wen.file;
//
//import com.aliyun.vod.upload.impl.UploadVideoImpl;
//import com.aliyun.vod.upload.req.UploadVideoRequest;
//import com.aliyun.vod.upload.resp.UploadVideoResponse;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.vod.model.v20170321.*;
//import io.lettuce.core.ScriptOutputType;
//
//import java.util.List;
//
//public class TestVod {
//
//    public static void main(String[] args) throws ClientException {
//
//        uploadVideo();
//
//    }
//
//
//    // 1.根据视频id获取视频播放地址
//    public static void getPlayUrl() throws ClientException {
//        // 初始化对象
//        String accessId = "LTAI5tQHEjpLjQrreBFgeJib";
//        String accessKeySecret = "h7NH86mZ21dyrT4DCZOjQ5RhOyCYL4";
//        DefaultAcsClient client = InitObject.initVodClient(accessId, accessKeySecret);
//
//        // 创建获取视频地址request和response对象
//        GetPlayInfoRequest request = new GetPlayInfoRequest();
//        GetPlayInfoResponse response = new GetPlayInfoResponse();
//
//        // 向request中设置视频id
//        request.setVideoId("4009376175b271ef9c845017f0f90102");
//
//        // 调用初始化对象里面的方法，传递request，获取数据
//        response = client.getAcsResponse(request);
//
//        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
//        //播放地址
//        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
//            System.out.println("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
//        }
//        // Base信息
//        System.out.println("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
//    }
//
//    // 2.根据视频id获取视频播放凭证
//    public static void getPlayAuth() throws ClientException {
//        // 初始化对象
//        String accessId = "LTAI5tQHEjpLjQrreBFgeJib";
//        String accessKeySecret = "h7NH86mZ21dyrT4DCZOjQ5RhOyCYL4";
//        DefaultAcsClient client = InitObject.initVodClient(accessId, accessKeySecret);
//
//        // 创建获取视频播放凭证request和response对象
//        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
//        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
//
//        // 向request中设置视频id
//        request.setVideoId("4009376175b271ef9c845017f0f90102");
//
//        // 调用初始化对象里面的方法得到凭证
//        response = client.getAcsResponse(request);
//
//        System.out.println("PlayAuth = " + response.getPlayAuth() + "\n");
//    }
//
//    // 本地上传视频的方法
//    public static void uploadVideo(){
//        String accessId = "LTAI5tQHEjpLjQrreBFgeJib";
//        String accessKeySecret = "h7NH86mZ21dyrT4DCZOjQ5RhOyCYL4";
//        String title = "test_video"; // 视频标题
//        String fileName = "C:/Users/wen/Downloads/乐高滑雪.mp4"; // 本地文件路径和名称
//        UploadVideoRequest request = new UploadVideoRequest(accessId, accessKeySecret, title, fileName);
//        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
//        request.setPartSize(2 * 1024 * 1024L);
//        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
//        request.setTaskNum(1);
//
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadVideoResponse response = uploader.uploadVideo(request);
//        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
//        if (response.isSuccess()) {
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//        } else {
//            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//            System.out.print("ErrorCode=" + response.getCode() + "\n");
//            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
//        }
//
//    }
//}
