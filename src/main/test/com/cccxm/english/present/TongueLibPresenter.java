package com.cccxm.english.present;

import java.util.List;

import com.cccxm.english.bean.TongueLib;
import com.cccxm.english.contract.TongueLibContract.ITongueLibModel;
import com.cccxm.english.contract.TongueLibContract.ITongueLibPresent;
import com.cccxm.english.contract.TongueLibContract.ITongueLibView;
import com.doservlet.framework.bean.Data;
import com.doservlet.framework.bean.Param;

public class TongueLibPresenter implements ITongueLibPresent {
	private ITongueLibModel model;
	private ITongueLibView view;

	public TongueLibPresenter(ITongueLibModel model, ITongueLibView view) {
		super();
		this.model = model;
		this.view = view;
	}

	public Data getList(Param param) {
		int page = 0;
		try {
			page = param.getInt("page");
		} catch (Exception e) {
			return view.error("参数错误");
		}
		if (page < 0) {
			return view.error("参数错误");
		} else {
			List<TongueLib> list = model.getList(page, 20);
			return view.createResponse(list);
		} 
	}

}
