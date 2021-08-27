package com.hollay.ucenter.mapper;

import com.hollay.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author hollay
 * @since 2021-08-08
 */
public interface MemberMapper extends BaseMapper<Member> {

    //查询某一天注册人数
    Integer countRegisterDay(String day);
}
