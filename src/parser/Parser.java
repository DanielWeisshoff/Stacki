package src.parser;

import src.parser.Nodes.*;
import src.Main;
import src.Lexer.Token;
import src.Lexer.TokenType;

public class Parser {

	private Token[] tokens;
	private int index = -1;
	private Token currentToken;

	//Parse Tree
	private RootNode root = new RootNode();

	public Parser(Token[] tokens) {
		this.tokens = tokens;
		advance();
	}

	private void advance() {
		index++;
		currentToken = tokens[index];
		if (Main.DEBUG)
			System.out.println(currentToken.type);
	}

	public RootNode parse() {
		Node n;
		while (currentToken.type != TokenType.EOF) {
			n = buildInstruction();
			root.add(n);

		}
		return root;
	}

	// <Operator> <Number>
	private Node buildInstruction() {
		TokenType type = currentToken.type;
		advance();

		OperationType operation;
		if (currentIsNumber()) {
			NumberNode parameter = new NumberNode(getNumber());
			//OPERATIONS WITH PARAMETER
			switch (type) {
				case PUSH -> operation = OperationType.PUSH;
				case ADD -> operation = OperationType.ADD;
				case SUB -> operation = OperationType.SUB;
				case MUL -> operation = OperationType.MUL;
				case DIV -> operation = OperationType.DIV;
				case POW -> operation = OperationType.POW;
				case MOD -> operation = OperationType.MOD;
				case STORE -> operation = OperationType.STORE;
				case LOAD -> operation = OperationType.LOAD;
				default -> operation = OperationType.ERROR;
			}
			return new OperationNode(operation, parameter);
		} else {
			//OPERATIONS WITHOUT PARAMETER
			switch (type) {
				case POP -> operation = OperationType.POP;
				case ADD -> operation = OperationType.ADDSTACK;
				case SUB -> operation = OperationType.SUBSTACK;
				case MUL -> operation = OperationType.MULSTACK;
				case DIV -> operation = OperationType.DIVSTACK;
				case SWAP -> operation = OperationType.SWAP;
				case MOD -> operation = OperationType.MODSTACK;
				case CEIL -> operation = OperationType.CEIL;
				case FLOOR -> operation = OperationType.FLOOR;
				case ROUND -> operation = OperationType.ROUND;
				case DOUBLE -> operation = OperationType.DOUBLE;
				default -> operation = OperationType.ERROR;
			}
			return new OperationNode(operation);
		}
	}

	private boolean currentIsNumber() {
		return currentToken.type == TokenType.NUMBER;
	}

	private double getNumber() {
		double value = Double.parseDouble(currentToken.getValue());
		advance();
		return value;
	}
}
