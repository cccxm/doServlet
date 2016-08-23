package com.doservlet.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedList;

/**
 * 数据流工具包,可以便捷的在持有的文件中操作 byte,char,int,double,String类型的数据. 其中的数据流也可以用来操作网络流。
 * 
 * @author chenj_000
 * @version 15.8.6
 */
public class DataFileUtils {
	private File file;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private LinkedList<Integer> dataType;
	private final int BYTE = 1, CHAR = 2, INT = 4, DOUBLE = 8, STRING = 10;

	/**
	 * 跟据传入的文件创建数据流，提供读写操作
	 * 
	 * @param file
	 * @throws IOException
	 */
	public DataFileUtils(File file) throws IOException {
		this(file, false);
	}

	/**
	 * 跟据传入的文件创建数据流，提供读写操作
	 * 
	 * @param file
	 * @param append
	 *            是否使用追加模式
	 * @throws IOException
	 */
	public DataFileUtils(File file, boolean append) throws IOException {
		if (!file.exists())
			file.createNewFile();
		this.file = file;
		initStream(file, append);
		dataType = new LinkedList<Integer>();
	}

	/**
	 * 跟据目录和文件名创建数据流，提供读写操作
	 * 
	 * @param dirName
	 *            路径名
	 * @param fileName
	 *            文件名
	 * @throws IOException
	 */
	public DataFileUtils(String dirName, String fileName) throws IOException {
		this(dirName, fileName, false);
	}

	/**
	 * 跟据目录和文件名创建数据流，提供读写操作
	 * 
	 * @param dirName
	 *            路径名
	 * @param fileName
	 *            文件名
	 * @param append
	 *            追加模式
	 * @throws IOException
	 */
	public DataFileUtils(String dirName, String fileName, boolean append)
			throws IOException {
		file = getFile(dirName, fileName);
		initStream(file, append);
		dataType = new LinkedList<Integer>();
	}

	/**
	 * 根据Socket创建输入输出流
	 * 
	 * @param socket
	 * @throws IOException
	 */
	public DataFileUtils(Socket socket) throws IOException {
		this.socket = socket;
		this.in = new DataInputStream(socket.getInputStream());
		this.out = new DataOutputStream(socket.getOutputStream());
		dataType = new LinkedList<Integer>();
	}

	private void initStream(File file, boolean append)
			throws FileNotFoundException {
		in = new DataInputStream(new FileInputStream(file));
		out = new DataOutputStream(new FileOutputStream(file, append));
	}

	/**
	 * 清空数据
	 * 
	 * @throws IOException
	 */
	public void clear() throws IOException {
		delete();
		if (!file.exists())
			file.createNewFile();
		initStream(file, true);
	}

	/**
	 * 关闭所有流
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (in != null)
			in.close();
		if (out != null)
			out.close();
		if (socket != null)
			socket.close();
	}

	/**
	 * 当前持有对象为File文件时，删除文件并关闭所有流，持有对象为Socket时，关闭流
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean delete() throws IOException {
		if (socket != null) {
			close();
			return true;
		}
		close();
		return file.delete();
	}

	/**
	 * 是否有下一个元素
	 * 
	 * @return
	 */
	public boolean hasNext() {
		return out.size() > 0;
	}

