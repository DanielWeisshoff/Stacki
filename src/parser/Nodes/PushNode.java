package src.parser.Nodes;

public class PushNode extends Node {

	private NumberNode number;

	public PushNode(NumberNode number) {
		this.number = number;
	}

	public double getValue() {
		return number.getValue();
	}
}
