package com.hollay.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hollay.eduservice.entity.EduSubject;
import com.hollay.eduservice.entity.excel.SubjectData;
import com.hollay.eduservice.entity.subject.OneSubject;
import com.hollay.eduservice.entity.subject.TwoSubject;
import com.hollay.eduservice.listener.SubjectExcelListener;
import com.hollay.eduservice.mapper.EduSubjectMapper;
import com.hollay.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    //调用easyexcel读取上传的excel文件
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        //获取文件输入流
        InputStream in = null;
        try {
            in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取所有一二级分类课程（树形结构）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //获取所有一级分类课程
        List<EduSubject> oneSubjectList = baseMapper.selectList(new QueryWrapper<EduSubject>().eq("parent_id", 0));
        //获取所有二级分类课程
        List<EduSubject> twoSubjectList = baseMapper.selectList(new QueryWrapper<EduSubject>().ne("parent_id", 0));
        //创建存储最终结果的list列表
        ArrayList<OneSubject> finalAllSubjectList = new ArrayList<>();
        //创建key为一级分类id，value为其对应的二级分类列表(parent_id=key)的hashmap
        HashMap<String, ArrayList<TwoSubject>> twoSubjectMap = new HashMap<>();
        //遍历一级分类课程列表
        for (EduSubject oneSubject: oneSubjectList) {
            //将oneSubject对应的属性复制到finalSubject
            OneSubject finalSubject1 = new OneSubject();
            BeanUtils.copyProperties(oneSubject, finalSubject1);
            //将一级分类课程添加到最终结果列表
            finalAllSubjectList.add(finalSubject1);
            //将一级分类id put到map中
            twoSubjectMap.put(oneSubject.getId(), new ArrayList<TwoSubject>());
        }
        //遍历二级分类课程列表
        for (EduSubject twoSubject: twoSubjectList) {
            TwoSubject finalSubject2 = new TwoSubject();
            BeanUtils.copyProperties(twoSubject, finalSubject2);
            //将二级分类课程添加到对应的一级分类里
            twoSubjectMap.get(twoSubject.getParentId()).add(finalSubject2);
        }
        //遍历最终结果列表，将每个一级分类对应的二级分类列表set进去
        for (OneSubject subject: finalAllSubjectList) {
            subject.setChildren(twoSubjectMap.get(subject.getId()));
        }
        return finalAllSubjectList;
    }
}
