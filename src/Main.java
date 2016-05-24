import org.xml.sax.ext.LexicalHandler;

public class Main {

	// arguments: config file, text file
	public static void main(String[] args) {
		if(args == null) {
			System.err.println("Error: argument list is null.");
			System.exit(0);
		}
		
		Parser p = new Parser();
		SyntaxParser sp = new SyntaxParser(args);
		
//		if( sp.yyLL1Parse() == null ) {
//			System.err.println("Input is wrong according to LL(1) table.");
//		}
	}

}
