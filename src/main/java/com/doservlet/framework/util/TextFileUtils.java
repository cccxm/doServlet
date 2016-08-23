package com.doservlet.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.LinkedList;

/**
 * 文本文件工具类。其中使用的文件流为
 * <tt>RandomAccessFile<tt>类，该类操作以字符为基础的文本文件， 可以向操作字符串一样操作文本中的字符数据。
 * 当前版本操作中文字符可能会出现未知异常。
 * 
 * @author chenj_000
 * @version 15.8.16
 */
public class TextFileUtils {
	private String regex = "[^a-zA-Z0-9]+";
	private File file;
	private RandomAccessFile ran;
	private LinkedList<Integer> index;
	private int wordCount;
	private String[] words;
	private int lineCont;
	private int sumlineCount;
	private String[] lines;
	private int sumWordCount;

	public TextFileUtils(File file) throws IOException {
		this.file = file;
		ran = new RandomAccessFile(file, "rw");
		index = new LinkedList<Integer>();
	}

	public TextFileUtils(String dirName, String fileName) throws IOException {
		file = getFile(dirName, fileName);
		ran = new RandomAccessFile(file, "rw");
		index = new LinkedList<Integer>();
	}

	/**
	 * 向文件末尾追加内容
	 * 
	 * @param content
	 *            追加内容
	 * @throws IOException
	 */
	public void append(String content) throws IOException {
		long len = ran.length();
		ran.seek(len);
		// ran.writeUTF(content);
		ran.write(content.getBytes("utf-8"));
	}

	/**
	 * 向文本中追加字节数据
	 * 
	 * @param buff
	 * @throws IOException
	 */
	public void append(byte[] buff) throws IOException {
		long len = ran.length();
		ran.seek(len == 0 ? 0 : len - 1);
		// ran.writeUTF(content);
		ran.write(buff);
	}

	/**
	 * 向文本中追加字节数据
	 * 
	 * @param buff
	 * @throws IOException
	 */
	public void append(byte[] buff, int offset, int len) throws IOException {
		long length = ran.length();
		ran.seek(length == 0 ? 0 : length - 1);
		// ran.writeUTF(content);
		ran.write(buff, offset, len);
	}

	/**
	 * 清空文件内容
	 * 
	 * @throws IOException
	 */
	public void clear() throws IOException {
		ran.setLength(0);
	}

	/**
	 * 关闭当前操作文件的随机流
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		ran.close();
	}

	/**
	 * 删除本对象当前持有的文件，并关闭所有的流
	 * 
	 * @return 文件删除状态
	 * @throws IOException
	 */
	public boolean delete() throws IOException {
		ran.close();
		return file.delete();
	}

