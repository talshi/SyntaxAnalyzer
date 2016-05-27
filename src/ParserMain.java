
public class ParserMain 
{
	public static void main(String[] args) 
	{
		//System.out.println("hello world");
		
		/*
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
		
		//configFile - args[0]
		//inputFile - args[1]

		Parser p = new Parser(args);
		AST ast;
		
		if ((ast = p.yyLL1Parse()) != null){
			System.out.println(p.printAST());
			//p.printAST();
		}
		else{
			System.err.println("NOOOOOOO");
		}
	}

}
