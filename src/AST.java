import java.util.List;

public class AST {

	final private Node root;
	private Node current;
	
	public AST() {
		root = new Node("E"); // TODO init root
		current = root;
	}
	
	public void setChildList(List<Node> l) {
		current.setChildren(l);
	}
	
	public void setParentAsCurrent() {
		current = current.getParent();
	}
	
	public void setCurrent(Node n) {
		current = n;
	}
	
	public Node getCurrent() {
		return current;
	}
	
	public Node getChild(String data) {
		return this.current;
	}
	
	public String toString() {
		// TODO
		return null;
	}
	
}