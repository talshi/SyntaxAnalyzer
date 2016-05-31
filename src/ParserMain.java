
public class ParserMain 
{
	public static void main(String[] args) 
	{
		/*
		// Ex1
		Scanner s = new Scanner(args[0]);
		TokenWrite tw = new TokenWrite(args[0]);
		
		TokenInfo ti = null;
		ETokenType type = null;
		
		do
		{
			ti = s.yylex();
			type = ti.getTokenType();
			
			System.out.println("tokenInfo = " + ti.getAttribute());
			
			if (type != ETokenType.ERROR && type != ETokenType.WHITE && type != ETokenType.CMMNT && type != ETokenType.NL && type != ETokenType.EOF) 
			{
				tw.writeToFile(ti);
			}
		}
		while (type != ETokenType.EOF);
		
		s.close(); //close reading
		tw.closeWriter(); //close writing
		*/
		
		// Ex2
		Parser p = new Parser(args);
		AST ast = p.yyLL1Parse();
	}

}
