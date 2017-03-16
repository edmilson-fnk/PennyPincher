package inout;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Operations {

	public static void saveFile(String content, String path) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(path);
		out.println(content);
		out.close();
	}

}
