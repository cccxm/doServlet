package com.cccxm.english.view;

import com.cccxm.english.bean.AdBean;
import com.cccxm.english.contract.AdContract.IAdView;
import com.cccxm.english.model.AdModel;
import com.cccxm.english.present.AdPresent;
import com.doservlet.framework.annotation.Action;
import com.doservlet.framework.annotation.Controller;
import com.doservlet.framework.bean.Data;
import com.doservlet.framework.bean.HttpResponse;

@Controller
public class AdView implements IAdView {
	@Action("get:/welcomeAd")
	public Data welcomeAd() {
		return new AdPresent(new AdModel(), this).welcomeAd();
	}

	public Data success(AdBean bean) {
		HttpResponse<AdBean> res = new HttpResponse<AdBean>();
		res.setSuccess(true);
		res.setData(bean);
		return new Data(res);
	}

	public Data error(String message) {
		HttpResponse<AdBean> res = new HttpResponse<AdBean>();
		res.setMessage(message);
		return new Data(res);
	}

}
