package com.hollay.eduservice.client;

import com.hollay.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class) //用于指定从哪个服务中调用功能 ，名称与被调用的服务名保持一致
@Component //防止在其他位置注入VodClient时idea报错
public interface VodClient {

    /*
        定义调用方法的路径
     */
    //根据视频id删除阿里云视频
    @DeleteMapping("/eduvod/video/removeAliyunVideo/{videoId}")
    public R removeAliyunVideo(@PathVariable("videoId") String videoId); //注意：@PathVariable注解一定要指定参数名称，否则出错

    //删除多个阿里云视频的方法 （参数多个视频id）
    @DeleteMapping("/eduvod/video/batch-removeAliyunVideo")
    public R batchRemoveAliyunVideo(@RequestParam("videoIdList") List<String> videoIdList);
}
