package src.parser.Nodes;

import java.util.ArrayList;

public class RootNode extends Node {

	public ArrayList<Node> children = new ArrayList<>();

	public void add(Node node) {
		children.add(node);
	}
}
