package com.hollay.eduservice.client;

import com.hollay.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    /*
    出错之后会执行
     */
    @Override
    public R removeAliyunVideo(String videoId) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R batchRemoveAliyunVideo(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}
