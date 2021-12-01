package src.Lexer;

public class Token {

	public TokenType type;
	public String value;
	private boolean isNumber = false;

	public Token(TokenType type) {
		this(type, null);
	}

	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;

		if (this.type == TokenType.NUMBER)
			isNumber = true;
	}

	public void print() {
		if (value == null)
			System.out.println("[" + type + "]");
		else if (isNumber)
			System.out.println("[" + type + ", " + value + "]");
		else //Text
			System.out.println("[" + type + ", \"" + value + "\"]");

	}

	public String getValue() {
		return value;
	}
}