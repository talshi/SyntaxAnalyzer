import Node.nodeTypeEnum;

public class NonTerminal extends Node {
	
	public NonTerminal(String data) {
		super(data);
	}
	
	public nodeTypeEnum getNodeType() {
		return nodeTypeEnum.String;
	}

	@Override
	public void setData(String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
