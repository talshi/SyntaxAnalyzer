import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;

public class CharReader 
{
	private FileReader fr;
	private LineNumberReader lnr;
	private char currChar;
	
	public CharReader(String fileName)
	{
		try
		{
			this.fr = new FileReader(fileName);
			this.lnr = new LineNumberReader(fr);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public char getChar()
	{
		int ch;
		
		try
		{
			if ((ch = lnr.read()) != -1)
			{
				this.currChar = (char)(ch);
			}
			else
			{
				this.currChar = (char)(-1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return this.currChar;
	}
	
	public int getLine()
	{
		return this.lnr.getLineNumber() + 1;
	}
	
	public void close() 
	{
		try 
		{
			this.fr.close();
			this.lnr.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
