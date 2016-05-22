import java.util.LinkedList;
import java.util.List;

public class Node {

	private static int nodeNumber = 0;
	private int id;
	final private Node parent;
	private String data;
	private nodeTypeEnum type;
	final private List<Node> children;
	
	public static enum nodeTypeEnum {
		Token,
		String,
		EOF,
		eps
	}
	
	public Node(String data) {
		this.parent = null;
		this.children = new LinkedList<Node>();
		this.data = data;
		this.id = nodeNumber++;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
	
	public nodeTypeEnum getNodeType() {
		return type;
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
	
	public void setChildren(List<Node> l) {
		children.addAll(l);
	}
	
	public List<Node> getChildren() {
		return children;
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
