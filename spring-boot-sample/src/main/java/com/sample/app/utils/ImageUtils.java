package com.sample.app.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.mortennobel.imagescaling.ResampleOp;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片工具类
 */
public class ImageUtils {


	/**
	 * 缩放图片
	 * 
	 * @param oldFile
	 *            原图片
	 * @param formatName
	 *            缩放后格式
	 * @param newFile
	 *            缩放后图片
	 * @param width
	 *            指定宽度
	 * @param height
	 *            指定高度
	 * @param db
	 *            是否等比例缩放
	 * @throws IOException
	 */
	public static void resizeImage(File oldFile, String formatName,
			File newFile, int width, int height, boolean db) throws IOException {
		try {
			BufferedImage image = null;
			if (oldFile != null && oldFile.isFile() && oldFile.exists()) {
				image = ImageIO.read(oldFile);
			}
			double[] zoomSize = new double[2];
			double srcWidth = image.getWidth();
			double srcHeigth = image.getHeight();

			if (db) {
				// 文件宽和高都小于指定宽和高则不需要处理
				if (srcWidth <= width && srcHeigth <= height) {
					// 不缩放
					zoomSize[0] = srcWidth;
					zoomSize[1] = srcHeigth;
				} else {
					// 等比例缩放控制
					double tempHeight = (srcHeigth / srcWidth) * width;
					if (tempHeight > height) {
						zoomSize[0] = (srcWidth / srcHeigth) * height;
						zoomSize[1] = height;
					} else {
						zoomSize[0] = width;
						zoomSize[1] = (srcHeigth / srcWidth) * width;
					}
				}
			} else {// 不等比例
				zoomSize[0] = width;
				zoomSize[1] = height;
			}
			ResampleOp resampleOp = new ResampleOp((int) zoomSize[0],
					(int) zoomSize[1]);
			BufferedImage tag = resampleOp.filter(image, null);
			ImageIO.write(tag, formatName, newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
	 * @param fromPic
	 * @param toPic
	 */
	public static void resizeImage(String filePath,String toPic){
		try {
			BufferedImage sourceImg = ImageIO.read(new File(filePath));
			Thumbnails.of(sourceImg).scale(1f).outputQuality(0.5f).toFile(toPic);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得图片宽度
	 * 
	 * @param file
	 *            图片文件
	 * @return
	 */
	public static int getHeight(MultipartFile file) {
		try {
			InputStream iis = file.getInputStream();
			BufferedImage bi = ImageIO.read(iis);
			return bi.getHeight();//高
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	/**
	 * 获得图片宽度
	 * 
	 * @param file
	 *            图片文件
	 * @return
	 */
	public static int getWidth(MultipartFile file) {
		try {
			InputStream iis = file.getInputStream();
			BufferedImage bi = ImageIO.read(iis);
			return bi.getWidth();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获得图片宽度
	 * 
	 * @param file
	 *            图片文件
	 * @return
	 */
	public static int getWidth(File file) {
		try {
			Image src = ImageIO.read(file);
			return src.getWidth(null);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获得图片高度
	 * 
	 * @param file
	 *            图片文件
	 * @return
	 */
	public static int getHeight(File file) {
		try {
			Image src = ImageIO.read(file);
			return src.getHeight(null);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 图片切割
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param x
	 *            目标切片起点x坐标
	 * @param y
	 *            目标切片起点y坐标
	 * @param destWidth
	 *            目标切片宽度
	 * @param destHeight
	 *            目标切片高度
	 */
	public static void cut(String intImageFile, String outImageFile, int x,
			int y, int destWidth, int destHeight) {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(intImageFile));
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度
			if (srcWidth >= destWidth && srcHeight >= destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
				img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(destWidth, destHeight,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null); // 绘制缩小后的图
				g.dispose();
				// 输出为文件
				ImageIO.write(tag, "JPEG", new File(outImageFile));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 把水印印刷到图片上
     * 
     * @param pressImg --
     *            水印文件
     * @param targetImg --
     *            目标文件
     * @param x
     *            --x坐标
     * @param y
     *            --y坐标
     */
    public static void pressImage(String pressImg, String targetImg, int x, int y) {
        try {
            //目标文件
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);

            //水印文件
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            //中间水印
            //g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
            //右下角水印
            g.drawImage(src_biao, (wideth - wideth_biao), (height - height_biao), wideth_biao, height_biao, null);
            //水印文件结束
            g.dispose();
//            FileOutputStream out = new FileOutputStream(targetImg);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            encoder.encode(image);
            ImageIO.write(image, targetImg.substring(targetImg.lastIndexOf(".")+1), _file);
//            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) throws IOException {
		resizeImage("D:\\IMG_0234.JPG", "D:\\IMG_0234.JPG");
	}

}
