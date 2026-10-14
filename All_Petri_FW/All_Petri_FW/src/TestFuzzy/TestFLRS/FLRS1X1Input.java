package TestFuzzy.TestFLRS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FLRS1X1Input {
	public static void main(String[] args) throws InterruptedException, IOException {
		File file = new File("C:\\Users\\Melisa\\Desktop\\AN4\\DCS\\project\\test3.txt");
		Files.deleteIfExists(file.toPath());
		FileWriter fw = new FileWriter(file.getPath());
		Float f = -1f;
		for (int i = 0; i < 100; i++) {
		
			fw.write("P1:"+f+"F\n");
			f += 0.00001f;
		}
		fw.close();
		System.out.println("Done!");
	}
}
