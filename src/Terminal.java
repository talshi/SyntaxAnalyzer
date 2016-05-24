
public class Terminal extends Node {

	private Token t = null;
	
	public Terminal(String data) {
		super(data);
	}
	
	public nodeTypeEnum getNodeType() {
		return nodeTypeEnum.Token;
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
