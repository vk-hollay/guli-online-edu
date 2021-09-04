package com.hollay.eduservice.controller;


import com.hollay.commonutils.R;
import com.hollay.eduservice.client.VodClient;
import com.hollay.eduservice.entity.EduVideo;
import com.hollay.eduservice.service.EduVideoService;
import com.hollay.servicebase.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    //删除小节时候，同时把对应的阿里云视频删除
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id) {
        //根据小节id得到视频id
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //视频id不为空则可以执行删除视频
        if (!StringUtils.isEmpty(videoSourceId)) {
            //根据视频id远程调用service-vod服务实现阿里云存储的视频删除
            R result = vodClient.removeAliyunVideo(videoSourceId);
            if (result.getCode() == 20001) {
                throw new MyException(20001, result.getMessage());
            }
        }
        //删除小节
        videoService.removeById(id);
        return R.ok();
    }
}

