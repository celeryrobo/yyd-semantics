package com.yyd.semantic.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * 文件操作方法
 * @author celery
 *
 */
import java.util.Properties;

import org.springframework.util.ResourceUtils;

public class FileUtils {
	/**
	 * 查找指定后缀名的文件
	 * 
	 * @param filename
	 *            文件或者文件夹
	 * @param ext
	 *            指定需要查找的文件的后缀
	 * @return 所有查询到的文件绝对路径
	 */
	public static List<File> listFiles(String filename, String ext) {
		List<File> files = new ArrayList<File>();
		File file = new File(filename);
		if (!file.exists()) {
			return files;
		} else if (file.isDirectory()) {
			File[] filelist = file.listFiles(new FileExtFilter(ext));
			if (filelist != null) {
				for (File f : filelist) {
					files.addAll(listFiles(f.getAbsolutePath(), ext));
				}
			}
		} else {
			files.add(file);
		}
		return files;
	}

	public static List<String> listFilenames(String filename, String ext) {
		List<File> files = listFiles(filename, ext);
		List<String> filenames = new ArrayList<String>();
		for (File file : files) {
			filenames.add(file.getAbsolutePath());
		}
		return filenames;
	}

	/**
	 * 读取指定文件的全部内容
	 * 
	 * @param filename
	 *            指定的需要读取的文件名称
	 * @return 读取文件的全部内容
	 * @throws Exception
	 */
	public static String readFile(String filename) throws Exception {
		File file = new File(filename);
		return readFile(file);
	}

	public static String readFile(File file) throws Exception {
		StringBuilder sb = new StringBuilder("");
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			br.close();
			isr.close();
			fis.close();
		}
		return sb.toString();
	}

	public static BufferedReader fileReader(File file) throws Exception {
		BufferedReader br = null;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
		}
		return br;
	}

	public static BufferedReader fileReader(String filename) throws Exception {
		File file = new File(filename);
		return fileReader(file);
	}

	public static String getResourcePath() {
		try {
			return ResourceUtils.getURL("classpath:").getPath();
		} catch (FileNotFoundException e) {
		}
		return ClassLoader.getSystemResource("").getPath();
	}

	public static Properties buildProperties(String path) throws Exception {
		String propertiesPath = getResourcePath() + path;
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(propertiesPath);
		properties.load(fis);
		fis.close();
		return properties;
	}
}
