package com.sample.pic.gif.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sample.pic.gif.fmsware.AnimatedGifEncoder;
import com.sample.pic.gif.fmsware.GifDecoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 在eclipse里使用JPEGCodec和JPEGImageEncoder时 会报错
 * 
 * 因为这是sun公司私有的两个方法，现在也废弃了，不建议使用。

         如果使用，可以使用下面三种任意一种方式：

   1.添加对sun私有包的权限
              在项目文件上右键-->build path-->configure build path-->libraries-->将JRE System library展开
          -->Access rules:No rules defined-->Edit-->Add-->Resolution选择Accessible、Rule Pattern
              填写**，然后在apply，保存，在页面中导入一下包就行了
   
   2.把Windows-Preferences-Java-Complicer-Errors/Warnings里面的Deprecated and restricted API中的
       Forbidden references(access rules)选为Warning就可以编译通过。
 *
 */
public class PicUtil {

	/**
	 * 把多张jpg图片合成一张
	 * 
	 * @param pic    String[] 多个jpg文件名 包含路径
	 * @param newPic String 生成的gif文件名 包含路径
	 */
	public synchronized static void jpgToGif(String pic[], String newPic) {
		try {
			AnimatedGifEncoder e = new AnimatedGifEncoder(); // 请见本博客文章
			e.setRepeat(0);
			e.start(newPic);
			BufferedImage src[] = new BufferedImage[pic.length];
			for (int i = 0; i < src.length; i++) {
				e.setDelay(200); // 设置播放的延迟时间
				src[i] = ImageIO.read(new File(pic[i])); // 读入需要播放的jpg文件
				e.addFrame(src[i]); // 添加到帧中
			}
			e.finish();
		} catch (Exception e) {
			System.out.println("jpgToGif Failed:");
			e.printStackTrace();
		}
	}

	/**
	 * 把gif图片按帧拆分成jpg图片
	 * 
	 * @param gifName String 小gif图片(路径+名称)
	 * @param path    String 生成小jpg图片的路径
	 * @return String[] 返回生成小jpg图片的名称
	 */
	public synchronized static String[] splitGif(String gifName, String path) {
		try {
			GifDecoder decoder = new GifDecoder();
			decoder.read(gifName);
			int n = decoder.getFrameCount(); // 得到frame的个数
			String[] subPic = new String[n];
			for (int i = 0; i < n; i++) {
				BufferedImage frame = decoder.getFrame(i); // 得到帧
				// int delay = decoder.getDelay(i); //得到延迟时间
				// 生成小的JPG文件
				subPic[i] = path + String.valueOf(i) + ".jpg";
				FileOutputStream out = new FileOutputStream(subPic[i]);
				ImageIO.write(frame, "jpeg", out);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(frame); // 存盘
				out.flush();
				out.close();
			}
			return subPic;
		} catch (Exception e) {
			System.out.println("splitGif Failed!");
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
//		String gifPath = "D:\\test\\20201205163854.gif";
//		String picPath = "D:\\test\\pic\\test";
//		splitGif(gifPath, picPath);
		
		String[] pic = new String[37];
		
		for (int i = 0; i < pic.length; i++) {
			pic[i] = "D:\\test\\pic\\test" + i + ".jpg";
		}
		
		String gifPath = "D:\\test\\new0.gif";
		jpgToGif(pic, gifPath);
	}

}
