package src.Preprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import src.Main;
import src.Lexer.Token;
import src.Lexer.TokenType;

/*
TODO
Right now this is just a quick solution for experimental functionality. There is no error checking
*/
public class Preprocessor {

	private Token[] tokens;
	private int index = -1;
	private Token current;
	private Map<String, Double> macros = new HashMap<>();
	private ArrayList<Token> buffer = new ArrayList<>();

	public Preprocessor(Token[] tokens) {
		this.tokens = tokens;
		advance();
	}

	private void advance() {
		index++;
		if (index < tokens.length)
			current = tokens[index];
	}

	public Token[] process() {

		while (index < tokens.length) {
			if (current.type == TokenType.PREPROCESSOR)
				buildPreprocessorCommand();
			else if (current.type == TokenType.IDENTIFIER) {
				evalIdentifier();
			} else {
				buffer.add(current);
				advance();
			}
		}

		Token[] arr = new Token[buffer.size()];
		buffer.toArray(arr);
		return arr;
	}

	private void buildPreprocessorCommand() {
		advance();

		switch (current.value) {
		case "DEFINE":
			buildDefine();
		}
	}

	private void evalIdentifier() {
		switch (current.value) {
		case "RA" -> insertNumber(0);
		case "RB" -> insertNumber(1);
		case "RC" -> insertNumber(2);
		case "RD" -> insertNumber(3);
		default -> insertMacro();
		}
	}

	//Commands
	private void buildDefine() {
		advance();
		String macroName = current.value;
		advance();
		double macroValue = Double.parseDouble(current.value);
		if (Main.DEBUG)
			System.out.println("Initialized macro '" + macroName + "', " + macroValue);

		macros.put(macroName, macroValue);
		advance();
	}

	//INSERTING MACROS
	private void insertMacro() {
		if (macros.containsKey(current.value)) {
			double value = macros.get(current.value);
			buffer.add(new Token(TokenType.NUMBER, "" + value));
		}
		advance();
	}

	private void insertNumber(int value) {
		buffer.add(new Token(TokenType.NUMBER, "" + value));
		advance();
	}
}
