package com.katana.tenement.framework.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author admin
 * @date 2018/1/22
 */
public class ImageUtils {
    /**
     * 按倍率缩小图片
     *
     * @param srcImagePath 读取图片路径
     * @param toImagePath  写入图片路径
     * @param ratio        缩小比率  宽、高一起等比率缩小
     * @throws IOException
     */
    public static void reduceImage(String srcImagePath, String toImagePath, int ratio) throws IOException {
        FileOutputStream out = null;
        try {
            //读入文件
            File file = new File(srcImagePath);
            // 构造Image对象
            BufferedImage src = ImageIO.read(file);
            int width = src.getWidth();
            int height = src.getHeight();
            BufferedImage tag = null;
            if (ratio != 0) {
                tag = new BufferedImage(width / ratio, height / ratio, BufferedImage.TYPE_INT_RGB);
                tag.getGraphics().drawImage(src, 0, 0, width / ratio, height / ratio, null);
            } else {
                // 缩小边长
                tag = new BufferedImage(90, 90, BufferedImage.TYPE_INT_RGB);
                // 绘制 缩小  后的图片
                tag.getGraphics().drawImage(src, 0, 0, 90, 90, null);
            }
            ImageIO.write(tag, "png", new File(toImagePath));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    /**
     * 图片缩放,w，h为缩放的目标宽度和高度
     * src为源文件目录，dest为缩放后保存目录
     *
     * @param src
     * @param dest
     * @param w
     * @param h
     * @throws Exception
     */
    public static void zoomImage(String src, String dest, int w, int h) throws Exception {
        double wr, hr;
        File srcFile = new File(src);
        File destFile = new File(dest);

        //读取图片
        BufferedImage bufImg = ImageIO.read(srcFile);

        //设置缩放目标图片模板
        Image image;

        //获取缩放比例
        wr = w * 1.0 / bufImg.getWidth();
        hr = h * 1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        image = ato.filter(bufImg, null);
        try {
            //写入缩减后的图片
            ImageIO.write((BufferedImage) image, dest.substring(dest.lastIndexOf(".") + 1), destFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 将图片写入到磁盘
     *
     * @param img      图片数据流
     * @param fileName 文件保存时的名称
     */
    private static void writeImageToDisk(byte[] img, String fileName) {
        try {
            File file = new File(fileName);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(img);
            fops.flush();
            fops.close();
            System.out.println("图片已经写入");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证上传文件类型是否属于图片格式
     *
     * @return
     */
    public static boolean isImage(String contentType, String ext) {
        List<String> arrowType = Arrays.asList("image/bmp", "image/png", "image/gif", "image/jpg", "image/jpeg", "image/pjpeg");
        List<String> arrowExtension = Arrays.asList("gif", "jpg", "bmp", "png");
        return arrowType.contains(contentType.toLowerCase()) && arrowExtension.contains(ext.toLowerCase());
    }

}
