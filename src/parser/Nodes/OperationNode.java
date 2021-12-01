package src.parser.Nodes;

import src.parser.OperationType;

public class OperationNode extends Node {

	private NumberNode parameter;
	public OperationType type;

	public OperationNode(OperationType type) {
		this(type, null);
	}

	public OperationNode(OperationType type, NumberNode parameter) {
		this.type = type;
		this.parameter = parameter;
	}

	public double getValue() {
		return parameter.getValue();
	}
}