	/**
	 * 查找文本中是否包含此单词，包含则返回位置，否则返回-1；
	 * 查找到单词后可以调用nextWord方法，由此遍历。若第一次使用此方法或文本已修改，建议先调用reset方法
	 * 
	 * @since 15.8.2
	 * @param word
	 * @return
	 * @throws IOException
	 */
	public int findWord(String word) throws IOException {
		if (words == null)
			words = getContent().split(regex);
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(word)) {
				wordCount = words.length - i;
				return i;
			}
		}
		return -1;
	}

	/**
	 * 获得文件字节数组
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] getBytes() throws IOException {
		return getBytes(0, ran.length());
	}

	/**
	 * 获得文件指定区域的文件数组
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws IOException
	 */
	public byte[] getBytes(long start, long end) throws IOException {
		if (start < 0 || end > ran.length() || start > end)
			return null;
		byte[] buff = new byte[(int) (end - start)];
		ran.seek(start);
		ran.read(buff);
		return buff;
	}

	/**
	 * 获取全部文本内容
	 * 
	 * @return 全部文本内容
	 * @throws IOException
	 */
	public String getContent() throws IOException {
		return getContent(0, ran.length());
	}

	/**
	 * 获取某一区间的文本数据
	 * 
	 * @param start
	 *            起始位置
	 * @param end
	 *            结束位置
	 * @return 当前区间文本数据
	 * @throws IOException
	 */
	public String getContent(long start, long end) throws IOException {
		if (start < 0 || end > ran.length() || start > end)
			return null;
		byte[] buff = new byte[(int) (end - start)];
		ran.seek(start);
		return new String(buff, 0, ran.read(buff), "utf-8");

	}

	/**
	 * 返回当前正在使用的文件对象
	 * 
	 * @since 15.8.8
	 * @return
	 */
	public File getFile() {
		return file;
	}

	/**
	 * 创建文件对象
	 * 
	 * @param dirName
	 *            文件目录
	 * @param fileName
	 *            文件名
	 * @return 文件对象
	 * @since 15.7.29
	 * @throws IOException
	 */
	public static File getFile(String dirName, String fileName)
			throws IOException {
		File directory = new File(dirName);
		if (!directory.exists())
			directory.mkdirs();
		File file = new File(directory, fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	/**
	 * 从当前文件中获取输入流
	 * 
	 * @return
	 * @since 15.8.7
	 * @throws IOException
	 */
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}

	/**
	 * 获取文件有效行数
	 * 
	 * @return
	 */
	public int getLineCount() {
		try {
			hasNextLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sumlineCount;
	}

	/**
	 * 获取文本文件中的单词数
	 * 
	 * @return
	 */
	public int getWordCount() {
		try {
			hasNext();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sumWordCount;
	}

	/**
	 * 获取文件长度
	 * 
	 * @return 文件长度
	 * @throws IOException
	 */
	public int length() throws IOException {
		return (int) ran.length();
	}

	/**
	 * 是否还有单词未遍历
	 * 
	 * @since 15.8.2
	 * @return
	 */
	public boolean hasNext() throws IOException {
		if (words == null) {
			words = getContent().split(regex);
			sumWordCount = wordCount = words.length;
		}
		return wordCount == 0 ? false : true;
	}

	/**
	 * 当前是否有下一行
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean hasNextLine() throws IOException {
		if (lines == null) {
			lines = getContent().split("[\r\n]+");
			sumlineCount = lineCont = lines.length;
		}
		return lineCont == 0 ? false : true;
	}

	/**
	 * 插入文本数据
	 * 
	 * @param content
	 *            待插入数据
	 * @param position
	 *            插入位置
	 * @return 插入结果
	 * @throws IOException
	 */
	public boolean insert(String content, int position) throws IOException {
		return replace(content, position, position);
	}

	/**
	 * 输出换行
	 * 
	 * @since 15.7.31
	 * @throws IOException
	 * 
	 */
	public void newLine() throws IOException {
		append("\r\n");
	}

	/**
	 * 读取下一行数据
	 * 
	 * @return
	 */
	public String nextLine() {
		if (lineCont == 0)
			return null;
		return lines[lines.length - lineCont--];
	}

	/**
	 * 使用目标字符串替换源字符串
	 * 
	 * @param source
	 *            被替换的数据
	 * @param target
	 *            目标数据
	 * @return 替换成功返回 true
	 * @throws IOException
	 */
	public boolean newReplace(String source, String target) throws IOException {
		String text = getContent();
		if (!text.contains(source))
			return false;
		String[] str = text.split(source);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < str.length - 1; i++) {
			content.append(str[i]).append(target);
		}
		content.append(str[str.length - 1]);
		clear();
		append(content.toString());
		return true;

	}

	/**
	 * 按单词遍历文本文件
	 * 
	 * @since 15.8.2
	 * @return
	 * @throws IOException
	 */
	public String nextWord() throws IOException {
		if (wordCount == 0) {
			return null;
		}
		return words[words.length - wordCount--];
	}

	/**
	 * 从当前单词位置向后查询len个单词并返回
	 * 
	 * @since 15.8.2
	 * @param len
	 * @return
	 * @throws IOException
	 */
	public String nextWord(int len) throws IOException {
		if (len >= wordCount || wordCount == 0)
			return null;
		wordCount -= len;
		return words[words.length - wordCount--];
	}

	/**
	 * 查询文本中与字符串相匹配的索引,并以数组形式返回
	 * 
	 * @param str
	 *            匹配字符串
	 * @return 包含索引的数组，未找到返回null
	 * @throws IOException
	 */
	public int[] search(String str) throws IOException {
		String content = null;
		int site = 0;
		int n;
		while (site < (int) ran.length()) {
			content = getContent(site, (int) ran.length());
			if ((n = content.indexOf(str)) == -1)
				break;
			site += n;
			index.add(site);
			site += str.length();
		}
		if (index.size() == 0)
			return null;
		int[] a = new int[index.size()];
		site = 0;
		for (Object obj : index.toArray()) {
			a[site++] = (Integer) obj;
		}
		return a;
	}

	/**
	 * 重置单词遍历器，当文本被修改后应当调用此方法。
	 * 
	 * @since 15.8.2
	 * @throws IOException
	 */
	public void reset() {
		wordCount = 0;
		words = null;
		lineCont = 0;
		lines = null;
	}

	/**
	 * 删除文件中对应的文本数据
	 * 
	 * @param str
	 *            删除的文本数据
	 * @return true 删除成功， false 没有该数据或删除失败
	 * @throws IOException
	 */
	public boolean remove(String str) throws IOException {
		return replace(str, "");
	}

	/**
	 * 删除区间内数据
	 * 
	 * @param start
	 *            被删除的起始位置
	 * @param end
	 *            被删除字段的结束位置
	 * @return 被删除的字符串
	 */
	public String remove(int start, int end) throws IOException {
		String str = getContent(start, end);
		return replace("", start, end) ? str : null;
	}

	/**
	 * 使用目标字符串替换源字符串,因为此方法算法错位倾向,现在被newReplace方法替换
	 * 
	 * @param source
	 *            被替换的数据
	 * @param target
	 *            目标数据
	 * @return 替换成功返回 true
	 * @deprecated 此方法具有错位倾向,现在被newReplace方法替换
	 * @throws IOException
	 */
	@Deprecated
	public boolean replace(String source, String target) throws IOException {
		int len = source.length();
		int size = target.length() - len;
		int[] site = search(source);
		int i = 0;
		if (site == null)
			return false;
		for (int a : site) {
			replace(target, a + size * i, a + size * i++ + len);
		}
		return true;
	}

	/**
	 * 使用content替换区间内数据
	 * 
	 * @param content
	 *            目标数据
	 * @param start
	 *            起始位置
	 * @param end
	 *            结束位置
	 * @return 替换状态
	 * @throws IOException
	 */
	public boolean replace(String content, int start, int end)
			throws IOException {
		if (start < 0 || end > ran.length() || start > end)
			return false;
		String str = getContent(0, start) + content
				+ getContent(end, (int) ran.length());
		clear();
		ran.write(str.getBytes("utf-8"));
		return true;
	}

	/**
	 * 重置单词遍历器，并设置单次分割方式。
	 * 
	 * @since 15.8.2
	 * @throws IOException
	 */
	public void reset(String regex) {
		wordCount = 0;
		words = null;
		this.regex = regex;
		lineCont = 0;
		lines = null;
	}

	/**
	 * 以字符串格式返回文本中的数据
	 * 
	 * @param file
	 * @return
	 * @since 15.8.8
	 */
	public static String toString(File file) {
		try {
			return new TextFileUtils(file).getContent();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
