package com.cccxm.english.model;

import java.util.List;

import com.cccxm.english.bean.TongueLib;
import com.cccxm.english.contract.TongueLibContract.ITongueLibModel;
import com.doservlet.framework.helper.DatabaseHelper;

public class TongueLibModel implements ITongueLibModel {

	public List<TongueLib> getList(int page, int count) {
		List<TongueLib> list = DatabaseHelper.queryEntityList(TongueLib.class,
				"select * from tongue_lib limit ?,?", page * count, count);
		return list;
	}

}
