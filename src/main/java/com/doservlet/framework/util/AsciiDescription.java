package com.doservlet.framework.util;

public enum AsciiDescription {
	/**
	 * 空字符
	 */
	NULL(0),
	/**
	 * 标题开始
	 */
	START_OF_HEADLING(1),
	/**
	 * 正文开始
	 */
	START_OF_TEXT(2),
	/**
	 * 正文结束
	 */
	END_OF_TEXT(3),
	/**
	 * 传输结束
	 */
	END_OF_TRANSMISSION(4),
	/**
	 * 请求
	 */
	ENQUIRY(5),
	/**
	 * 收到请求
	 */
	ACKNOWLEDGE(6),
	/**
	 * 响铃
	 */
	BELL(7),
	/**
	 * 退格
	 */
	BACKSPACE(8),
	/**
	 * 水平制表符
	 */
	HORIZONTAL_TAB(9),
	/**
	 * 换行符
	 */
	NEW_LINE(10),
	/**
	 * 垂直制表符
	 */
	VERTICAL_TAB(11),
	/**
	 * 换页
	 */
	NEW_PAGE(12),
	/**
	 * 回车键
	 */
	CARRIAGE_RETURN(13),
	/**
	 * 禁用切换
	 */
	SHIFT_OUT(14),
	/**
	 * 启用切换
	 */
	SHIFT_IN(15),
	/**
	 * 数据链路转义
	 */
	DATA_LINK_ESCAPE(16),
	/**
	 * 控制设备1
	 */
	DEVICE_CONTROL_1(17),
	/**
	 * 控制设备2
	 */
	DEVICE_CONTROL_2(18),
	/**
	 * 控制设备3
	 */
	DEVICE_CONTROL_3(19),
	/**
	 * 控制设备4
	 */
	DEVICE_CONTROL_4(20),
	/**
	 * 拒绝接受
	 */
	NEGATIVE_ACKNOWLEDGE(21),
	/**
	 * 同步空闲
	 */
	SYNCHRONOUS_IDLE(22),
	/**
	 * 传输块结束
	 */
	BLOCK(23),
	/**
	 * 取消
	 */
	CANCEL(24),
	/**
	 * 介质中断
	 */
	END_OF_MEDIUM(25),
	/**
	 * 替补
	 */
	SUBSTITUTE(26),
	/**
	 * 溢出
	 */
	ESCAPE(27),
	/**
	 * 文件分隔符
	 */
	FILE_SEPARATOR(28),
	/**
	 * 分组分隔符
	 */
	GROUP_SEPARATOR(29),
	/**
	 * 记录分隔符
	 */
	RECORD_SEPARATOR(30),
	/**
	 * 单元分隔符
	 */
	UNIT_SEPARATOR(31),
	/**
	 * 空格
	 */
	SPACE(32);
	private int index;

	private AsciiDescription(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
