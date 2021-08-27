package com.hollay.eduservice.controller.front;


import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hollay.commonutils.JwtUtils;
import com.hollay.commonutils.R;
import com.hollay.eduservice.client.OrderClient;
import com.hollay.eduservice.entity.EduCourse;
import com.hollay.eduservice.entity.chapter.ChapterVo;
import com.hollay.eduservice.entity.frontVo.CourseFrontVo;
import com.hollay.eduservice.entity.frontVo.CourseWebVo;
import com.hollay.eduservice.service.EduChapterService;
import com.hollay.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    //1 条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2 课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        //根据课程id和用户id查询当前课程是否已经支付过了
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println(memberId);
        boolean buyCourse = false;
        if (!StringUtils.isEmpty(memberId)) {
            buyCourse = orderClient.isBuyCourse(courseId, memberId);
        }
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy", buyCourse);
    }

    //根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{courseId}")
    public Map<String, Object> getCourseInfoOrder(@PathVariable String courseId) {
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        Map<String, Object> resultMap = BeanUtils.beanToMap(courseWebVo);
        System.out.println(resultMap);
        return resultMap;
    }
}












