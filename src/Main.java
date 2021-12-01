package src;

import java.util.Scanner;

import src.Interpreter.Interpreter;
import src.Lexer.Lexer;
import src.Lexer.Token;
import src.Preprocessor.Preprocessor;
import src.parser.Parser;
import src.parser.Nodes.RootNode;

public class Main {

	public static boolean DEBUG = true;

	public static void main(String[] args) {

		System.out.println("Programm auswählen (standard: program.txt): ");
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();

		//Quellcode zu String
		String quellcode;
		if (input.equals(""))
			quellcode = Reader.readFile();
		else
			quellcode = Reader.readFile(input);

		Lexer lexer = new Lexer(quellcode);
		Token[] tokens = lexer.lex();

		if (DEBUG) {
			System.out.println("\nLEXER:");
			for (Token t : tokens)
				t.print();
		}

		Preprocessor pre = new Preprocessor(tokens);
		tokens = pre.process();
		if (DEBUG) {
			System.out.println("\nPREPROCESSOR:");
			for (Token t : tokens)
				t.print();
		}

		if (DEBUG)
			System.out.println("\nPARSER:");
		Parser parser = new Parser(tokens);
		RootNode root = parser.parse();

		if (DEBUG)
			System.out.println("\nINTERPETER:");
		Interpreter interpreter = new Interpreter(root);
		interpreter.run();

		System.out.println();
	}
}
