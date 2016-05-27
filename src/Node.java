import java.util.ArrayList;
import java.util.List;

public abstract class Node {

	private static int nodeID = 1;
	protected int id;
	
	protected String data;
	protected Node parent;
	protected List<Node> children;

	abstract public Node clone();
	
	public abstract void setData(String data);

	public abstract String getData();

	public abstract NodeType getNodeType();

	public void setChild(Node n) {
		if(n == null) {
			throw new NullPointerException("ERROR: Child is null.");
		}
		boolean res = children.add(n);
		if(res == false) {
			System.err.println("ERROR: add child failed.");
			System.exit(0);
		}
	}

	public List<Node> getChildren() {
		return children;
	}

	public boolean setChildren(List<Node> children) {
		//return this.children.addAll(children);
		
		for (Node n : children){
			this.children.add(n.clone());
		}
		
		for (Node n : this.children){
			n.parent = this;
		}
		
		return true;
	}

	// remove abstract ??
	private boolean addChild(Node n){
		// add Terminal
		if (n.getNodeType() == NodeType.EOF || n.getNodeType() == NodeType.Token){
			return this.children.add(new Terminal(n));
		}
		else{
			return this.children.add(new NonTerminal(n));
		}
	}

	public Node getParent() {
		return this.parent;
	}

	public boolean isLeaf() {
		return (this.children.isEmpty());
	}

	public boolean isRoot() {
		return (this.parent == null);
	}

	public boolean isChild(Node n) {
		for(Node child : children) {
			if(child == n)
				return true;
		}
		return false;
	}

	public int getID() {
		return this.id;
	}

	public void setID(){
		this.id = nodeID++;
	}

	public String toString() {
		String str = "";

		if (isLeaf()) {
			str += this.data;
			return str;
		}

		str += "(" + this.data;
		for (Node child : children) {
			str += child.toString();
		}
		str += ")";

		return str;
	}


}
