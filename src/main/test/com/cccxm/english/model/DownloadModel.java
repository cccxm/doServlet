package com.cccxm.english.model;

import java.io.File;

import com.cccxm.english.contract.DownloadContract.IDownloadModel;

public class DownloadModel implements IDownloadModel {
	private static final String OUT_DIR = "E:\\out";

	public File getResource(String fileName) {
		File file = new File(OUT_DIR, fileName);
		if (file.exists())
			return file;
		return null;
	}

}
