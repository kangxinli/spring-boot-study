package com.sample.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sample.app.entity.FileInfo;

/**
 * 文件service
 *
*/
public interface IFileService extends IService<FileInfo> {
	FileInfo upload(MultipartFile file ) throws Exception;
	
	List<FileInfo> findList(Map<String, Object> params);

	void delete(String id);
}
