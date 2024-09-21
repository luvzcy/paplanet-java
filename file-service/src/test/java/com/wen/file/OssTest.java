//package com.wen.file;
//
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import com.aliyuncs.exceptions.ClientException;
//
//public class OssTest {
//
//    private static String accessKeyId = "LTAI5tQHEjpLjQrreBFgeJib";
//    private static String accessKeySecret = "h7NH86mZ21dyrT4DCZOjQ5RhOyCYL4";
//    private static String bucket = "hhjs-guangzhou";
//    private static String endpoint = "oss-cn-guangzhou.aliyuncs.com";
//
//    public static void main(String[] args) throws ClientException {
//
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        String objectName = "paplanet/2024-09-21/0d43248cbf903d3ce0cff3530055230.jpg";
//        try {
//            // 删除指定的文件
//            ossClient.deleteObject(bucket, objectName);
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            ossClient.shutdown();
//        }
//    }
//}
