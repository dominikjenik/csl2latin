public class Runner {

	public static final String FOLDER = "prepis/";
	public static final String FORMAT = ".txt";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// magic conversion https://www.branah.com/unicode-converter
		//https://en.wikipedia.org/wiki/List_of_Unicode_characters
		//https://en.wikipedia.org/wiki/Greek_diacritics
		//https://ru.wikipedia.org/wiki/%D0%A1%D1%82%D0%B0%D1%80%D0%BE%D1%81%D0%BB%D0%B0%D0%B2%D1%8F%D0%BD%D1%81%D0%BA%D0%B0%D1%8F_%D0%B0%D0%B7%D0%B1%D1%83%D0%BA%D0%B0

		Helper t = new Helper();
		//		String fileName = "vecerna/zaloha_celej_vecerne_hash";//https://azbyka.ru/bogosluzhenie/chasoslov/chas_ucs.shtml#16
		//		String fileName = "vecerna/uvodny_psalm_vecerne";
		if (args.length == 0) {
			args = new String[1];
			args[0] = "work_dir/in.txt";
		}
		String f1 = args[0];
		String f2 = args[0] + 2 + FORMAT;
		String f3 = args[0] + 3 + FORMAT;
		String f4 = args[0] + 4 + FORMAT;
		String f5 = args[0] + 5 + FORMAT;
		String f6 = args[0] + 6 + FORMAT;
		String f7 = args[0] + 7 + FORMAT;
		String f8 = args[0] + 8 + FORMAT;
		String f9 = args[0] + 9 + FORMAT;

		t.rewriteLetters(f1, f2);
		t.removeBrackets(f2, f3);
		t.removeHash(f3, f4);
		t.capitalizeAfterDot(f4, f5);
		t.rewriteWithMap("mapaFrazy.csv", f5, f6);
		//t.markTitlas(f6, f7);
		f7 = f6;
		t.rewriteTitlas("mapaTitly.csv", f7, f8, false);
		t.rewriteTitlas("mapaVelkeTitly.csv", f8, f9, true);

	}

}
