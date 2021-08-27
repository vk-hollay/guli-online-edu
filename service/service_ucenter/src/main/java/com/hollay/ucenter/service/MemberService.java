package com.hollay.ucenter.service;

import com.hollay.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hollay.ucenter.entity.Vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author hollay
 * @since 2021-08-08
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo, String ip);

    Member getMenberByOperid(String openid);

    Integer countRegisterDay(String day);
}
