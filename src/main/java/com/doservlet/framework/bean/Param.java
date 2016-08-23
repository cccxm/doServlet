package com.doservlet.framework.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;

import com.doservlet.framework.util.ASCII;
import com.doservlet.framework.util.AsciiDescription;
import com.doservlet.framework.util.CastUtils;

/**
 * 请求参数对象
 * 
 * @author 陈小默
 *
 */
public class Param {
	private List<FormParam> formParamList;
	private List<FileParam> fileParamList;
	private Map<String, Object> paramMap;
	private Map<String, List<FileParam>> fileMap;

	public Param(List<FormParam> formParamList) {
		super();
		this.formParamList = formParamList;
	}

	public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
		super();
		this.formParamList = formParamList;
		this.fileParamList = fileParamList;
	}

	public Map<String, Object> getFieldMap() {
		if (paramMap == null) {
			paramMap = new HashedMap<String, Object>();
			if (CollectionUtils.isNotEmpty(formParamList)) {
				for (FormParam formParam : formParamList) {
					String fieldName = formParam.getFieldName();
					Object fieldValue = formParam.getFieldValue();
					if (paramMap.containsKey(fieldName)) {
						fieldValue = paramMap.get(fieldName)
								+ ASCII.getString(AsciiDescription.GROUP_SEPARATOR)
								+ fieldValue;
					}
					paramMap.put(fieldName, fieldValue);
				}
			}
		}
		return paramMap;
	}

	public Map<String, List<FileParam>> getFileMap() {
		if (fileMap == null) {
			fileMap = new HashedMap<String, List<FileParam>>();
			if (CollectionUtils.isNotEmpty(fileParamList)) {
				for (FileParam fileParam : fileParamList) {
					String fieldName = fileParam.getFieldName();
					List<FileParam> fileParamList;
					if (fileMap.containsKey(fieldName)) {
						fileParamList = fileMap.get(fieldName);
					} else {
						fileParamList = new ArrayList<FileParam>();
					}
					fileParamList.add(fileParam);
					fileMap.put(fieldName, fileParamList);
				}
			}
		}
		return fileMap;
	}

	public List<FileParam> getFileList(String fieldName) {
		return getFileMap().get(fieldName);
	}

	public FileParam getFile(String fieldName) {
		List<FileParam> fileList = getFileList(fieldName);
		if (CollectionUtils.isNotEmpty(fileList) && fileList.size() == 1)
			return fileList.get(0);
		return null;
	}

	public Object get(String name) {
		return getFieldMap().get(name);
	}

	public String getString(String name) {
		return CastUtils.castString(get(name));
	}

	public long getLong(String name) {
		return CastUtils.castLong(get(name));
	}

	public int getInt(String name) {
		return CastUtils.castInt(get(name));
	}

	public double getDouble(String name) {
		return CastUtils.castDouble(get(name));
	}

	public boolean getBoolean(String name) {
		return CastUtils.castBoolean(get(name));
	}

	public boolean isEmpty() {
		return CollectionUtils.isEmpty(formParamList)
				&& CollectionUtils.isEmpty(fileParamList);
	}
}
