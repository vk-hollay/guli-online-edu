package com.hollay.eduservice.service;

import com.hollay.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hollay.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
