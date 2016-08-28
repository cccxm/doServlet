package com.doservlet.framework.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;

import com.doservlet.framework.bean.FormParam;
import com.doservlet.framework.bean.Param;
import com.doservlet.framework.util.ASCII;
import com.doservlet.framework.util.AsciiDescription;
import com.doservlet.framework.util.CodecUtil;
import com.doservlet.framework.util.Regex;
import com.doservlet.framework.util.StreamUtil;

public class RequestHelper {
	public static Param createParam(HttpServletRequest request)
			throws IOException {
		List<FormParam> formParamList = new ArrayList<FormParam>();
		formParamList.addAll(parseParameterNames(request));
		formParamList.addAll(parseInputStream(request));
		return new Param(formParamList, request);
	}

	private static List<FormParam> parseParameterNames(
			HttpServletRequest request) {
		List<FormParam> formParamList = new ArrayList<FormParam>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String fieldName = parameterNames.nextElement();
			String[] fieldValues = request.getParameterValues(fieldName);
			if (ArrayUtils.isNotEmpty(fieldValues)) {
				Object fieldValue;
				if (fieldValues.length == 1) {
					fieldValue = fieldValues[0];
				} else {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < fieldValues.length; i++) {
						sb.append(fieldValues[i]);
						if (i != fieldValues.length - 1) {
							sb.append(ASCII
									.getChar(AsciiDescription.GROUP_SEPARATOR));
						}
					}
					fieldValue = sb.toString();
				}
				formParamList.add(new FormParam(fieldName, fieldValue));
			}
		}
		return formParamList;
	}

	private static List<FormParam> parseInputStream(HttpServletRequest request)
			throws IOException {
		List<FormParam> formParamList = new ArrayList<FormParam>();
		String body = CodecUtil.decodeURL(StreamUtil.getString(request
				.getInputStream()));
		if (!Regex.isBlank(body)) {
			String[] params = body.split("&");
			if (ArrayUtils.isNotEmpty(params)) {
				for (String param : params) {
					String[] array = param.split("=");
					if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
						formParamList.add(new FormParam(array[0], array[1]));
					}
				}
			}
		}
		return formParamList;
	}
}