	/**
	 * 获得其中的byte数组
	 * 
	 * @return
	 */
	public byte[] getBytes() {
		byte[] bytes = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int n = -1;
		try {
			while ((n = read(bytes)) != -1) {
				out.write(bytes, 0, n);
			}
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取一个数据队列对象
	 * 
	 * @return
	 * @since 15.8.6
	 * @throws IOException
	 */
	public static DataQueue getDataQueue() throws IOException {
		class Queue extends DataFileUtils implements DataQueue {
			public Queue() throws IOException {
				super(".", "dataQ.dat");
				Runtime.getRuntime().addShutdownHook(new Thread() {
					@Override
					public void run() {
						try {
							delete();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}

			@Override
			public boolean delete() throws IOException {
				return super.delete();
			}
		}
		return new Queue();
	}

	/**
	 * 新建文件
	 * 
	 * @param dirName
	 *            路径名
	 * @param fileName
	 *            文件名
	 * @return 文件对象
	 * @since 15.7.29
	 * 
	 */
	public static File getFile(String dirName, String fileName) {
		new File(dirName).mkdirs();
		File file = new File(dirName + "/" + fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 从文件中读取已存入的任意类型的数据，并以Object对象返回 byte,char,int,double,String类型的数据。
	 * 如果当前文件是Socket对象，则返回null，必须调用read(byte[] b)方法
	 * 
	 * @return Object 取出的数据
	 * @throws IOException
	 */
	public Object read() throws IOException {
		if (socket != null) {
			return in.read();
		}
		switch (dataType.removeLast()) {
		case BYTE:
			return in.readByte();
		case CHAR:
			return in.readChar();
		case INT:
			return in.readInt();
		case DOUBLE:
			return in.readDouble();
		case STRING: {
			int len = dataType.removeLast();
			String str = "";
			for (int i = 0; i < len; i++) {
				str += in.readChar();
			}
			return str;
		}
		default:
			return in.read();
		}
	}

	/**
	 * 当前持有文件为Socket时才能调用此方法，否则返回-1；
	 * 
	 * @param b
	 * @return 实际写入字节长度，没有数据或持有对象不是Socket时返回-1
	 * @throws IOException
	 */
	public int read(byte[] b) throws IOException {
		if (dataType.size() > 0)
			return -1;
		int len = in.read(b);
		return len;
	}

	/**
	 * 向网络流或文件中写入一个字节数组，其最终都将被转换为字符串。
	 * 
	 * @param b
	 * @throws IOException
	 */
	public void write(byte[] b) throws IOException {
		if (socket == null) {
			out.write(b);
			return;
		}
		out.write(b);
	}

	/**
	 * 使用输入流向文件中写入数据，写入完毕后流将被关闭。
	 * 
	 * @param b
	 * @throws IOException
	 */
	public void write(InputStream in) throws IOException {
		int len;
		byte[] buffer = new byte[8 * 1024];
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		try {
			in.close();
		} catch (Exception e) {
			in = null;
		}
	}

	/**
	 * 向网络流或文件中写入一个字节数组，其中写入的数据具有不可读取的特性， 即调用read()方法获取不到任何对象
	 * 
	 * @param b
	 * @param off
	 * @param len
	 * @throws IOException
	 */
	public void write(byte[] b, int off, int len) throws IOException {
		if (socket == null) {
			out.write(b, off, len);
			return;
		}
		out.write(b, off, len);
	}

	/**
	 * 向流中写入一个字节
	 * 
	 * @param b
	 * @throws IOException
	 */
	public void write(byte b) throws IOException {
		if (socket != null) {
			write(new String(b + "").getBytes());
			return;
		}
		out.writeByte(b);
		dataType.addFirst(BYTE);
	}

	/**
	 * 写入字符数据
	 * 
	 * @param c
	 * @throws IOException
	 */
	public void write(char c) throws IOException {
		if (socket != null) {
			write(new String(c + "").getBytes());
			return;
		}
		out.writeChar(c);
		dataType.addFirst(CHAR);
	}

	/**
	 * 写入整型数据
	 * 
	 * @param i
	 * @throws IOException
	 */
	public void write(int i) throws IOException {
		if (socket != null) {
			write(new String(i + "").getBytes());
			return;
		}
		out.writeInt(i);
		dataType.addFirst(INT);
	}

	/**
	 * 写入双精度数据
	 * 
	 * @param d
	 * @throws IOException
	 */
	public void write(double d) throws IOException {
		if (socket != null) {
			write(new String(d + "").getBytes());
			return;
		}
		out.writeDouble(d);
		dataType.addFirst(DOUBLE);
	}

	/**
	 * 向文件中写入字符串数据
	 * 
	 * @param str
	 * @throws IOException
	 */
	public void write(String str) throws IOException {
		if (socket != null) {
			write(str.getBytes());
			return;
		}
		out.writeChars(str);
		dataType.addFirst(STRING);
		dataType.addFirst(str.length());
	}

	/**
	 * 默认写入 int 类型的数据 0
	 * 
	 * @deprecated 此方法容易导致文本数据混乱
	 * @throws IOException
	 */
	@Deprecated
	public void write() throws IOException {
		if (socket != null) {
			write(new String(0 + "").getBytes());
			return;
		}
		out.write(0);
		dataType.addFirst(0);
	}
}