import java.util.List;

public class AST {

	private boolean DEBUG = true;
	//final
	private Node root;
	private Node current;
	
	public AST(NonTerminal nt) {
		root = new NonTerminal(nt.getData(), null);
		current = root;
		
		if(DEBUG)
			System.out.println("[SA]AST initalized successfuly");
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
	
	public Node getRoot(){
		return this.root;
	}
	
	public Node getCurrent() {
		return this.current;
	}
	
	public Node getChild(String data) { //??
		return this.current;
	}
	
	public String toString() {
		// TODO
		return null;
	}
	
}
