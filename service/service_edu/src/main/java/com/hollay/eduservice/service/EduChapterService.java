package com.hollay.eduservice.service;

import com.hollay.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hollay.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 */
public interface EduChapterService extends IService<EduChapter> {

    //获取所有章节小节
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //删除章节
    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
