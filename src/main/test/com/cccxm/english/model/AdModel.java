package com.cccxm.english.model;

import com.cccxm.english.bean.AdBean;
import com.cccxm.english.bean.enumeration.AdEnum;
import com.cccxm.english.contract.AdContract.IAdModel;

public class AdModel implements IAdModel {

	public AdBean getAdBean(AdEnum tag) {
		AdBean bean = new AdBean();
		bean.setImg("http://cdn.duitang.com/uploads/item/201408/23/20140823055947_8fX48.png");
		bean.setUrl("http://www.sogou.com");
		return bean;
	}

}
