package com.hollay.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Component //防止在其他位置注入VodClient时idea报错
@FeignClient("service-edu")
public interface EduClient {

    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{courseId}")
    public Map<String, Object> getCourseInfoOrder(@PathVariable("courseId") String courseId);
    //注意：@PathVariable注解一定要指定参数名称，否则出错
}
