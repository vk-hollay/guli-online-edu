package com.hollay.order.service.impl;

import com.hollay.order.client.EduClient;
import com.hollay.order.client.UcenterClient;
import com.hollay.order.entity.Order;
import com.hollay.order.mapper.OrderMapper;
import com.hollay.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hollay.order.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author hollay
 * @since 2021-08-12
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    //1 生成订单的方法
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用根据用户id获取用户信息
        Map<String, Object> userInfoOrder = ucenterClient.getUserInfoOrder(memberId);

        //通过远程调用根据课程id获取课信息
        Map<String, Object> courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle((String) courseInfoOrder.get("title"));
        order.setCourseCover((String) courseInfoOrder.get("cover"));
        order.setTeacherName((String) courseInfoOrder.get("teacherName"));
        order.setTotalFee(BigDecimal.valueOf((Double) courseInfoOrder.get("price")));
        order.setMemberId(memberId);
        order.setMobile((String) userInfoOrder.get("mobile"));
        order.setNickname((String) userInfoOrder.get("nickname"));
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }
}
