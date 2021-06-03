package com.demo.util;


import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: ZhengTianLiang
 * @date: 2021/6/3  17:26
 * @desc: 图片加水印工具类
 */

public class ImageRemarkUtil {


    public static MultipartFile addWorkMarkToMutipartFile(MultipartFile multipartFile,
                                                          String text) throws IOException {
        // 获取图片文件名
        String originFileName = multipartFile.getOriginalFilename();
        // 获取原图片后缀
        int lastSplit = originFileName.lastIndexOf(".");
        String suffix = originFileName.substring(lastSplit + 1);
        // 获取图片原始信息
        String dOriginFileName = multipartFile.getOriginalFilename();
        String dContentType = multipartFile.getContentType();
        // 是图片且不是gif才加水印
        if (!suffix.equalsIgnoreCase("gif")) {
            // 获取水印图片
            InputStream inputImg = multipartFile.getInputStream();
            Image img = ImageIO.read(inputImg);
            // 加图片水印
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);

            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight,
                    BufferedImage.TYPE_INT_RGB);
            //设置字体
            Font font = new Font("宋体", Font.PLAIN, 35);
            //调用画文字水印的方法
            markWord(bufImg, img, text, font, Color.RED, 0, imgHeight - 50);

            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bufImg, suffix, imOut);
            InputStream is = new ByteArrayInputStream(bs.toByteArray());
            // 加水印后的文件上传
            multipartFile = new MockMultipartFile(dOriginFileName, dOriginFileName, dContentType,
                    is);
        }
        //返回加了水印的上传对象
        return multipartFile;
    }

    public static void markWord(BufferedImage bufImg, Image img, String text, Font font, Color color, int x, int y) {
        //取到画笔
        Graphics2D g = bufImg.createGraphics();
        //画底片
        g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
        g.setColor(color);
        g.setFont(font);
        //位置
        g.drawString(text, x, y);
        g.dispose();
    }
}
