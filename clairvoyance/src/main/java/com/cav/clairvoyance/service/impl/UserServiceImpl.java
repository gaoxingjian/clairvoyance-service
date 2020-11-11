package com.cav.clairvoyance.service.impl;

import ch.qos.logback.core.util.TimeUtil;
import com.cav.clairvoyance.constants.Constant;
import com.cav.clairvoyance.domain.User;
import com.cav.clairvoyance.exception.BusinessException;
import com.cav.clairvoyance.exception.code.BaseResponseCode;
import com.cav.clairvoyance.mapper.UserMapper;
import com.cav.clairvoyance.service.RedisService;
import com.cav.clairvoyance.service.UserService;
import com.cav.clairvoyance.utils.JwtTokenUtil;
import com.cav.clairvoyance.utils.PasswordUtils;
import com.cav.clairvoyance.utils.UUIDUtil;
import com.cav.clairvoyance.vo.req.LoginReqVO;
import com.cav.clairvoyance.vo.req.RegisterReqVO;
import com.cav.clairvoyance.vo.resp.LoginRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;


    @Override
    public String register(RegisterReqVO vo) {
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        user.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(vo.getPassword(), user.getSalt());
        user.setPassword(encode);
        user.setId(UUIDUtil.getUUIDEndWithTime());
        user.setCreateTime(new Date());
        int i = userMapper.insertSelective(user);
        if (i != 1) throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        return user.getId();
    }

    @Override
    public LoginRespVO login(LoginReqVO vo) {
        User loginUser = new User();
        loginUser.setUsername(vo.getUsername());
        List<User> userList = userMapper.select(loginUser);
        if (null == userList) throw new BusinessException(BaseResponseCode.NOT_ACCOUNT);
        User user = userList.get(0);
        if (!PasswordUtils.matches(user.getSalt(), vo.getPassword(), user.getPassword()))
            throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
        LoginRespVO respVO = new LoginRespVO();
        BeanUtils.copyProperties(user, respVO);
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.JWT_USER_NAME, user.getUsername());
        String accessToken = JwtTokenUtil.getAccessToken(user.getId(), claims);
        String refreshToken = JwtTokenUtil.getRefreshToken(user.getId(), claims);
        respVO.setAccessToken(accessToken);
        respVO.setRefreshToken(refreshToken);
        return respVO;
    }

    @Override
    public void logout(String accessToken, String refreshToken) {

        if(StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(refreshToken)) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        log.info("subject.getPrincipals()={}",subject.getPrincipals());
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        String userId = JwtTokenUtil.getUserId(accessToken);
        //redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST+accessToken,userId,JwtTokenUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
        //redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST+refreshToken,userId,JwtTokenUtil.getRemainingTime(refreshToken), TimeUnit.MILLISECONDS);
        //redisService.delete(Constant.IDENTIFY_CACHE_KEY+userId);
    }


}
