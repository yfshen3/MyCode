package org.lf.admin.api.baseapi.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: XuYang
 * @Description:
 * @Date: Create in 14:37 2018/9/4
 */
public class QrCode {

    private static final Logger LOGGER = LoggerFactory.getLogger(QrCode.class);
    private static MultiFormatWriter mutiWriter = new MultiFormatWriter();
    private static final String PICTURE_FORMAT = "png";

    public static String getQrCode(String content) {
        try {
            if (content == null || "".equals(content.trim())) {
                return content;
            }
            BufferedImage bufferedImage = genBarcode(content, 512, 512);
            return "data:image/png;base64," + convertBufferedImageToBase64(bufferedImage);
        } catch (Exception e) {
            LOGGER.error("执行QrCode.getQrCode()方法,发生异常=> ", e);
            return content;
        }
    }

    private static BufferedImage genBarcode(String content, int width, int height)
            throws WriterException, IOException {
        Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hint);
        int[] pixels = new int[width * height];
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                Color color = new Color(0,0, 0);
                int colorInt = color.getRGB();
                pixels[y * width + x] = matrix.get(x, y) ? colorInt : 16777215;
            }
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, width, height, pixels);
        return image;
    }

    private static String convertBufferedImageToBase64(BufferedImage bufferedImage) {
        LOGGER.info("执行QrCode.convertBufferedImageToBase64()方法, bufferedImage:{}", bufferedImage.toString());
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, PICTURE_FORMAT, bos);
            byte[] imageBytes = bos.toByteArray();
            imageString = Base64.getEncoder().encodeToString(imageBytes).trim();
            bos.close();
        } catch (IOException e) {
            LOGGER.error("执行QrCode.convertBufferedImageToBase64()方法,发生异常=> ", e);
            return imageString;
        }
        return imageString;
    }
}
