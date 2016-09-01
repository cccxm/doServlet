package com.doservlet.framework.bean;

import java.io.File;

public class Resource {
	private File file;
	private String fileName;
	private String attachment;
	private String application;

	public Resource(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		if (fileName == null) {
			return file.getName();
		} else {
			return fileName;
		}
	}

	public Resource setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public String getAttachment() {
		return attachment;
	}

	public Resource setAttachment(String attachment) {
		this.attachment = attachment;
		return this;
	}

	public String getApplication() {
		return application;
	}

	public Resource setApplication(String application) {
		this.application = application;
		return this;
	}

}
