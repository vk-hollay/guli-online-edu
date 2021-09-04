package com.hollay.eduservice.controller;


import com.hollay.commonutils.R;
import com.hollay.eduservice.entity.subject.OneSubject;
import com.hollay.eduservice.service.EduSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来的文件，把文件内容读取出来
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
//        System.out.println(file.getOriginalFilename());
//        System.out.println(file.getContentType());
        if ("application/vnd.ms-excel".equals(file.getContentType())) {
            subjectService.saveSubject(file, subjectService);
            return R.ok();
        }
        return R.error();
    }

    //获取所有课程分类（树形结构）
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }
}

