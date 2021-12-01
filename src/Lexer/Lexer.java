package src.Lexer;

import java.util.ArrayList;

public class Lexer {

	private String text;
	private int index = -1;
	private char currentChar;

	//TODO kann col und row nicht richtig angeben, da newlines nicht erkannt werden
	//Error logging
	private int column = 1;
	private int row = 1;

	public Lexer(String text) {
		this.text = text;
		advance();
	}

	private void advance() {
		index++;
		column++;
		if (index < text.length())
			currentChar = text.charAt(index);
	}

	public Token[] lex() {

		ArrayList<Token> tokens = new ArrayList<>();
		Token t;
		do {
			t = nextToken();

			if (t.type == TokenType.ERROR) {
				System.out.printf("ERROR: %s at [%d,%d]\n", t.value, column, row);
				System.exit(1);
			}

			tokens.add(t);
		} while (t.type != TokenType.EOF);

		//Convert list to array
		Token[] tokenArr = new Token[tokens.size()];
		tokens.toArray(tokenArr);

		return tokenArr;
	}

	public Token nextToken() {
		//skipping whitespace and comments
		if (isWhiteSpace(currentChar) && currentChar != '!')
			skipWhiteSpace();

		if (currentChar == ';') {
			skipComment();
			return nextToken();
		} else if (isEOF())
			return new Token(TokenType.EOF);
		else if (!isCharSet(currentChar))
			return new Token(TokenType.ERROR, "unknown symbol '" + currentChar + "'");
		else if (currentChar == '#')
			return buildPreprocessorToken();
		else if (isLetter(currentChar))
			return buildOperatorToken();
		else if (isNumeric(currentChar) || currentChar == '-' || currentChar == '.')
			return buildNumberToken();
		else if (currentChar == '[') {
			advance();
			return new Token(TokenType.OPEN_BRACKET);
		} else if (currentChar == ']') {
			advance();
			return new Token(TokenType.CLOSE_BRACKET);
		} else
			return new Token(TokenType.ERROR,
					"couldn't build token '" + (char) currentChar + "' ASCII: " + (int) currentChar);
	}

	//===== TOKEN BUILDING =====//

	private Token buildOperatorToken() {

		StringBuilder builder = new StringBuilder();
		do {
			builder.append(currentChar);
			advance();
		} while (isLetter(currentChar) && !isEOF());

		String operator = builder.toString().toUpperCase();
		switch (operator) {
			case "PUSH":
				return new Token(TokenType.PUSH);
			case "POP":
				return new Token(TokenType.POP);
			case "ADD":
				return new Token(TokenType.ADD);
			case "SUB":
				return new Token(TokenType.SUB);
			case "MUL":
				return new Token(TokenType.MUL);
			case "DIV":
				return new Token(TokenType.DIV);
			case "POW":
				return new Token(TokenType.POW);
			case "SWAP":
				return new Token(TokenType.SWAP);
			case "MOD":
				return new Token(TokenType.MOD);
			case "CEIL":
				return new Token(TokenType.CEIL);
			case "FLOOR":
				return new Token(TokenType.FLOOR);
			case "ROUND":
				return new Token(TokenType.ROUND);
			case "STORE":
				return new Token(TokenType.STORE);
			case "LOAD":
				return new Token(TokenType.LOAD);
			case "DOUBLE":
				return new Token(TokenType.DOUBLE);
			default:
				return new Token(TokenType.IDENTIFIER, operator);
		}
	}

	private Token buildNumberToken() {
		boolean hasFloatingPoint = false;

		StringBuilder builder = new StringBuilder();
		if (currentChar == '.')
			hasFloatingPoint = true;

		builder.append(currentChar);
		advance();

		while ((isNumeric(currentChar) || currentChar == '.') && !isEOF()) {
			//Error check for multiple floating points
			if (currentChar == '.')
				if (hasFloatingPoint)
					return new Token(TokenType.ERROR, "a number can only have one floating point");
				else
					hasFloatingPoint = true;

			builder.append(currentChar);
			advance();
		}
		return new Token(TokenType.NUMBER, builder.toString());
	}

	private Token buildPreprocessorToken() {
		advance();
		return new Token(TokenType.PREPROCESSOR);
	}

	private void skipComment() {
		do {
			advance();
		} while (currentChar != '\n');
	}

	private void skipWhiteSpace() {
		while (isWhiteSpace(currentChar) && !isEOF()) {
			if (currentChar == '\n') {
				advance();
				column = 1;
				row++;
			} else
				advance();
		}
	}

	//char checking
	private boolean isWhiteSpace(char c) {
		return (c == ' ' || c == '\n' || c == '\t');
	}

	private boolean isLetter(char c) {
		return CharSet.alphabetic.contains(c);
	}

	private boolean isNumeric(char c) {
		return CharSet.numeric.contains(c);
	}

	private boolean isNumberOrLetter(char c) {
		return CharSet.letterOrNumber.contains(c);
	}

	private boolean isCharSet(char c) {
		return CharSet.charSet.contains(c);
	}

	private boolean isEOF() {
		return currentChar == '!';
	}

}
