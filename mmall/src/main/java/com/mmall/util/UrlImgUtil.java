package com.mmall.util;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class UrlImgUtil {
    public static String encodeImgageToBase64(String remark) {

        ByteArrayOutputStream outputStream = null;

        try {

            URL url = new URL(remark);

            BufferedImage bufferedImage = ImageIO.read(url);

            outputStream = new ByteArrayOutputStream();

            ImageIO.write(bufferedImage,"jpg",outputStream);

        } catch (IOException e) {

            return remark;

        }

        BASE64Encoder encoder = new BASE64Encoder();

        String s= encoder.encode(outputStream.toByteArray());

        return s;

    }

    public static void main(String[] args) {
        String base64 = encodeImgageToBase64("https://cisjw-1256877329.cos.ap-beijing.myqcloud.com/education-upload/20181229/244954a64d004e7f8fe6b784636a061f.png");
        System.out.println(base64);
    }
}
