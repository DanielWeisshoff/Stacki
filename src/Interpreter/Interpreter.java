package src.Interpreter;

import src.parser.Nodes.Node;
import java.util.Stack;
import src.parser.Nodes.*;

public class Interpreter {

	//Memory
	private Stack<Double> stack = new Stack<>();
	private double[] registers = new double[4];

	private RootNode root;

	public Interpreter(RootNode root) {
		this.root = root;
	}

	public void run() {
		for (Node n : root.children)
			if (n instanceof OperationNode)
				interpretOperation((OperationNode) n);

		popStack();
	}

	private void interpretOperation(OperationNode n) {

		switch (n.type) {
			//without parameter
			case POP -> pop();
			case ADDSTACK -> addStack();
			case SUBSTACK -> subStack();
			case MULSTACK -> mulStack();
			case DIVSTACK -> divStack();
			case SWAP -> swap();
			case MODSTACK -> modStack();
			case CEIL -> ceil();
			case FLOOR -> floor();
			case ROUND -> round();
			case DOUBLE -> Double();
			//with parameter
			case PUSH -> push(n.getValue());
			case ADD -> add(n.getValue());
			case SUB -> sub(n.getValue());
			case MUL -> mul(n.getValue());
			case DIV -> div(n.getValue());
			case POW -> pow(n.getValue());
			case MOD -> mod(n.getValue());
			case STORE -> store((int) n.getValue());
			case LOAD -> load((int) n.getValue());
			default -> System.out.println("ERROR: unknown operation ");
		}

	}

	//OPERATIONS without parameter
	private void pop() {
		stack.pop();
	}

	private void addStack() {

		double val1 = stack.pop();
		double val2 = stack.pop();
		stack.add(val1 + val2);
	}

	private void subStack() {
		double val1 = stack.pop();
		double val2 = stack.pop();
		stack.add(val1 - val2);
	}

	private void mulStack() {
		double val1 = stack.pop();
		double val2 = stack.pop();
		stack.add(val1 * val2);
	}

	private void divStack() {
		double val1 = stack.pop();
		double val2 = stack.pop();
		stack.add(val1 / val2);
	}

	private void swap() {
		double val1 = stack.pop();
		double val2 = stack.pop();

		stack.add(val1);
		stack.add(val2);
	}

	private void popStack() {
		while (!stack.empty())
			System.out.println(stack.pop());
	}

	private void modStack() {
		double val1 = stack.pop();
		double val2 = stack.pop();

		stack.add(val1 % val2);
	}

	private void ceil() {
		double val1 = stack.pop();
		stack.add(Math.ceil(val1));
	}

	private void floor() {
		double val1 = stack.pop();
		stack.add(Math.floor(val1));
	}

	private void round() {
		double val1 = stack.pop();
		if (val1 % 1 >= .5)
			stack.add(Math.ceil(val1));
		else
			stack.add(Math.floor(val1));
	}

	private void Double() {
		double val = stack.peek();
		stack.add(val);
	}

	//OPERATIONS with parameter

	private void push(double value) {
		stack.add(value);
	}

	private void add(double value) {
		double stackVal = stack.pop();
		stack.add(stackVal + value);
	}

	private void sub(double value) {
		double stackVal = stack.pop();
		stack.add(stackVal - value);
	}

	private void mul(double value) {
		double stackVal = stack.pop();
		stack.add(stackVal * value);
	}

	private void div(double value) {
		double stackVal = stack.pop();
		stack.add(stackVal / value);
	}

	//TODO right now only works with integers
	private void pow(double value) {
		double stackVal = stack.pop();

		double pow = 1;
		for (int i = 0; i < value; i++)
			pow *= stackVal;

		stack.add(pow);
	}

	private void mod(double value) {
		double stackVal = stack.pop();
		stack.add(stackVal % value);
	}

	private void store(int value) {
		double stackVal = stack.pop();
		registers[value] = stackVal;
	}

	private void load(int value) {
		stack.add(registers[value]);
	}
}
