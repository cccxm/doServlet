package com.cccxm.english.contract;

import com.cccxm.english.bean.AdBean;
import com.cccxm.english.bean.enumeration.AdEnum;
import com.doservlet.framework.bean.Data;

public class AdContract {
	public interface IAdView {
		Data welcomeAd();

		Data success(AdBean bean);

		Data error(String message);
	}

	public interface IAdPresent {
		Data welcomeAd();
	}

	public interface IAdModel {
		/**
		 * 根据标签选择响应广告内容
		 * 
		 * @param tag
		 * @return
		 */
		AdBean getAdBean(AdEnum tag);
	}
}
