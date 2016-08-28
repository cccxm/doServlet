package com.doservlet.framework.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doservlet.framework.bean.FileParam;
import com.doservlet.framework.bean.FormParam;
import com.doservlet.framework.bean.Param;
import com.doservlet.framework.util.DataFileUtils;
import com.doservlet.framework.util.Regex;

public class UploadHelper {
	private static final Logger l = LoggerFactory.getLogger(UploadHelper.class);
	private static ServletFileUpload servletFileUpload;

	public static void init(ServletContext servletContext) {
		File repository = (File) servletContext
				.getAttribute("javax.servlet.context.tempdir");
		servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(
				DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
		int uploadLimit = ConfigHelper.getAppUploadLimit();
		if (uploadLimit != 0) {
			servletFileUpload.setFileSizeMax(1024l * 1024 * uploadLimit);
		}
	}

	/**
	 * 是否为multipart
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isMultipart(HttpServletRequest request) {
		return ServletFileUpload.isMultipartContent(request);
	}

	public static Param createParam(HttpServletRequest request)
			throws IOException {
		List<FormParam> formList = new ArrayList<FormParam>();
		List<FileParam> fileList = new ArrayList<FileParam>();
		try {
			Map<String, List<FileItem>> fileItemListMap = servletFileUpload
					.parseParameterMap(request);
			if (MapUtils.isNotEmpty(fileItemListMap)) {
				for (Map.Entry<String, List<FileItem>> fileItemListEntry : fileItemListMap
						.entrySet()) {
					String fieldName = fileItemListEntry.getKey();
					List<FileItem> fileItemList = fileItemListEntry.getValue();
					if (CollectionUtils.isNotEmpty(fileItemList)) {
						for (FileItem fileItem : fileItemList) {
							if (fileItem.isFormField()) {
								String fieldValue = fileItem.getString("utf-8");
								formList.add(new FormParam(fieldName,
										fieldValue));
							} else {
								String fileName = FilenameUtils
										.getName(new String(fileItem.getName()
												.getBytes(), "utf-8"));
								if (Regex.isNotBlank(fileName)) {
									long fileSize = fileItem.getSize();
									String contentType = fileItem
											.getContentType();
									InputStream inputStream = fileItem
											.getInputStream();
									fileList.add(new FileParam(fieldName,
											fileName, fileSize, contentType,
											inputStream));
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			l.error("create param failure", e);
			throw new RuntimeException(e);
		}
		return new Param(formList, fileList, request);
	}

	/**
	 * 上传文件
	 * 
	 * @param basePath
	 * @param fileParam
	 */
	public static void uploadFile(String basePath, FileParam fileParam) {
		DataFileUtils file = null;
		try {
			if (fileParam != null) {
				file = new DataFileUtils(basePath, fileParam.getFileName());
				file.write(fileParam.getInputStream());
			}
		} catch (Exception e) {
			l.error("upload file failure", e);
			throw new RuntimeException(e);
		} finally {
			if (file != null)
				try {
					file.close();
				} catch (IOException e) {
					file = null;
				}
		}
	}

	public static void uploadFiles(String basePath,
			List<FileParam> fileParamList) {

		if (CollectionUtils.isNotEmpty(fileParamList)) {
			for (FileParam fileParam : fileParamList) {
				uploadFile(basePath, fileParam);
			}
		}
	}
}
