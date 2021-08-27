package com.hollay.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Component //防止在其他位置注入VodClient时idea报错
@FeignClient("service-ucenter")
public interface UcenterClient {

    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    public Map<String, Object> getUserInfoOrder(@PathVariable("id") String id);
    //注意：@PathVariable注解一定要指定参数名称，否则出错
}
