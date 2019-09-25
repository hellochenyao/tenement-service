package com.katana.tenement.web.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.web.app.api.wxCode.RequestWXCodePost;
import com.katana.tenement.web.app.api.wxCode.ResponseWXCodePost;
import com.katana.wx.user.conn.UserConnection;
import com.katana.wx.weapp.qrcode.conn.WeappQrcodeConn;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.Random;

@RestController
@RequestMapping(value = "/app/wx-code/{userId}")
@Api(tags = "APP-获取二维码模块")
public class WXCodeController {

    @Value("${weapp.appid}")
    private String appid;

    @Value("${weapp.secret}")
    private String secret;

    @ApiOperation(value = "获取小程序二维码")
    @PostMapping(value = "/getCode")
    public ResponseWXCodePost getCode(@RequestBody RequestWXCodePost code, HttpServletRequest request) {
        String png_base64="";
        try {
            String accessTokenStr = UserConnection.getAccessToken(appid,secret);
            JSONObject accessToken = JSONObject.parseObject(accessTokenStr);
            BufferedImage codeImage = WeappQrcodeConn.createWxaqrcode(accessToken.get("access_token").toString(),code.getPath(),code.getWidth());
            BufferedImage subImage = codeImage.getSubimage(0, 0, codeImage.getWidth(), codeImage.getHeight());
            BufferedImage inputbig = new BufferedImage(430, 430, BufferedImage.TYPE_INT_BGR);
            ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
            Graphics2D g = (Graphics2D) inputbig.getGraphics();
            g.drawImage(subImage, 0, 0,430,430,null); //画图
            g.dispose();
            inputbig.flush();
            ImageIO.write(inputbig, "jpg",byteArrayStream);
            byte[] bytes = byteArrayStream.toByteArray();//转换成字节
            BASE64Encoder encoder = new BASE64Encoder();
            png_base64 =  encoder.encodeBuffer(bytes).trim();//转换成base64串
            png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("RECEIVE_ERROR","获取失败!");
        }
        ResponseWXCodePost response = new ResponseWXCodePost();
        response.setCodeBase(png_base64);
        int randomNumber = new Random().nextInt(2);
        StringBuilder back = new StringBuilder("back/bg");
        back.append(randomNumber+".png");
        response.setBackUrl(back.toString());
        response.setHeadUrl("back/head.png");
        return response;
    }
}
