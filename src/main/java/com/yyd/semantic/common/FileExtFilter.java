package com.yyd.semantic.common;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 查找或遍历文件时，过滤掉非文件夹或非指定后缀名的文件
 * 
 * @author celery
 *
 */
public class FileExtFilter implements FilenameFilter {
	private String ext;

	public FileExtFilter(String ext) {
		this.ext = ext;
	}

	@Override
	public boolean accept(File dir, String name) {
		String absolutePath = dir.getAbsolutePath() + "\\" + name;
		File file = new File(absolutePath);
		return file.isDirectory() || name.endsWith(this.ext);
	}

}
