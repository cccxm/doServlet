package com.doservlet.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射工具类
 * 
 * @author 陈小默
 *
 */
public final class ReflectionUtils {
	/**
	 * 创建实例
	 * 
	 * @param cls
	 * @return
	 */
	public static Object newInstance(Class<?> cls) {
		Object instance;
		try {
			instance = cls.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return instance;
	}

	/**
	 * 创建实例
	 * 
	 * @param cls
	 * @return
	 */
	public static Object newInstance(String className) {
		Class<?> loadClass = ClassUtils.loadClass(className);
		return newInstance(loadClass);
	}

	/**
	 * 安全的属性赋值方法
	 * 
	 * @param field
	 * @param object
	 * @param resultSet
	 */
	public static void set(Field field, Object object, ResultSet resultSet)
			throws Exception {
		String fieldName = field.getName();
		field.setAccessible(true);
		field.set(object, resultSet.getObject(fieldName));
	}

	/**
	 * 给预编译指令赋值
	 * 
	 * @param statement
	 * @param order
	 * @param value
	 * @throws SQLException
	 */
	public static void set(PreparedStatement statement, int order, Object value)
			throws SQLException {
		switch (getType(value.getClass())) {
		case SHORT:
			statement.setShort(order, (Short) value);
			break;
		case INT:
			statement.setInt(order, (Integer) value);
			break;
		case BYTE:
			statement.setByte(order, (Byte) value);
			break;
		case BYTES:
			statement.setBytes(order, (byte[]) value);
			break;
		case LONG:
			statement.setLong(order, (Long) value);
			break;
		case STRING:
			statement.setString(order, (String) value);
			break;
		case FLOAT:
			statement.setFloat(order, (Float) value);
			break;
		case DOUBLE:
			statement.setDouble(order, (Double) value);
			break;
		default:
			break;
		}
	}

	/**
	 * 安全的属性赋值方法
	 * 
	 * @param field
	 * @param object
	 * @param value
	 * @throws Exception
	 */
	public static void set(Field field, Object object, Object value) {
		try {
			field.setAccessible(true);
			field.set(object, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 给对象设置属性
	 * 
	 * @param filedName
	 *            属性名
	 * @param object
	 *            对象
	 * @param value
	 *            属性值
	 * @throws Exception
	 */
	public static void set(String filedName, Object object, Object value) {
		Field field;
		try {
			field = object.getClass().getDeclaredField(filedName);
			field.setAccessible(true);
			field.set(object, value);
		} catch (Exception e) {
		}
	}

	/**
	 * 从对象中获取属性值
	 * 
	 * @param fileName
	 *            属性名
	 * @param object
	 *            对象
	 * @param type
	 *            返回值类型
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String fileName, Object object, Class<T> type)
			throws Exception {
		Field field = getField(fileName, object);
		field.setAccessible(true);
		T t = null;
		if (field.getType() != type)
			throw new Exception("type not matching" + getType(field).name());
		t = (T) field.get(object);
		return t;
	}

	/**
	 * 获取类中的属性对象
	 * 
	 * @param field
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static Field getField(String field, Class<?> cls) {
		try {
			return cls.getDeclaredField(field);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取对象中的属性对象
	 * 
	 * @param field
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static Field getField(String field, Object object) {
		try {
			return object.getClass().getDeclaredField(field);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param field
	 * @return
	 */
	public static String getTypeForSql(Field field) {
		String sql = "";
		switch (getType(field)) {
		case STRING:
			sql = " VARCHAR(255) ";
			break;
		case INT:
			sql = " INTEGER ";
			break;
		case LONG:
			sql = " BIGINT ";
			break;
		default:
			break;
		}
		return sql;
	}

	/**
	 * Get attribute type
	 * 
	 * @param field
	 * @return
	 */
	public static ReflectEnum getType(Field field) {
		return getType(field.getType());
	}

	/**
	 * 获取类型
	 * 
	 * @param type
	 * @return
	 */
	public static ReflectEnum getType(Class<?> type) {
		ReflectEnum e = ReflectEnum.STRING;
		if (type == int.class || type == Integer.class)
			e = ReflectEnum.INT;
		else if (type == String.class)
			e = ReflectEnum.STRING;
		else if (type == long.class || type == Long.class)
			e = ReflectEnum.LONG;
		else if (type == short.class || type == Short.class)
			e = ReflectEnum.SHORT;
		else if (type == char.class || type == Character.class)
			e = ReflectEnum.CHAR;
		else if (type == float.class || type == Float.class)
			e = ReflectEnum.FLOAT;
		else if (type == double.class || type == Double.class)
			e = ReflectEnum.DOUBLE;
		else if (type == byte.class || type == Byte.class)
			e = ReflectEnum.BYTE;
		return e;
	}

	/**
	 * To determine whether the current field is empty
	 * 
	 * @param field
	 * @param object
	 * @return
	 */
	public static boolean isNull(Field field, Object object) {
		boolean isNull = true;
		field.setAccessible(true);
		try {
			switch (getType(field)) {
			case INT:
				isNull = field.getInt(object) == 0;
				break;
			case LONG:
				isNull = field.getLong(object) == 0;
				break;
			case STRING:
				isNull = field.get(object) == null;
				break;
			default:
				break;
			}
		} catch (Exception e) {
			isNull = true;
		}
		return isNull;
	}

	/**
	 * 当前属性是否为空值
	 * 
	 * @param field
	 * @param object
	 * @return
	 */
	public static boolean isNull(String field, Object object) {
		return isNull(getField(field, object), object);
	}

	/**
	 * 通过反射获取bean对象的属性值
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String toString(Object object) throws Exception {
		StringBuffer buffer = new StringBuffer()
				.append(object.getClass().getSimpleName()).append(":")
				.append("[");
		Field[] fields = object.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			buffer.append(toString(field, object));
			if (i != fields.length - 1) {
				buffer.append(",");
			}
		}
		return buffer.append("]").toString();
	}

	/**
	 * 获取javabean对象属性键值对
	 * 
	 * @param field
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String toString(Field field, Object object) throws Exception {
		return new StringBuffer().append(field.getName()).append("=")
				.append(field.get(object)).toString();
	}

	/**
	 * 获取javabean对象属性键值对
	 * 
	 * @param field
	 * @param object
	 * @param map
	 * @throws Exception
	 */
	public static void toKeyValue(Field field, Object object,
			Map<String, Object> map) throws Exception {
		field.setAccessible(true);
		map.put(field.getName(), field.get(object));
	}

	/**
	 * 获取Javabean属性键值对
	 * 
	 * @param object
	 */
	public static Map<String, Object> getMap(Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Field field : object.getClass().getDeclaredFields()) {
			try {
				toKeyValue(field, object, map);
			} catch (Exception e) {
			}
		}
		return map;
	}

	/**
	 * 调用方法
	 * 
	 * @param object
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(Object object, Method method,
			Object... args) {
		Object result;
		try {
			method.setAccessible(true);
			int count = method.getParameterCount();
			if (count == 0)
				result = method.invoke(object);
			else
				result = method.invoke(object, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

}
