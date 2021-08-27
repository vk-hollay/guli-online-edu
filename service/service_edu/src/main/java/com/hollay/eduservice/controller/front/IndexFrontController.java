package com.hollay.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hollay.commonutils.R;
import com.hollay.eduservice.entity.EduCourse;
import com.hollay.eduservice.entity.EduTeacher;
import com.hollay.eduservice.service.EduCourseService;
import com.hollay.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台首页
 */
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前8条热门课程，查询前4条名师
    @GetMapping("index")
    public R index() {
        List<EduCourse> eduList = courseService.getHotCourses();
        List<EduTeacher> teacherList = teacherService.getHotTeacher();
        return R.ok().data("eduList",eduList).data("teacherList",teacherList);
    }

}
