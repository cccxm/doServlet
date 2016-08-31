package test;

import com.doservlet.framework.util.TextFileUtils;
import com.google.gson.Gson;

public class Main {
	public static void main(String[] args) throws Exception {
		TextFileUtils file = new TextFileUtils("C:\\Users\\chenj_000\\Desktop",
				"test.txt");
		TongueList list = new TongueList();
		while (file.hasNextLine()) {
			String en = file.nextLine();
			String ch = file.nextLine();
			en = en.substring(en.indexOf(' ') + 1);
			Tongue t = new Tongue();
			t.setCh(ch);
			t.setEn(en);
			list.addList(t);
		}
		String json = new Gson().toJson(list);
		TextFileUtils out = new TextFileUtils(
				"C:\\Users\\chenj_000\\Desktop\\out", "00000001.json");
		out.append(json);
		file.close();
		out.close();
	}
}
