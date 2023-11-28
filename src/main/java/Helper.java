import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Helper {

	void rewriteLetters(String in, String out) {
		Map<String, String> map = loadMap("mapaPismena.csv", false, false);
		Scanner sc = null;
		PrintWriter pw = null;
		try {
			sc = new Scanner(new File(in));
			pw = new PrintWriter(new File(out));
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				Scanner lineReader = new Scanner(line);
				while (lineReader.hasNext()) {
					String word = lineReader.next();
					String[] splits = word.split("#");
					StringBuilder result = new StringBuilder();
					for (String split : splits) {
						if (split.length() == 0) {
							continue;
						}
						result.append("#" + (map.get(split) != null ? map.get(split) : split));
					}
					pw.print(result + " ");
				}
				pw.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sc != null)
				sc.close();
			if (pw != null)
				pw.close();
		}
	}

	void removeHash(String in, String out) {
		Scanner sc = null;
		PrintWriter pw = null;
		try {
			sc = new Scanner(new File(in));
			pw = new PrintWriter(new File(out));
			while (sc.hasNextLine()) {
				String riadok = sc.nextLine();
				riadok = riadok.replaceAll("#", "");
				pw.println(riadok);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sc != null)
				sc.close();
			if (pw != null)
				pw.close();
		}
	}

	void capitalizeAfterDot( String in, String out) {
		Scanner sc = null;
		PrintWriter pw = null;
		try {
			sc = new Scanner(new File(in));
			pw = new PrintWriter(new File(out));
			boolean capitalizeNextWord = true;
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				Scanner lineReader = new Scanner(line);
				while (lineReader.hasNext()) {
					String word = lineReader.next();
					if (capitalizeNextWord) {
						if (word.charAt(0) == 'q') {
							word = 'q' + word.substring(1, 2).toUpperCase() + word.substring(2);//capitalize plain java
						} else {
							word = word.substring(0, 1).toUpperCase() + word.substring(1);//capitalize plain java
						}
						capitalizeNextWord = false;
					}
					pw.print(word + " ");
					if (word.contains(".")||word.contains("?")) {
						capitalizeNextWord = true;
					}
				}
				pw.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sc != null)
				sc.close();
			if (pw != null)
				pw.close();
		}
	}

	void removeBrackets(String in, String out) {
		Scanner sc = null;
		PrintWriter pw = null;
		try {
			sc = new Scanner(new File(in));
			pw = new PrintWriter(new File(out));
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				Scanner lineReader = new Scanner(line);
				boolean skip = false;
				while (lineReader.hasNext()) {
					String word = lineReader.next();
					if (word.contains("(") && word.contains(")")) {
						continue;
					}
					if (word.contains("(")) {
						skip = true;//skip the brackets words
					}
					if (word.contains(")")) {
						skip = false;//skip the brackets words
						continue;
					}
					if (skip) {
						continue;
					}
					pw.print(word + " ");
				}
				pw.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sc != null)
				sc.close();
			if (pw != null)
				pw.close();
		}
	}

	private Map<String, String> loadMap(String fileName, boolean matchingBeginEnd, boolean ignoreCase) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(getClass().getResourceAsStream(fileName));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith("#")) {
					continue;//line comments
				}
				Scanner lineReader = new Scanner(line);
				String key = lineReader.next();
				key = key.replace("\uFEFF", "");
				if (lineReader.hasNext()) {
					String value = lineReader.next();
					//todo male vs velke pismena na zaciatku slova?
					if (value.startsWith("/")) {
						value = "";//ignore - replace with empty
					}
					if (ignoreCase) {
						key = "(?i)" + key;
					}
					if (matchingBeginEnd) {
						key = "^" + key + "$";
					}
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		return map;
	}

	void rewriteWithMap(String tableSource, String in, String out) {
		Scanner sc = null;
		PrintWriter pw = null;
		try {
			Map<String, String> table = loadMap(tableSource, false, false);
			sc = new Scanner(new File (in));
			pw = new PrintWriter(new File(out));
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				Scanner lineReader = new Scanner(line);
				while (lineReader.hasNext()) {
					String word = lineReader.next();
					for (Map.Entry<String, String> entry : table.entrySet()) {
						word = word.replaceAll(entry.getKey(), entry.getValue());
					}
					pw.print(word + " ");
				}
				pw.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sc != null)
				sc.close();
			if (pw != null)
				pw.close();
		}
	}

	void markTitlas(String in, String out) {
		Scanner sc = null;
		PrintWriter pw = null;
		try {
			sc = new Scanner(new File(in));
			pw = new PrintWriter(new File(out));
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				Scanner lineReader = new Scanner(line);
				boolean skip = false;
				while (lineReader.hasNext()) {
					String word = lineReader.next();
					if (word.contains("x")) {
						pw.print("> ");
					}
					pw.print(word + " ");
				}
				pw.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sc != null)
				sc.close();
			if (pw != null)
				pw.close();
		}
	}

	void rewriteTitlas(String tableSource, String in, String newFile, boolean alwaysUpper) {
		Scanner sc = null;
		PrintWriter pw = null;
		try {
			Map<String, String> table = loadMap(tableSource, true, true);
			sc = new Scanner(new File(in));
			pw = new PrintWriter(newFile);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				Scanner lineReader = new Scanner(line);
				while (lineReader.hasNext()) {
					String word = lineReader.next();
					boolean lastCharacterCutted = false;
					char lastCharacter = word.charAt(word.length() - 1);
					if (lastCharacter == ':' || lastCharacter == ';' || lastCharacter == '.' || lastCharacter == ',') {
						word = word.substring(0, word.length() - 1);
						lastCharacterCutted = true;
					}
					for (Map.Entry<String, String> entry : table.entrySet()) {
						String wordBefore = word;
						boolean isUpper = Character.isUpperCase(word.charAt(0));
						word = word.replaceAll(entry.getKey(), entry.getValue());
						if (!word.equals(wordBefore)) {//if change was made by replaceAll
							if (isUpper || alwaysUpper) {
								word = toCamelCase(word);
							}
						}
					}
					if (lastCharacterCutted) {
						word = word + lastCharacter;
					}
					pw.print(word + " ");
				}
				pw.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sc != null)
				sc.close();
			if (pw != null)
				pw.close();
		}
	}

	public static String toCamelCase(final String init) {
		if (init == null)
			return null;

		final StringBuilder ret = new StringBuilder(init.length());

		for (final String word : init.split(" ")) {
			if (!word.isEmpty()) {
				ret.append(Character.toUpperCase(word.charAt(0)));
				ret.append(word.substring(1).toLowerCase());
			}
			if (!(ret.length() == init.length()))
				ret.append(" ");
		}

		return ret.toString();
	}
}