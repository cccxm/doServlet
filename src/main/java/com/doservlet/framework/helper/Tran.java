package com.doservlet.framework.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * sql事务工具类
 * 
 * @author 陈小默
 *
 */
public class Tran {
	private static final Logger l = LoggerFactory.getLogger(Tran.class);
	private static final ThreadLocal<Savepoint> SAVE_POINT = new ThreadLocal<Savepoint>();

	private static final ThreadLocal<Map<String, Savepoint>> SAVE_POINT_MAP = new ThreadLocal<Map<String, Savepoint>>();

	/**
	 * 开启事务
	 */
	public static void startTransaction() {
		startTransaction(Connection.TRANSACTION_NONE);
	}

	/**
	 * 开启事务
	 * 
	 * @param connectionLevel
	 *            安全等级
	 */
	public static void startTransaction(int connectionLevel) {
		Connection connection = DatabaseHelper.getConnection();
		try {
			if (connectionLevel != Connection.TRANSACTION_NONE)
				connection.setTransactionIsolation(connectionLevel);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			l.error("start transaction error", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 设置一个未命名的回滚点
	 */
	public static void savePoint() {
		savePoint(null);
	}

	/**
	 * 设置带有名称的回滚点
	 * 
	 * @param name
	 */
	public static void savePoint(String name) {
		try {
			if (name == null) {
				SAVE_POINT.set(DatabaseHelper.getConnection().setSavepoint());
			} else {
				Map<String, Savepoint> map = SAVE_POINT_MAP.get();
				if (map == null) {
					SAVE_POINT_MAP.set(map = new HashMap<String, Savepoint>());
				}
				map.put(name, DatabaseHelper.getConnection().setSavepoint(name));
			}
		} catch (Exception e) {
			l.error("save point error", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * 如果此前没有设置回滚点，则回滚全部操作。若此前设置过未命名的回滚点，则回滚到该点。
	 */
	public static void rollback() {
		roolback(null);
	}

	/**
	 * 回滚到回滚点
	 * 
	 * @param name
	 */
	public static void roolback(String name) {
		try {
			if (name == null)
				if (SAVE_POINT.get() == null)
					DatabaseHelper.getConnection().rollback();
				else
					DatabaseHelper.getConnection().rollback(SAVE_POINT.get());
			else if (SAVE_POINT_MAP.get() != null) {
				DatabaseHelper.getConnection().rollback(
						SAVE_POINT_MAP.get().get(name));
			}
		} catch (SQLException e) {
			l.error("rollback", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 提交操作
	 */
	public static void commit() {
		try {
			DatabaseHelper.getConnection().commit();
		} catch (SQLException e) {
			l.error("commit error", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭全部连接
	 */
	public static void close() {
		DatabaseHelper.close();
	}

	/**
	 * 执行更新语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int update(String sql, Object... params) {
		return DatabaseHelper.update(sql, params);
	}

	/**
	 * 查询数据
	 * 
	 * @param sql
	 * @param param
	 * @return
	 */
	public static <T> List<T> queryEntityList(Class<T> entityClass, String sql,
			Object... params) {
		return DatabaseHelper.queryEntityList(entityClass, sql, params);
	}

	/**
	 * 插入数据
	 * 
	 * @param sql
	 * @param params
	 */
	public static void insert(String sql, Object... params) {
		update(sql, params);
	}

	/**
	 * 插入数据
	 * 
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> T insert(Class<T> entityClass, String sql,
			Object... params) {
		return DatabaseHelper.insert(entityClass, sql, params);
	}

}
