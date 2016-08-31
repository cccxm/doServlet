package com.cccxm.english.contract;

import java.util.List;

import com.cccxm.english.bean.TongueLib;
import com.doservlet.framework.bean.Data;
import com.doservlet.framework.bean.Param;

public interface TongueLibContract {
	public static interface ITongueLibView {
		Data getList(Param param);

		Data createResponse(List<TongueLib> list);

		Data error(String message);
	}

	public static interface ITongueLibPresent {
		Data getList(Param param);
	}

	public static interface ITongueLibModel {
		List<TongueLib> getList(int page, int count);
	}
}
