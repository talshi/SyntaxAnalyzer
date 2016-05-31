import java.util.ArrayList;

public class Terminal extends Node {

	public Terminal(String data, Node parent) {
		this.id = 0;
		this.data = data;
		this.parent = parent;
		this.children = new ArrayList<Node>();
	}
	
	// Copy Constructor
//	public Terminal(Node t) {
//		this.id = 0;
//		this.data = t.data;
//		this.parent = t.parent;
//		this.children = new ArrayList<Node>();
//	}
	
	public Node clone(){
		return new Terminal(this.data, this.parent);
	}
	
	public NodeType getNodeType() {
		if(this.data.equals("EOF")) {
			return NodeType.EOF;
		}
		else if(this.data.equals("eps")) {
			return NodeType.eps;
		}
		else {
			return NodeType.Token;
		}
		
	}
	
	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return this.data;
	}

//	public boolean addChild(Node n) {
//		return this.children.add(new Terminal(n));
//	}
	
	public String toString(){
		return this.data;
	}
	
}
