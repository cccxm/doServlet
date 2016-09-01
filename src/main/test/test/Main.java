package test;

import com.doservlet.framework.util.TextFileUtils;
import com.google.gson.Gson;

public class Main {
	public static void main(String[] args) throws Exception {
		String fileName = "00000040.json";
		String libName = "简单实用口语(40)";
		int level = 100;
		int score = 5;
		int count = 20;
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
		TextFileUtils out = new TextFileUtils("E:\\out", fileName);
		out.append(json);
		file.close();
		out.close();
		System.out
				.printf("insert tongue_lib(lib_name,level,score,uri,count) values(\"%s\",%d,%d,\"%s\",%d)",
						libName, level, score, fileName, count);
	}
}
