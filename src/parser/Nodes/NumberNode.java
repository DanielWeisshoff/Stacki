package src.parser.Nodes;

public class NumberNode extends Node {

	private double value;

	public NumberNode(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}
}