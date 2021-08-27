package com.hollay.sms.utils;


import com.hollay.commonutils.RandomUtils;
import com.hollay.sms.entity.VerifyCode;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码生成工具类
 */
@Slf4j
public class VerifyCodeGenUtils {

    private static final String[] FONT_TYPES = { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66" };

    private static final int VALICATE_CODE_LENGTH = 4;

    /**
     * 填充验证码图片背景
     * @param graphics
     * @param width
     * @param height
     */
    private static void fillBackground(Graphics graphics, int width, int height) {
        // 填充背景,设定背景色
        //graphics.setColor(Color.WHITE);
        graphics.setColor(RandomUtils.randomColor(200, 250));
        //设置矩形坐标x y 为0
        graphics.fillRect(0, 0, width, height);

        // 加入干扰线条
        for (int i = 0; i < 10; i++) {
            //设置随机颜色算法参数
            graphics.setColor(RandomUtils.randomColor(40, 150));
            Random random = new Random();
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.drawLine(x, y, x1, y1);
        }
    }

    /**
     * 设置验证码字符颜色大小
     *
     * @param g
     * @param randomStr
     */
    private static void createCharacter(Graphics g, String randomStr, int width, int height) {
        char[] charArray = randomStr.toCharArray();
        int charlength = charArray.length;
        for (int i = 0; i < charlength; i++) {
            //设置RGB颜色算法参数
            g.setColor(new Color(50 + RandomUtils.nextInt(100),
                    50 + RandomUtils.nextInt(100), 50 + RandomUtils.nextInt(100)));
            //设置字体大小，类型
            g.setFont(new Font(FONT_TYPES[RandomUtils.nextInt(FONT_TYPES.length)], Font.BOLD, 28));
            //设置x y 坐标，x y坐标决定字符在整个背景中的位置
            g.drawString(String.valueOf(charArray[i]), 15*i+15, 19 + RandomUtils.nextInt(8)+8);
        }
    }

    /**
     * 生成验证码随机字符，并将生成的验证码图片写入输出流
     *
     * @param width
     * @param height
     * @param os
     * @return
     * @throws IOException
     */
    public static String generate(int width, int height, OutputStream os) throws IOException {
        // 在内存中创建图象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics graphics = image.getGraphics();
        fillBackground(graphics, width, height);
        String randomStr = RandomUtils.randomString(VALICATE_CODE_LENGTH);
        createCharacter(graphics, randomStr, width, height);
        graphics.dispose();
        //设置JPEG格式
        ImageIO.write(image, "JPEG", os);
        return randomStr;
    }

    /**
     * 生成验证码对象
     *
     * @param width
     * @param height
     * @return
     */
    public static VerifyCode generate(int width, int height) {
        VerifyCode verifyCode = null;
        try (
            //将流的初始化放到这里就不需要手动关闭流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
            String code = generate(width, height, baos);
            verifyCode = new VerifyCode();
            verifyCode.setCode(code);
            verifyCode.setImgBytes(baos.toByteArray());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            verifyCode = null;
        }
        return verifyCode;
    }


}

