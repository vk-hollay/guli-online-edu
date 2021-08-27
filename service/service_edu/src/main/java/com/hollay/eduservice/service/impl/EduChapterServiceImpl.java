package com.hollay.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hollay.eduservice.entity.EduChapter;
import com.hollay.eduservice.entity.EduVideo;
import com.hollay.eduservice.entity.chapter.ChapterVo;
import com.hollay.eduservice.entity.chapter.VideoVo;
import com.hollay.eduservice.mapper.EduChapterMapper;
import com.hollay.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hollay.eduservice.service.EduVideoService;
import com.hollay.servicebase.exception.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hollay
 * @since 2021-07-20
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;//注入小节service

/*    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //1 根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2 根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //创建list集合，用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3 遍历查询章节list集合进行封装
        //遍历查询章节list集合
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //eduChapter对象值复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把chapterVo放到最终list集合
            finalList.add(chapterVo);

            //创建集合，用于封装章节的小节
            List<VideoVo> videoList = new ArrayList<>();

            //4 遍历查询小节list集合，进行封装
            for (int m = 0; m < eduVideoList.size(); m++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
                //判断：小节里面chapterid和章节里面id是否一样
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //放到小节封装集合
                    videoList.add(videoVo);
                }
            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }*/

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1 根据课程id查询课程里面所有的章节
        List<EduChapter> eduChapterList = baseMapper.selectList(new QueryWrapper<EduChapter>().eq("course_id", courseId));
        //2 根据课程id查询课程里面所有的小节
        List<EduVideo> eduVideoList = videoService.list(new QueryWrapper<EduVideo>().eq("course_id", courseId));

        List<ChapterVo> chapterVoList = eduChapterList.parallelStream()
                .map(eduChapter -> {
                        ChapterVo chapterVo = new ChapterVo();
                        BeanUtils.copyProperties(eduChapter, chapterVo);
                        return chapterVo;
                })
                .peek(chapterVo -> chapterVo.setChildren(eduVideoList.parallelStream()
                        .filter(eduVideo -> eduVideo.getChapterId().equals(chapterVo.getId()))
                        .map(eduVideo -> {
                            VideoVo videoVo = new VideoVo();
                            BeanUtils.copyProperties(eduVideo, videoVo);
                            return videoVo;
                        })
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());

//        chapterVoList.forEach(System.out::println);
        return chapterVoList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        int count = videoService.count(new QueryWrapper<EduVideo>().eq("chapter_id", chapterId));
        if (count > 0) { //章节中有小节，不进行删除
            throw new MyException(20001, "章节中包含小节，不能删除");
        } else { //章节中无小节，可以进行删除
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    //2 根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
