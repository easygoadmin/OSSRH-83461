package com.v3ex.captcha;

import com.v3ex.captcha.base.ArithmeticCaptchaAbstract;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;

public class ArithmeticCaptcha extends ArithmeticCaptchaAbstract {
    public ArithmeticCaptcha() {
    }

    public ArithmeticCaptcha(int width, int height) {
        this();
        this.setWidth(width);
        this.setHeight(height);
    }

    public ArithmeticCaptcha(int width, int height, int len) {
        this(width, height);
        this.setLen(len);
    }

    public ArithmeticCaptcha(int width, int height, int len, Font font) {
        this(width, height, len);
        this.setFont(font);
    }

    public boolean out(OutputStream out) {
        this.checkAlpha();
        return this.graphicsImage(this.getArithmeticString().toCharArray(), out);
    }

    public String toBase64() {
        return this.toBase64("data:image/png;base64,");
    }

    private boolean graphicsImage(char[] strs, OutputStream out) {
        try {
            BufferedImage bi = new BufferedImage(this.width, this.height, 1);
            Graphics2D g2d = (Graphics2D) bi.getGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, this.width, this.height);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            this.drawOval(2, g2d);
            g2d.setFont(this.getFont());
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int fW = this.width / strs.length;
            int fSp = (fW - (int) fontMetrics.getStringBounds("8", g2d).getWidth()) / 2;

            for (int i = 0; i < strs.length; ++i) {
                g2d.setColor(this.color());
                int fY = this.height - (this.height - (int) fontMetrics.getStringBounds(String.valueOf(strs[i]), g2d).getHeight() >> 1);
                g2d.drawString(String.valueOf(strs[i]), i * fW + fSp + 3, fY - 3);
            }

            g2d.dispose();
            ImageIO.write(bi, "png", out);
            out.flush();
            boolean var20 = true;
            return var20;
        } catch (IOException var18) {
            var18.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException var17) {
                var17.printStackTrace();
            }

        }

        return false;
    }
}
