package com.doservlet.framework.util;

import java.io.IOException;

/**
 * 数据队列接口<br>
 * 采用先进先出的方式，例：<br>
 * DataQueue queue=DataFileUtils.getDataQueue;<br>
 * &nbsp;&nbsp;queue.write(String str);<br>
 * &nbsp;&nbsp;queue.write(Int i);<br>
 * &nbsp;&nbsp;String str=queue.read();<br>
 * &nbsp;&nbsp;Int i=queue.read();<br>
 * @version 15.8.6
 * @author chenj_000
 *
 */
public interface DataQueue {
	public void write(byte b) throws IOException;

	public void write(byte[] b) throws IOException;

	public void write(char c) throws IOException;

	public void write(int i) throws IOException;

	public void write(double d) throws IOException;

	public void write(String s) throws IOException;

	public Object read() throws IOException;
	
	public boolean delete() throws IOException;
	
	public void clear() throws IOException;
	
	public boolean hasNext();
}
