import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

public class RunnerTest {

	@Test
	public void psalm103()
			throws IOException {
		final String fileNameOrig = "psalm103_orig.txt";
		final String fileNameForComparision = "psalm103_test.txt";
		final String workingDir = "tmp/";
		new File(workingDir).mkdirs();
		File fileOrigInWorkDir = new File(workingDir, fileNameOrig);
		copyFileUsingStream(getClass().getResourceAsStream(fileNameOrig), fileOrigInWorkDir);
		new File(workingDir, fileNameForComparision).createNewFile();
		copyFileUsingStream(getClass().getResourceAsStream(fileNameForComparision), new File(workingDir, fileNameForComparision));

		String[] file = { fileOrigInWorkDir.getAbsolutePath() };
		Runner.main(file);
		Scanner sc = null;
		Scanner scTest = null;
		try {
			sc = new Scanner(new File(workingDir, fileNameOrig + "9.txt"));
			scTest = new Scanner(new File(workingDir, fileNameForComparision));
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String lineTest = scTest.nextLine();
				Scanner lineReader = new Scanner(line);
				Scanner lineReaderForComparision = new Scanner(lineTest);
				while (lineReader.hasNext()) {
					String word = lineReader.next();
					String wordTest = lineReaderForComparision.next();
					System.out.println(wordTest + "/" + word);
					Assert.assertTrue(wordTest + "/" + word, wordTest.equalsIgnoreCase(word));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sc != null)
				sc.close();
		}
	}

	private static void copyFileUsingStream(InputStream is, File dest)
			throws IOException {
		OutputStream os = null;
		try {
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
	}
}