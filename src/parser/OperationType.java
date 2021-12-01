package src.parser;

public enum OperationType {
	PUSH, POP,
	// Arithmetic 
	ADD, SUB, MUL, DIV,
	//Stackbased versions
	ADDSTACK, SUBSTACK, MULSTACK, DIVSTACK, MODSTACK,
	//Advanced
	POW, SWAP, MOD, CEIL, FLOOR, ROUND, DOUBLE,
	//MEMORY
	STORE, LOAD,
	//
	ERROR
}
