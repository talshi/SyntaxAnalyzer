import java.io.File;

public class Scanner 
{
	private CharReader cr;
	private char currChar;

	public Scanner(String fileName)
	{
		this.cr = new CharReader(fileName);
		this.currChar = this.cr.getChar();
	}

	public TokenInfo yylex()
	{
		int line = this.cr.getLine();
		
		switch (this.currChar)
		{
			case ';':
			{
				this.currChar = this.cr.getChar();
				return new TokenInfo(ETokenType.SC, line, null);
			}
			case '(':
			{
				this.currChar = this.cr.getChar();
				return new TokenInfo(ETokenType.LP, line, null);
			}
			case ')':
			{
				this.currChar = this.cr.getChar();
				return new TokenInfo(ETokenType.RP, line, null);
			}
			case '{':
			{
				this.currChar = this.cr.getChar();
				return new TokenInfo(ETokenType.LC, line, null);
			}
			case '}':
			{
				this.currChar = this.cr.getChar();
				return new TokenInfo(ETokenType.RC, line, null);
			}
			case '+':
			{
				this.currChar = this.cr.getChar();
				return new TokenInfo(ETokenType.PLUS, line, null);
			}
			case '-':
			{
				this.currChar = this.cr.getChar();
				return new TokenInfo(ETokenType.MINUS, line, null);
			}
			case '*':
			{
				this.currChar = this.cr.getChar();
				return new TokenInfo(ETokenType.MULT, line, null);
			}
			case '&':
			{
				this.currChar = this.cr.getChar();
				
				if ('&' == this.currChar)
				{
					this.currChar = this.cr.getChar();
					return new TokenInfo(ETokenType.AND, line, null);
				}
				else
				{
					//return new TokenInfo(ETokenType.ERROR, line, null);
					return null;
				}
			}
			case '|':
			{
				this.currChar = this.cr.getChar();
				
				if ('|' == this.currChar)
				{
					this.currChar = this.cr.getChar();
					return new TokenInfo(ETokenType.OR, line, null);
				}
				else
				{
					//return new TokenInfo(ETokenType.ERROR, line, null);
					return null;
				}
			}
			case '=':
			{
				this.currChar = this.cr.getChar();
				
				// ==
				if ('=' == this.currChar)
				{
					this.currChar = this.cr.getChar();
					return new TokenInfo(ETokenType.REL, line, "==");
				}
				// =
				else
				{
					return new TokenInfo(ETokenType.ASSIGN, line, null);
				}
			}
			case '!':
			{
				this.currChar = this.cr.getChar();
				
				// !=
				if ('=' == this.currChar)
				{
					this.currChar = this.cr.getChar();
					return new TokenInfo(ETokenType.REL, line, "!=");
				}
				// ERROR
				else
				{
					//return new TokenInfo(ETokenType.ERROR, line, null);
					return null;
				}
			}
			case '<':
			{
				this.currChar = this.cr.getChar();
				
				// <=
				if ('=' == this.currChar)
				{
					this.currChar = this.cr.getChar();
					return new TokenInfo(ETokenType.REL, line, "<=");
				}
				// <
				else
				{
					return new TokenInfo(ETokenType.REL, line, "<");
				}
			}
			case '>':
			{
				this.currChar = this.cr.getChar();
				
				// >=
				if ('=' == this.currChar)
				{
					this.currChar = this.cr.getChar();
					return new TokenInfo(ETokenType.REL, line, ">=");
				}
				// >
				else
				{
					return new TokenInfo(ETokenType.REL, line, ">");
				}
			}
			case '/':
			{
				this.currChar = this.cr.getChar();

				// single line comment
				if ('/' == this.currChar)
				{
					return readSingleLineComment();
				}
				// multiple line comment
				else if ('*' == this.currChar)
				{
					return readMultipleLineComment();
				}
				// DIV
				else
				{
					return new TokenInfo(ETokenType.DIV, line, null);
				}
			}
			case ' ':
			{
				this.currChar = this.cr.getChar();
				//return new TokenInfo(ETokenType.WHITE, line, null);
				return null;
			}
			case '\n':
			{
				this.currChar = this.cr.getChar();
				//return new TokenInfo(ETokenType.NL, line, null);
				return null;
			}
			case (char)(-1):
			{
				return new TokenInfo(ETokenType.EOF, line, null);
			}
			// it's a reserved word, or an ID or a function name
			default:
			{
				if (Character.isDigit(this.currChar))
				{
					String number = getNumber();
					
					if (null != number)
						return new TokenInfo(ETokenType.NUM, line, number);
					//return new TokenInfo(ETokenType.ERROR, line, null);
					return null;
				}
				
				return isID();
			}
		}
	}
	
	private TokenInfo readSingleLineComment()
	{
		int line = this.cr.getLine();
		int newLine = cr.getLine();
		
		while (newLine == line)
		{
			this.currChar = this.cr.getChar();
			newLine = this.cr.getLine();
		}
		
		//return new TokenInfo(ETokenType.CMMNT, line, null);
		return null;
	}
	
	private TokenInfo readMultipleLineComment()
	{
		int line = this.cr.getLine();

		while (true)
		{
			this.currChar = this.cr.getChar();
			
			if ((char)(-1) == this.currChar)
			{
				//return new TokenInfo(ETokenType.ERROR, line, null);
				return null;
			}
			else if ('*' == this.currChar)
			{
				this.currChar = this.cr.getChar();
				
				if ((char)(-1) == this.currChar)
					//return new TokenInfo(ETokenType.ERROR, line, null);
					return null;
				else if ('/' != this.currChar)
					continue;					
				
				this.currChar = this.cr.getChar();
				return new TokenInfo(ETokenType.CMMNT, line, null);
			}
		}
	}
	
	private String getNumber()
	{
		String number = "";
		
		while (!Character.isLetter(this.currChar)) 
		{
			if (Character.isDigit(this.currChar))
				number += currChar;
			else 
				break;
			
			this.currChar = this.cr.getChar();
		}
		
		return number;
	}
	
	private TokenInfo isID()
	{
		StringBuilder sb = new StringBuilder();
		ETokenType ett;
		
		int line = this.cr.getLine();
		
		while (Character.isLetter(this.currChar) || Character.isDigit(this.currChar))
		{
			if ((char)(-1) == this.currChar || ' ' == this.currChar)
				break;
				
			sb.append(this.currChar);
			this.currChar = this.cr.getChar();
		}
		
		if (sb.toString().equals("") || null == sb.toString())
		{
			currChar = cr.getChar();
			//return new TokenInfo(ETokenType.ERROR, line, null);
			return null;
		}
		
		if (null != (ett = isReservedWord(sb.toString())))
		{
			return new TokenInfo(ett, line, null);
		}
		
		if (Character.isLowerCase(sb.toString().charAt(0)))
		{
			return new TokenInfo(ETokenType.ID, line, sb.toString());
		}
		else
		{
			return new TokenInfo(ETokenType.FID, line, sb.toString());
		}
	}
	
	private ETokenType isReservedWord(String str)
	{
		if (str.equals("int"))
			return ETokenType.INT;
		if (str.equals("function"))
			return ETokenType.FUNC;
		if (str.equals("main"))
			return ETokenType.MAIN;
		if (str.equals("if"))
			return ETokenType.IF;
		if (str.equals("then"))
			return ETokenType.THEN;
		if (str.equals("else"))
			return ETokenType.ELSE;
		
		return null;
	}
	
	public void close()
	{
		this.cr.close();
	}
}
