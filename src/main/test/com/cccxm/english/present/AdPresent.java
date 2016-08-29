package com.cccxm.english.present;

import com.cccxm.english.bean.AdBean;
import com.cccxm.english.bean.enumeration.AdEnum;
import com.cccxm.english.contract.AdContract.IAdModel;
import com.cccxm.english.contract.AdContract.IAdPresent;
import com.cccxm.english.contract.AdContract.IAdView;
import com.doservlet.framework.bean.Data;

public class AdPresent implements IAdPresent {
	private IAdModel model;
	private IAdView view;

	public AdPresent(IAdModel model, IAdView view) {
		super();
		this.model = model;
		this.view = view;
	}

	public Data welcomeAd() {
		AdBean bean = model.getAdBean(AdEnum.welcom);
		if (bean != null) {
			return view.success(bean);
		} else {
			return view.error("no ad");
		}
	}
}
