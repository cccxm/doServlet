package com.cccxm.english.view;

import java.util.List;

import com.cccxm.english.bean.TongueLib;
import com.cccxm.english.contract.TongueLibContract.ITongueLibPresent;
import com.cccxm.english.contract.TongueLibContract.ITongueLibView;
import com.cccxm.english.model.TongueLibModel;
import com.cccxm.english.present.TongueLibPresenter;
import com.doservlet.framework.annotation.Action;
import com.doservlet.framework.annotation.Controller;
import com.doservlet.framework.bean.Data;
import com.doservlet.framework.bean.HttpResponse;
import com.doservlet.framework.bean.Param;

@Controller
public class TongueLibView implements ITongueLibView {

	@Action("get:/user/lib/tongue/list")
	public Data getList(Param param) {
		System.out.println("====in====");// TODO
		ITongueLibPresent presenter = new TongueLibPresenter(
				new TongueLibModel(), this);
		return presenter.getList(param);
	}

	public Data createResponse(List<TongueLib> list) {
		HttpResponse<List<TongueLib>> response = new HttpResponse<List<TongueLib>>();
		response.setSuccess(true);
		response.setData(list);
		return new Data(response);
	}

	public Data error(String message) {
		HttpResponse<String> response = new HttpResponse<String>();
		response.setSuccess(true);
		response.setMessage(message);
		return new Data(response);
	}

}
