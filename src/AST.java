import java.util.List;

public class AST {

	private boolean DEBUG = true;
	private static int id = 0;
	//final
	private Node root;
	private Node current;
	
	public AST(NonTerminal nt) {
		root = new NonTerminal(nt.getData(), null);
		root.setID(id++);
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
	
	public String toString(){
		String str = "";
		return printAST(str, this.root);
	}
	
	private String printAST(String str, Node root)
	{
		if (root.isLeaf()){
			for (Node n: root.getChildren())
				System.out.println("i'm a child");
			return str;
		}

		for (Node child : root.getChildren())
		{
			if (root.getID() == 0){
				root.setID(id++);
			}
			if (child.getID() == 0){
				child.setID(id++);	
			}

			str +=  root.getData() + "_" + root.getID() + "->" + child.getData() + "_" + child.getID() + "\n";
			str = printAST(str, child);
		}

		return str;
	}
	
}
