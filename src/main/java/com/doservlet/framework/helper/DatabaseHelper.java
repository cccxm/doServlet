package com.doservlet.framework.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doservlet.framework.util.PropsUtil;

public class DatabaseHelper {
	private static final Logger l = LoggerFactory
			.getLogger(DatabaseHelper.class);

	private static final QueryRunner QUERY_RUNNER;

	private static final ThreadLocal<Connection> LOCAL_CONNECTION;

	private static final DataSource DATA_SOURCE;

	static {
		Properties dbcpProperties = new PropsUtil("dbcp.properties")
				.getProperties();
		try {
			DATA_SOURCE = BasicDataSourceFactory
					.createDataSource(dbcpProperties);
		} catch (Exception e) {
			l.error("load dbcp properties failure", e);
			throw new RuntimeException(e);
		}
		QUERY_RUNNER = new QueryRunner();
		LOCAL_CONNECTION = new ThreadLocal<Connection>();
	}

	public static DataSource getDataSource() {
		return DATA_SOURCE;
	}

	/**
	 * 获得与数据库的连接，使用完毕后应该尽快关闭
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = LOCAL_CONNECTION.get();
		if (conn == null)
			try {
				conn = DATA_SOURCE.getConnection();
				LOCAL_CONNECTION.set(conn);
			} catch (SQLException e) {
				l.error("get Connection error", e);
				throw new RuntimeException();
			}
		return conn;

	}

	/**
	 * 数据库使用完毕应当立即关闭流
	 * 
	 * @throws SQLException
	 */
	protected static void close() {
		Connection connection = LOCAL_CONNECTION.get();
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
			} finally {
				connection = null;
			}
			LOCAL_CONNECTION.remove();
		}
	}

	/**
	 * 查询实体列表
	 * 
	 * @param entityClass
	 *            模型类
	 * @param sql
	 *            查询语句
	 * @param params
	 *            参数列表
	 * @return
	 */
	public static <T> List<T> queryEntityList(Class<T> entityClass, String sql,
			Object... params) {
		List<T> entityList = null;
		try {
			entityList = QUERY_RUNNER.query(getConnection(), sql,
					new BeanListHandler<T>(entityClass), params);
		} catch (Exception e) {
			l.error("query entity list failure", e);
		}
		return entityList;
	}

	/**
	 * 插入数据
	 * 
	 * @param sql
	 * @param params
	 */
	public static int insert(String sql, Object... params) {
		return update(sql, params);
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
		T t = null;
		try {
			return QUERY_RUNNER.insert(getConnection(), sql,
					new BeanHandler<T>(entityClass), params);
		} catch (SQLException e) {
			l.error("insert entity list failure", e);
		}
		return t;
	}

	/**
	 * 更新数据
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int update(String sql, Object... params) {
		try {
			return QUERY_RUNNER.update(getConnection(), sql, params);
		} catch (Exception e) {
			l.error("update failure", e);
		}
		return -1;
	}

}
