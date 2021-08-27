package com.hollay.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CourseQuery implements Serializable  {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称,模糊查询")
    private String title;

    @ApiModelProperty(value = "课程状态,Normal发布 Draft未发布")
    private String status;
}
