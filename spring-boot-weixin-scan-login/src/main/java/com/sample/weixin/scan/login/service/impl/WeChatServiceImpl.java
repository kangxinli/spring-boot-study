package com.sample.weixin.scan.login.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sample.weixin.scan.login.constant.Constant;
import com.sample.weixin.scan.login.protocol.LoginProtocol;
import com.sample.weixin.scan.login.service.WeChatService;
import com.sample.weixin.scan.login.util.DateUtil;
import com.sample.weixin.scan.login.util.MD5Utils;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {

    @Autowired
    private WxMpService wxMpService;

    @Value("${wx.open.config.redirectUrl}")
    private String wxRedirectUrl;

    @Value("${wx.open.config.csrfKey}")
    private String CSRF_KEY;

    @Override
    public String getQRCodeUrl() {
        // 生成 state 参数，用于防止 csrf
        String date = DateUtil.format(new Date(), "yyyyMMdd");
        String state = MD5Utils.generate(CSRF_KEY + date);
        return wxMpService.buildQrConnectUrl(wxRedirectUrl, Constant.WeChatLogin.SCOPE, state);
    }

    @Override
    public Boolean wxCallBack(LoginProtocol.WeChatQrCodeCallBack.Input input) {

        String code = input.getCode();
        String state = input.getState();

        if (code == null) {
            return false;
        }

        if (code != null && state != null) {
            // 验证 state,防止跨站请求伪造攻击
            String date = DateUtil.format(new Date(), "yyyyMMdd");
            Boolean isNotCsrf = MD5Utils.verify(CSRF_KEY + date, state);
            if (!isNotCsrf) {
                return false;
            }

            // 获取 openid
            try {

                WxMpOAuth2AccessToken accessToken =wxMpService.oauth2getAccessToken(code);
                String openid = accessToken.getOpenId();
                // String token = accessToken.getAccessToken();


                // 拿到 openid 后做自己的业务, 获取用 token 进一步获取用户信息
                log.info("开放平台用户openid:" + openid);

                // 用 access_token 获取用户的信息
                WxMpUser user = wxMpService.oauth2getUserInfo(accessToken, null);
                log.info("开放平台用户信息:" + user);
                
            } catch (WxErrorException e) {
                log.error(e.getMessage(), e);
            }

            return true;
        }
        return false;
    }
}
