import java.util.ArrayList;

public class NonTerminal extends Node {
	
	public NonTerminal(String data, Node parent) {
		this.id = 0;
		this.data = data;
		this.parent = parent;
		this.children = new ArrayList<Node>();
	}
	
	public Node clone(){ 
		return new NonTerminal(this.data, this.parent);
	}
	
	public NodeType getNodeType() {
		if(this.data.equals("eps")) {
			return NodeType.eps;
		}
		else {
			return NodeType.String;
		}
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return this.data;
	}

//	public boolean addChild(Node n) {
//		return this.children.add(new NonTerminal(n));
//	}
	
	public String toString(){
		return this.data;
	}
	
}
