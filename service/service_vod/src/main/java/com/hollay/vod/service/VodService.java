package com.hollay.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    //上传视频到阿里云，返回上传视频ID
    String uploadVideoAliyun(MultipartFile file);

    //根据视频id删除阿里云视频
    void removeAliyunVideo(String id);

    //根据视频id删除阿里云视频(删除多个视频)
    void removeMoreAliyunVideo(List<String> videoIdList);
}
