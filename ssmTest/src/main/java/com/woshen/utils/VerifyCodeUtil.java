package com.woshen.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/10/17 17:54
 * @Version: 1.0.0
 * @Description: 生成图形验证码的工具类
 */
public class VerifyCodeUtil {

    private VerifyCodeUtil(){}


    public static ByteArrayOutputStream createImg(String code) throws IOException {
      return  createImg(code,250,100);
    }

    public static ByteArrayOutputStream createImg(String code,int width,int height) throws IOException {
        int verifySize = code.length();
        BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA,
                Color.ORANGE, Color.PINK, Color.YELLOW };
        float[] fractions = new float[colors.length];
        Random rand = new Random();
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0,0,width,height);
        Color c = getRandColor(200, 250);
        graphics.setColor(c);// 设置背景色
        graphics.fillRect(0, 2, width, height - 4);

        // 绘制干扰线
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Color randColor = getRandColor(0, 120);
            graphics.setColor(randColor);// 设置线条的颜色
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            graphics.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int rgb = getRandomIntColor();
            bufferedImage.setRGB(x, y, rgb);
        }

        shear(graphics, width, height, c);// 使图片扭曲


        //int fontSize = height - 4;

        char[] chars = code.toCharArray();
        for (int i = 0; i < verifySize; i++) {
            graphics.setColor(getRandColor(0,120));
            int randomFontSize = getRandomFontSize(height);
            Font font = new Font("Algerian", Font.ITALIC, randomFontSize);
            graphics.setFont(font);
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1),
                    (width / verifySize) * i + randomFontSize / 2, height / 2);
            graphics.setTransform(affine);
            int w = ((width - 10) / verifySize) * i + random.nextInt(20);
            graphics.drawChars(chars, i, 1, w, height / 2 + randomFontSize / 2 - 10);
            //graphics.drawString(String.valueOf(chars[i]),w,height / 2 + randomFontSize / 2 - 10);
        }

        graphics.dispose();
        ByteArrayOutputStream os= null;
        try {
            os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", os);
            os.flush();
            return os;
        }finally {
            if(os != null) {
                os.close();
            }
        }
    }

    /**
     * 在一定范围内随机生成颜色值
     *
     * @param fc
     * @param bc
     * @return
     */
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 随机生成颜色值
     *
     * @return
     */
    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    /**
     * 随机生成rgb值
     *
     * @return
     */
    private static int[] getRandomRgb() {
        Random random = new Random();
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    /**
     * 使图片扭曲
     *
     * @param g
     * @param w1
     * @param h1
     * @param color
     */
    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }
    /**
     * X方向扭曲
     *
     * @param g
     * @param w1
     * @param h1
     * @param color
     */
    private static void shearX(Graphics g, int w1, int h1, Color color) {
        Random random = new Random();
        int period = random.nextInt(2);
        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);
        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }


    /**
     * Y方向扭曲
     *
     * @param g
     * @param w1
     * @param h1
     * @param color
     */
    private static void shearY(Graphics g, int w1, int h1, Color color) {
        Random random = new Random();
        int period = random.nextInt(40) + 10; // 50;
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }

    /**
     * 随机字体大小
     * @param height
     * @return
     */
    private static int getRandomFontSize(int height){
        if(height < 32){
            return 28;
        }
        Random random = new Random();
        int i = random.nextInt(height - 28) + 24;
        return i;
    }
}
