package com.hollay.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hollay.commonutils.R;
import com.hollay.eduservice.entity.EduTeacher;
import com.hollay.eduservice.entity.vo.TeacherQuery;
import com.hollay.eduservice.service.EduTeacherService;
import com.hollay.servicebase.exception.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author hollay
 * @since 2021-05-07
 */

@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //rest风格
    //查询讲师表所有数据
    @GetMapping("findAll")
    @ApiOperation(value = "所有讲师列表")
    public R findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("teacherList", list);
    }

    //逻辑删除讲师
    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "逻辑删除讲师")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师Id", required = true) @PathVariable String id) {
        boolean b = eduTeacherService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return  R.error();
        }
    }

    //分页查询讲师, current当前页， limit每页记录数
    @GetMapping("pageTeacher/{current}/{limit}")
    @ApiOperation(value = "分页查询讲师")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        try {
            int i = 10/0;
        } catch (Exception e) {
            throw new MyException(20001, "执行自定义异常处理...");
        }

        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        eduTeacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合

        //return R.ok().data("total", total).data("rows", records);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", total);
        hashMap.put("rows", records);
        return R.ok().data(hashMap);
    }

    //条件查询带分页   current当前页， limit每页记录数, teacherQuery为查询条件
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    @ApiOperation(value = "条件查询带分页")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询 (mybatis可用动态sql实现)
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin); //ge >=
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);//le <=
        }
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        eduTeacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合
        return R.ok().data("total", total).data("rows", records);
    }

    //添加讲师
    @PostMapping("addTeacher")
    @ApiOperation(value = "添加讲师")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        return save ? R.ok() : R.error();
    }

    //根据讲师id进行查询
    @GetMapping("getTeacher/{id}")
    @ApiOperation(value = "根据讲师id进行查询")
    public R getTeacher(@PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    //讲师修改功能
    @PostMapping("updateTeacher")
    @ApiOperation(value = "根据ID修改讲师")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.updateById(eduTeacher);
        return b ? R.ok() : R.error();
    }
}

