package com.happy.video.controller;


import cn.hutool.core.util.RandomUtil;
import com.happy.video.util.Constants;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class VcodeController {

    // 数字二维码字典
    static final char[] VCODE_ARRAY = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    // 生成验证码
    // 浏览器http://locahost:8080/vcode --> 图片
    // 验证码保存在session,不能保存在request
    @RequestMapping("/vcode")
    public void vcode(HttpServletResponse resp, HttpSession session) {


        BufferedImage image = new BufferedImage(80, 30, BufferedImage.TYPE_INT_RGB);
        Graphics2D gd = image.createGraphics();

        gd.setColor(Color.white);
        gd.drawRect(0, 0, 80, 30);

        Font font = new Font("宋体", Font.BOLD, 25);
        gd.setFont(font);


        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int ri = RandomUtil.randomInt(10);
            char v = VCODE_ARRAY[ri];

            // rgb 红绿蓝(255,255,255),(ff,ff,ff)
            gd.setColor(new Color(
                    RandomUtil.randomInt(255),
                    RandomUtil.randomInt(255),
                    RandomUtil.randomInt(255)
            ));

            // 画验证码
            gd.drawString("" + v, (i + 1) * 15, 25);

            builder.append(v);
        }

        // 保存验证码
        session.setAttribute(Constants.SESSION_VCODE, builder.toString());

        // 设置返回内容类型
        // http协议规定的数据类型
        // https://tool.oschina.net/commons
        resp.setContentType("image/jpeg");
        // 设置不缓存
        resp.setDateHeader("Expires", -1);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");

        // 输出图片
        ServletOutputStream ous = null;
        try {
            ous = resp.getOutputStream();

            ImageIO.write(image, "jpeg", ous);
            ous.flush();
            ous.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
