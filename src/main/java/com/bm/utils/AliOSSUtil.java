package com.bm.utils;

import com.aliyun.oss.OSSClient;

import java.io.File;
import java.util.UUID;

/**
 * 阿里云OOS上传文件工具类
 */
public class AliOSSUtil {
    /**
     * 将本地file上传到阿里云指定域名的目录下，并用UUID重命名
     * @param file
     * @return String
     */
    public static String ossUpload(File file) {

        String bucketDomain = "https://student-manager1.oss-cn-beijing.aliyuncs.com/";
        //Endpoint以北京为例，其它Region请按实际情况填写
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        //accessKeyId和accessKeySecret，请填写自己的
        String accessKeyId = "LTAI71hvh5becO5o";
        String accessKeySecret = "7PJSsy9BrZk9dVf7eByIt6xP6mrynS";
        //Bucket名称
        String bucketName = "student-manager1";
        //目录名称
        String filedir = "logo/";
        //获得本地文件的文件名
        String fileName = file.getName();
        //上传后的文件名生成，这里会将客户端的文件保留扩展名，主文件名用UUID生成
        String fileKey = UUID.randomUUID().toString() + fileName.substring(fileName.indexOf("."));
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传file到bucketName的filedir目录下，用fileKey作为新文件名存储
        ossClient.putObject(bucketName, filedir + fileKey, file);
        //创建一个可变长字符串变量，用来存放上传文件的URL
        StringBuffer url = new StringBuffer();
        //选择一个本地文件D:/bg.jpg上传到OSS后，应该生成这样的外链
        //https://student-manage.oss-cn-beijing.aliyuncs.com/logo/826ff621-5543-4485-a4a0-fc5c0cec8d54.jpg
        //域名+目录+新文件名
        url.append(bucketDomain).append(filedir).append(fileKey);
        ossClient.shutdown();
        return url.toString();
    }

    public static void main(String[] args) {
        File file = new File("D:/7.png");
        String url = ossUpload(file);
        System.out.println(url);
    }
}