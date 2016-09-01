package com.cccxm.english.contract;

import java.io.File;

import com.doservlet.framework.bean.Data;
import com.doservlet.framework.bean.Param;
import com.doservlet.framework.bean.Resource;

public interface DownloadContract {
	public static interface IDownloadView {
		Object download(Param param);

		Data error(String msg);

		Resource success(File file);
	}

	public static interface IDownloadPresenter {
		Object Download(Param param);
	}

	public static interface IDownloadModel {
		File getResource(String fileName);
	}
}
