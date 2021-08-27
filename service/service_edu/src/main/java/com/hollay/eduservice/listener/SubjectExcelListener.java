package com.hollay.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hollay.eduservice.entity.EduSubject;
import com.hollay.eduservice.entity.excel.SubjectData;
import com.hollay.eduservice.service.EduSubjectService;
import com.hollay.servicebase.exception.MyException;

import java.util.Map;

/**
 * Excel读操作监听器
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService subjectService;

    public SubjectExcelListener() {}

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取excel内容，一行一行进行读取，存进数据库
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new MyException(20001, "文件数据为空");
        }
        //一行一行读取，每次读取都有两个值，第一个值一级分类，第二个值二级分类
        //添加一级分类
        //判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null) { //没有相同一级分类，进行添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0").setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }
        //获取一级分类Id值(主键回填)
        String pid = existOneSubject.getId();
        //添加二级分类
        //判断二级分类是否重复
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if (existTwoSubject == null) { //没有相同二级分类，进行添加
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid).setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }
    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息：" + headMap);
    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
