package src;

import java.io.File;
import java.util.Scanner;

public class Reader {

	private static String fileName = "program.txt";

	public static String readFile() {
		return readFile(fileName);
	}

	public static String readFile(String fileName) {
		File path = new File("src/examples/" + fileName);
		StringBuilder builder = new StringBuilder();
		try {

			Scanner scanner = new Scanner(path).useDelimiter("(\\b|\\B)");

			while (scanner.hasNextLine())
				builder.append(scanner.nextLine() + "\n");
			builder.append("!");
			scanner.close();
		} catch (Exception e) {
			System.out.println("Die Datei '" + fileName + "' konnte nicht gefunden werden");
		}
		return builder.toString();
	}
}
