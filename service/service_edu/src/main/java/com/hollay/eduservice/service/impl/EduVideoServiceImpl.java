package com.hollay.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hollay.commonutils.R;
import com.hollay.eduservice.client.VodClient;
import com.hollay.eduservice.entity.EduVideo;
import com.hollay.eduservice.mapper.EduVideoMapper;
import com.hollay.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hollay.servicebase.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author hollay
 * @since 2021-07-20
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    //1 根据课程id删除小节
    //删除小节，删除对应视频文件
    @Override
    public void removeVideoByCourseId(String courseId) {

        //根据课程id查询课程所有的视频id, 进行批量删除
        List<EduVideo> eduVideoList = baseMapper.selectList(new QueryWrapper<EduVideo>().eq("course_id", courseId));
        List<String> videoIdList = eduVideoList.stream().parallel()
                .map(EduVideo::getVideoSourceId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        baseMapper.delete(new QueryWrapper<EduVideo>().eq("course_id", courseId));

        if (videoIdList.size() > 0) {
            R result = vodClient.batchRemoveAliyunVideo(videoIdList);
            if (result.getCode() == 20001) {
                throw new MyException(20001, result.getMessage());
            }
        }


    }
}
