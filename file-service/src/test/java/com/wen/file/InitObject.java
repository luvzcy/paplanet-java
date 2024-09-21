//package com.wen.file;
//
//
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.profile.DefaultProfile;
//
//public class InitObject {
//    //读取AccessKey信息
//    public static DefaultAcsClient initVodClient(String accessId, String accessKeySecret){
//        String regionId = "cn-shanghai";  // 点播服务接入地域
//        // 阿里云账号AccessKey拥有所有API的访问权限，建议您使用RAM用户进行API访问或日常运维。
//        // 强烈建议不要把AccessKey ID和AccessKey Secret保存到工程代码里，否则可能导致AccessKey泄露，威胁您账号下所有资源的安全。
//        // 本示例通过从环境变量中读取AccessKey，来实现API访问的身份验证。运行代码示例前，请配置环境变量ALIBABA_CLOUD_ACCESS_KEY_ID和ALIBABA_CLOUD_ACCESS_KEY_SECRET。
//        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessId, accessKeySecret);
//        return new DefaultAcsClient(profile);
//    }
//
//}
