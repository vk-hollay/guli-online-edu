package com.hollay.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.hollay.commonutils.R;
import com.hollay.servicebase.annotation.MyUvLogAop;
import com.hollay.servicebase.exception.MyException;
import com.hollay.vod.service.VodService;
import com.hollay.vod.utils.AliyunKey;
import com.hollay.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file) {
        //返回上传视频ID
        String videoId = vodService.uploadVideoAliyun(file);
        return R.ok().data("videoId", videoId);
    }

    //根据视频id删除阿里云视频点播存储的视频
    @DeleteMapping("removeAliyunVideo/{videoId}")
    public R removeAliyunVideo(@PathVariable String videoId) {
        vodService.removeAliyunVideo(videoId);
        return R.ok();
    }

    //删除多个阿里云视频的方法 （参数多个视频id）
    @DeleteMapping("batch-removeAliyunVideo")
    public R batchRemoveAliyunVideo(@RequestParam("videoIdList")List<String> videoIdList) {
        vodService.removeMoreAliyunVideo(videoIdList);
        return R.ok();
    }

    //根据视频id获取视频凭证（根据视频凭证进行阿里云视频播放）
    @MyUvLogAop("play_video")
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(AliyunKey.accessKeyId, AliyunKey.accessKeySecret);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            throw new MyException(20001,"获取凭证失败");
        }
    }
}
