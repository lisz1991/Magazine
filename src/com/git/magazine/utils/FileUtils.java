package com.git.magazine.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @Use:文件处理工具类
 * @Date:2014-2-25
 * @Time:下午5:52:58
 */
public class FileUtils {

	private static final long KB = 1024;
	private static final long MB = (KB * KB);
	private static final long GB = (KB * MB);
	private static final long TB = (KB * GB);

	private static final String NAN = "NaN";
	private static final String NAF = "NaF";

	public static final boolean HIDE_SIZE_TYPE = Boolean.TRUE;
	public static final boolean SHOW_SIZE_TYPE = Boolean.FALSE;

	private static Exception exception;

	public FileUtils() {
		super();
	}

	public static FileOutputStream getOutStream(final File file)
			throws IOException, NullPointerException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException(String.format(
						"The file <%s> is not a file, its a directory!", file));
			}
			if (!file.canWrite()) {
				throw new IOException(String.format(
						"The file <%s> could not be altered", file));
			}
		} else {
			final File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				if (!parent.mkdirs()) {
					throw new IOException(String.format(
							"The file <%s> could not be created.", file));
				}
			}

			return new FileOutputStream(file);
		}
		throw new NullPointerException(String.format(exception.toString()));
	}

	public static FileInputStream getPutStream(final File file)
			throws IOException, NullPointerException {

		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException(String.format(
						"The file <%s> is not a file, its a directory!", file));
			}
			if (!file.canRead()) {
				throw new IOException(String.format(
						"The file <%s> could not be altered", file));
			}
		} else {
			throw new IOException(exception);
		}
		return new FileInputStream(file);
	}

	public static byte[] getFileByte(final File file) throws IOException {
		if (file.isDirectory())
			throw new RuntimeException(String.format("File %s is a directory.",
					file.getAbsolutePath()));
		if (file.length() > Integer.MAX_VALUE)
			throw new RuntimeException(String.format(
					"File %s is too large to process.", file.getAbsolutePath()));
		final byte buffer[] = new byte[(int) file.length()];
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			in.read(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
		return buffer;

	}

	public static byte[] input2Byte(InputStream in) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[6 * 1024];
		int count = -1;
		while ((count = in.read(data, 0, 4 * 1024)) != -1)
			outStream.write(data, 0, count);
		data = null;
		return outStream.toByteArray();
	}

	public static byte[] output2Byte(OutputStream out) throws IOException {
		byte[] data = new byte[6 * 1024];
		out.write(data);
		return data;
	}

	public static InputStream byte2Input(byte[] in) {
		ByteArrayInputStream is = new ByteArrayInputStream(in);
		return is;
	}

	public static OutputStream byte2Output(byte[] in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(in);
		return out;
	}

	public static String getFileSize(final Long fileSize,
			final FileSizeType fileSizeType, final boolean omitFormatType)
			throws NullPointerException {
		if (fileSize != null && fileSizeType != null) {
			if (fileSize > 0) {
				switch (fileSizeType) {
				case BYTE:
					return Long.toString(fileSize);
				case KILOBYTE:
					return Long.toString(Math.round(fileSize / (KB)))
							+ ((omitFormatType) ? "" : "KB");
				case MEGABYTE:
					return Long.toString(Math.round(fileSize / (MB)))
							+ ((omitFormatType) ? "" : "MB");
				case GIGABYTE:
					return Long.toString(Math.round(fileSize / (GB)))
							+ ((omitFormatType) ? "" : "GB");
				case TERABYTE:
					return Long.toString(Math.round(fileSize / (TB)))
							+ ((omitFormatType) ? "" : "TB");
				default:
					return NAF;
				}
			}
			return NAN;
		}
		throw new NullPointerException("The size provided was null");
	}

	public static long getDirSize(final File directory,
			final FileSizeType fileSizeType) throws IllegalArgumentException {
		if (!directory.exists()) {
			throw new IllegalArgumentException(String.format(
					"%s does not exist", directory));
		}
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException(String.format(
					"%s is not a directory", directory));
		}
		long size = 0;
		List<File> files = Arrays.asList(directory.listFiles());
		if (files == null || files.size() == size)
			return 0;
		for (File file : files) {
			if (file.isDirectory()) {
				size += getDirSize(file, null);
			} else {
				size += file.length();
			}
			size = Long
					.getLong(getFileSize(size, fileSizeType, HIDE_SIZE_TYPE));
			return size;
		}
		throw new IllegalArgumentException(exception.toString());
	}

	public static boolean folderCreate(final File folder) throws Exception {
		if (folder.exists() && !folder.isDirectory()) {
			throw new Exception(String.format("File %s exists.",
					folder.getName()));
		} else {
			return folder.mkdir();
		}
	}

	public static boolean fileDelete(final File file)
			throws NullPointerException {
		return file.exists() && file.delete();
	}

	public static void fileCopy(File source, File target) {
		File tarpath = new File(target, source.getName());
		if (source.isDirectory()) {
			tarpath.mkdir();
			File[] dir = source.listFiles();
			for (int i = 0; i < dir.length; i++) {
				fileCopy(dir[i], tarpath);
			}
		} else {
			try {
				InputStream is = new FileInputStream(source); // 用于读取文件的原始字节流
				OutputStream os = new FileOutputStream(tarpath); // 用于写入文件的原始字节的流
				byte[] buf = new byte[1024];// 存储读取数据的缓冲区大小
				int len = 0;
				while ((len = is.read(buf)) != -1) {
					os.write(buf, 0, len);
				}
				is.close();
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void fileMove(File from, File to) throws IOException {
		fileMove(from, to, false);
	}

	public static void fileMove(File from, File to, boolean overwrite)
			throws IOException {
		if (to.exists()) {
			if (overwrite) {
				if (!to.delete()) {
					throw new IOException(MessageFormat.format("deleteerror",
							(Object[]) new String[] { to.toString() }));
				}
			} else {
				throw new IOException(MessageFormat.format(
						"alreadyexistserror",
						(Object[]) new String[] { to.toString() }));
			}
		}

		if (from.renameTo(to))
			return;

		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(from);
			out = new FileOutputStream(to);
			copyStream(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
			if (!from.delete()) {
				throw new IOException(MessageFormat.format(
						"deleteoriginalerror",
						(Object[]) new String[] { from.toString(),
								to.toString() }));
			}
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
			if (out != null) {
				out.flush();
				out.close();
				out = null;
			}
		}
	}

	private static void copyStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	/**
	 * FILE-SIZE-TYPES
	 */
	public enum FileSizeType {
		BYTE, KILOBYTE, MEGABYTE, GIGABYTE, TERABYTE
	}
}
