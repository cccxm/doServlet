package com.cccxm.english.present;

import java.io.File;

import com.cccxm.english.contract.DownloadContract.IDownloadModel;
import com.cccxm.english.contract.DownloadContract.IDownloadPresenter;
import com.cccxm.english.contract.DownloadContract.IDownloadView;
import com.doservlet.framework.bean.Param;

public class DownloadPresenter implements IDownloadPresenter {
	private IDownloadModel model;
	private IDownloadView view;

	public DownloadPresenter(IDownloadModel model, IDownloadView view) {
		super();
		this.model = model;
		this.view = view;
	}

	public Object Download(Param param) {
		String fileName = null;
		try {
			fileName = param.getString("uri");
		} catch (Exception e) {
			return view.error("参数错误");
		}
		File file = model.getResource(fileName);
		if (file == null) {
			return view.error("文件不存在");
		} else {
			return view.success(file);
		}
	}

}
