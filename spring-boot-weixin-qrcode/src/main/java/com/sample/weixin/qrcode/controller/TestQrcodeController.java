package com.sample.weixin.qrcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.weixin.qrcode.utils.WeChatQrcodeUtils;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;


/**
 * <pre>
 * @Desc: 测试生成带参数二维码,   2592000(有效期30天)
 * </pre>
 */
@Controller
@RequestMapping("/wechat/qrcode")
public class TestQrcodeController {
    @Autowired
    private WeChatQrcodeUtils weChatQrcodeUtils;
 
    /**
     * <pre>
     * @desc: 创建生成二维码
     * </pre>
     */
    @RequestMapping("/createQrcode")
    @ResponseBody
    public Object createQrcode(String expireSeconds, int sceneId) throws WxErrorException {
        WxMpQrCodeTicket wxMpQrCodeTicket = weChatQrcodeUtils.qrCodeCreateTmpTicket(sceneId, Integer.valueOf(expireSeconds));
        return wxMpQrCodeTicket;
    }
 
    /**
     * <pre>
     * @desc: 通过ticket获取二维码（长链接URL）
     * </pre>
     */
    @RequestMapping("/getQrcodeUrl")
    @ResponseBody
    public Object getQrcodeUrl(String ticket) throws WxErrorException {
        String url = weChatQrcodeUtils.qrCodePictureUrl(ticket);
        return url;
    }
 
    /**
     * <pre>
     * @desc: 通过ticket获取二维码（短链接URL）
     * </pre>
     */
    @RequestMapping("/qrCodePictureUrl")
    @ResponseBody
    public Object qrCodePictureUrl(String ticket) throws WxErrorException {
        String urlPicture = weChatQrcodeUtils.qrCodePictureUrl(ticket,true);
       // String url= urlPicture.replace("\\/", "/");
        return urlPicture;
    }
}
