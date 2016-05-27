public class TokenInfo 
{
	private ETokenType type;
	private int line;
	private String attribute;
	
	public TokenInfo(ETokenType type, int line, String att)
	{
		this.type = type;
		this.line = line;
		this.attribute = att;
	}
	
	public ETokenType getTokenType()
	{
		return this.type;
	}
	
	public String getAttribute(){
		return this.attribute;
	}
	
	public String toString()
	{
		String str = "";
		return str += this.type.name() + ";"
				+ (this.attribute == null ? "" : this.attribute) + ";" 
				+ this.line;
	}
}
