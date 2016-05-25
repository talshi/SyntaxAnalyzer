import java.util.LinkedList;
import java.util.List;

public abstract class Node {

	private static int nodeID = 0;
	private int id;
	final private Node parent = null;
	private String data;
	final private List<Node> children = null;
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
	
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
		return this.children.addAll(children);
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
	
	public boolean addChild(Node n) {
		return this.children.add(n); // return true if add child was succeed
	}
	
	public abstract NodeType getNodeType();
    
    public String toString() {
    	String str = "";
    	
        if (isLeaf()) {
            str += " " + this.data;
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
