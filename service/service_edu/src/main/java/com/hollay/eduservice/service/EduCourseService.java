package com.hollay.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hollay.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hollay.eduservice.entity.frontVo.CourseFrontVo;
import com.hollay.eduservice.entity.frontVo.CourseWebVo;
import com.hollay.eduservice.entity.vo.CourseInfoVo;
import com.hollay.eduservice.entity.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    List<EduCourse> getHotCourses();

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
