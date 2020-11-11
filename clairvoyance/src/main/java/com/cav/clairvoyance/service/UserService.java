package com.cav.clairvoyance.service;

import com.cav.clairvoyance.vo.req.LoginReqVO;
import com.cav.clairvoyance.vo.req.RegisterReqVO;
import com.cav.clairvoyance.vo.resp.LoginRespVO;

public interface UserService {

    String register(RegisterReqVO vo);

    LoginRespVO login(LoginReqVO vo);

    void logout(String accessToken, String refreshToken);

}
