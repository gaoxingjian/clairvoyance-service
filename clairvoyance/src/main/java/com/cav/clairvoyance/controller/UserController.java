package com.cav.clairvoyance.controller;


import com.cav.clairvoyance.constants.Constant;
import com.cav.clairvoyance.domain.User;
import com.cav.clairvoyance.service.UserService;
import com.cav.clairvoyance.utils.DataResult;
import com.cav.clairvoyance.utils.JwtTokenUtil;
import com.cav.clairvoyance.vo.req.LoginReqVO;
import com.cav.clairvoyance.vo.req.RegisterReqVO;
import com.cav.clairvoyance.vo.resp.LoginRespVO;
import com.cav.clairvoyance.vo.resp.UserInfoRespVO;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Api(tags = "用户")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public DataResult<LoginRespVO> login(@RequestBody @Valid LoginReqVO vo) {
        DataResult<LoginRespVO> result = DataResult.success();
        result.setData(userService.login(vo));
        return result;
    }

    @PostMapping(value = "/register")
    public DataResult<String> register(@RequestBody @Valid RegisterReqVO vo) {
        DataResult<String> result = DataResult.success();
        result.setData(userService.register(vo));
        return result;
    }

    @GetMapping("/token")
    public DataResult<String> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader(Constant.REFRESH_TOKEN);
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        DataResult<String> result = DataResult.success();
        result.setData(userService.refreshToken(refreshToken, accessToken));
        return result;
    }

    @GetMapping(value = "")
    public DataResult<UserInfoRespVO> yourSelfInfo(HttpServletRequest request) {
        String id = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        DataResult<UserInfoRespVO> result = DataResult.success();
        User user = userService.detailInfo(id);
        UserInfoRespVO userInfoRespVO = new UserInfoRespVO();
        if (user != null) {
            BeanUtils.copyProperties(user, userInfoRespVO);
        }
        result.setData(userInfoRespVO);
        return result;
    }

    @GetMapping(value = "/logout")
    public DataResult logout(HttpServletRequest request) {
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        String refreshToken = request.getHeader(Constant.REFRESH_TOKEN);
        userService.logout(accessToken, refreshToken);
        return DataResult.success();
    }
}
