package com.cccxm.english.view;

import java.io.File;

import com.cccxm.english.contract.DownloadContract.IDownloadView;
import com.cccxm.english.model.DownloadModel;
import com.cccxm.english.present.DownloadPresenter;
import com.doservlet.framework.annotation.Action;
import com.doservlet.framework.annotation.Controller;
import com.doservlet.framework.bean.Data;
import com.doservlet.framework.bean.HttpResponse;
import com.doservlet.framework.bean.Param;
import com.doservlet.framework.bean.Resource;

@Controller
public class DownloadView implements IDownloadView {

	@Action("get:/user/lib/download")
	public Object download(Param param) {
		DownloadPresenter presenter = new DownloadPresenter(
				new DownloadModel(), this);
		return presenter.Download(param);
	}

	public Data error(String msg) {
		HttpResponse<String> res = new HttpResponse<String>();
		res.setSuccess(false);
		res.setMessage(msg);
		return new Data(res);
	}

	public Resource success(File file) {
		return new Resource(file).setAttachment("lib.json");
	}

}
