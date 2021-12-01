package src.Lexer;

public enum TokenType {
	NUMBER,
	//Operations
	PUSH, POP,
	// Arithmetic 
	ADD, SUB, MUL, DIV,
	//Advamced
	POW, SWAP, MOD, CEIL, FLOOR, ROUND, DOUBLE,
	//MEMORY
	STORE, LOAD,
	//
	EOF, ERROR, PREPROCESSOR, IDENTIFIER, OPEN_BRACKET, CLOSE_BRACKET
}