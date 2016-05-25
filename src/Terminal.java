
public class Terminal extends Node {

	private Token t = null;
	
	
	public Terminal(String data, Token.TokenType type, int lineNumber) {
		t = new Token(data, type, lineNumber);
		t.setLexema(data);
	}
	
	public NodeType getNodeType() {
		return NodeType.Token;
	}
	
	public void setData(String data) {
		this.t.setLexema(data);
	}

	public String getData() {
		return t.getLexema();
	}
	
}
