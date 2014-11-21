package com.xiaofeng.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @Use:压缩、解压文件工具类
 * @Date:2014-2-25
 * @Time:下午4:30:34
 */
public class ZipUtils {

	public static final int BUFFER = 1024;// 缓存大小

	private ZipUtils() {

	}

	/**
	 * ZIP压缩，把baseResources(文件目录)下所有文件及目录压缩到targetName文件
	 */
	public static void toZip(String baseResources, String targetName)
			throws Exception {
		List<?> fileList = getSubFiles(new File(baseResources));
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
				targetName));
		ZipEntry ze = null;
		byte[] buf = new byte[BUFFER];
		int readLen = 0;
		for (int i = 0; i < fileList.size(); i++) {
			File f = (File) fileList.get(i);
			ze = new ZipEntry(getAbsFileName(baseResources, f));
			ze.setSize(f.length());
			ze.setTime(f.lastModified());
			zos.putNextEntry(ze);
			InputStream is = new BufferedInputStream(new FileInputStream(f));
			while ((readLen = is.read(buf, 0, BUFFER)) != -1) {
				zos.write(buf, 0, readLen);
			}
			is.close();
		}
		zos.close();
	}

	/**
	 * 解压缩ZIP文件，将fileName文件里的内容解压到targetDirName目录下
	 */
	public static void fromZip(String fileName, String targetDirName)
			throws IOException {
		if (!targetDirName.endsWith(File.separator)) {
			targetDirName += File.separator;
		}
		// 根据ZIP文件创建ZipFile对象
		@SuppressWarnings("resource")
		ZipFile zipFile = new ZipFile(fileName);
		ZipEntry entry = null;
		String entryName = null;
		String targetFileName = null;
		byte[] buffer = new byte[BUFFER];
		int bytes_read;
		// 获取ZIP文件里所有的entry
		Enumeration<?> entrys = zipFile.entries();
		// 遍历所有entry
		while (entrys.hasMoreElements()) {
			entry = (ZipEntry) entrys.nextElement();
			// 获得entry的名字
			entryName = entry.getName();
			targetFileName = targetDirName + entryName;
			if (entry.isDirectory()) {
				// 如果entry是一个目录，则创建目录
				new File(targetFileName).mkdirs();
				continue;
			} else {
				// 如果entry是一个文件，则创建父目录
				new File(targetFileName).getParentFile().mkdirs();
			}

			// 否则创建文件
			File targetFile = new File(targetFileName);
			// 打开文件输出流
			FileOutputStream os = new FileOutputStream(targetFile);
			// 从ZipFile对象中打开entry的输入流
			InputStream is = zipFile.getInputStream(entry);
			while ((bytes_read = is.read(buffer)) != -1) {
				os.write(buffer, 0, bytes_read);
			}
			// 关闭流
			os.close();
			is.close();
		}
	}

	/**
	 * 给定根目录，返回另一个文件名的相对路径，用于zip文件中的路径.
	 */
	private static String getAbsFileName(String baseDir, File realFileName) {
		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true) {
			real = real.getParentFile();
			if (real == null)
				break;
			if (real.equals(base))
				break;
			else
				ret = real.getName() + "/" + ret;
		}
		return ret;
	}

	/**
	 * 取得指定目录下的所有文件列表，包括子目录.
	 */
	private static List<File> getSubFiles(File baseDir) {
		List<File> ret = new ArrayList<File>();
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile())
				ret.add(tmp[i]);
			if (tmp[i].isDirectory())
				ret.addAll(getSubFiles(tmp[i]));
		}
		return ret;
	}
}