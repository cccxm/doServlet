package test;

import java.util.LinkedList;
import java.util.List;

public class TongueList {
	private List<Tongue> list;

	public List<Tongue> getList() {
		return list;
	}

	public void addList(Tongue tongue) {
		if(list==null){
			list = new LinkedList<Tongue>();
		}
		list.add(tongue);
	}
	
}
