
public class NonTerminal extends Node {
	
	private String data;
	
	public NonTerminal(String data) {
		this.data = data;
	}
	
	public NodeType getNodeType() {
		return NodeType.String;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return this.data;
	}
	
}
