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
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

	private static final int FILE_SIZE = 100 * 1024 * 1024;	// n兆

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
