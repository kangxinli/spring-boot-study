package com.sample.java.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	private static final int FILE_SIZE = 1 * 1024 * 1024;	// n兆
	
	/**
	 * 文件上传
	 * @param file
	 * @param uploadPath
	 * @return
	 * @throws IOException
	 */
	public static String uploadfile(MultipartFile file, String uploadPath) throws IOException{
		
		String fileName = file.getOriginalFilename();
       // 获取文件的后缀名
       String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		String filePath = uploadPath + "/" + UUID.randomUUID().toString() + "." + suffixName;
		FileInputStream fis = null;
        FileOutputStream fos = null;
 
        try {
            fis = (FileInputStream) file.getInputStream();
            fos = new FileOutputStream(new File(filePath));
 
            FileChannel inChannel = fis.getChannel();
            FileChannel outChannel = fos.getChannel();
 
            int capacity = 1024;
            ByteBuffer buffer = ByteBuffer.allocate(capacity);
            while( (inChannel.read(buffer))!=-1 ){
                buffer.flip();
 
                while (( outChannel.write(buffer) )!=0){
 
                }
                buffer.clear();
            }
            inChannel.close();
            outChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }
	
	/**
	 * 文件删除
	 * @param path
	 * @throws IOException
	 */
	public static void deleteFile(String path) throws IOException {
		Path p = Paths.get(path);
		Files.delete(p);
	}

	/**
	 * 文件压缩
	 * @param filePath
	 * @param zipFile
	 */
	public static void zipFilePip(String filePath, String zipFile) {
		try (WritableByteChannel out = Channels.newChannel(new FileOutputStream(zipFile))) {
			Pipe pipe = Pipe.open();
			// 异步任务
			CompletableFuture.runAsync(() -> runTask(filePath, pipe));
			// 获取读通道
			ReadableByteChannel readableByteChannel = pipe.source();
			ByteBuffer buffer = ByteBuffer.allocate(FILE_SIZE);
			while (readableByteChannel.read(buffer) >= 0) {
				buffer.flip();
				out.write(buffer);
				buffer.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 异步任务
	@SuppressWarnings("resource")
	public static void runTask(String filePath, Pipe pipe) {
		try (ZipOutputStream zos = new ZipOutputStream(Channels.newOutputStream(pipe.sink()));
				WritableByteChannel out = Channels.newChannel(zos)) {
			File file = new File(filePath);
			zos.putNextEntry(new ZipEntry(file.getName()));
			FileChannel jpgChannel = new FileInputStream(file).getChannel();
			jpgChannel.transferTo(0, FILE_SIZE, out);
			jpgChannel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// 文件路径
		String filePath = "D:/...";
		// 压缩文件路径
		String zipPath = "D:/test/123.zip";
		long beginTime = System.currentTimeMillis();
		zipFilePip(filePath, zipPath);
		long endTime = System.currentTimeMillis();
		System.out.println("耗时：" + (endTime - beginTime));
	}

}
