import java.io.File;
import java.io.FileWriter;

public class TokenWrite 
{
	private File file;
	private FileWriter writer;
	
	public TokenWrite(String fileName)
	{
		String tokenFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".token";

		try
		{
			//create fileName + ".token"
			file = new File(tokenFileName);

			//tries to create new file in the system
			boolean bool = file.createNewFile();
			
			writer = new FileWriter(file);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void writeToFile(TokenInfo ti) 
	{
		try 
		{
			writer.write(ti.toString() + '\n');
			writer.flush();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void closeWriter()
	{
		try 
		{
			writer.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
