import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammar {

	boolean DEBUG = true;
	
	private Map<String, List<Node>> prodList;

	public Grammar(String configFile) {
		// create map of production rules
		prodList = new HashMap<String, List<Node>>();
		boolean parsePassed = parseConfigFile(configFile);
		if(!parsePassed) {
			System.err.println("Config file parsed failed.");
		}
		
		if(DEBUG)
			System.out.println("[SA]Grammar initialize successfully.");
	}

	public List<Node> getProductionRules(Node sthead, Token input) {
		// concatanate sthead and input
		String s = "";
		s += sthead;
		s += input.getLexema();
		return prodList.get(s);
	}

	private boolean parseConfigFile(String configFile) {
		FileReader fr = null;
		BufferedReader br = null;
		LineNumberReader lnr = null;
		String line = "";
		int lineNumber = 0;
		String[] terminals = null;
//		ArrayList<String[]> nonTerminals = new ArrayList<String[]>();
		List<String> nonTerminalLine = new ArrayList<String>();
		String currNonTerminal = "";

		try {
			fr = new FileReader(configFile);
			br = new BufferedReader(fr);
			lnr = new LineNumberReader(br);

			line = lnr.readLine();
			
			// parse terminals line
			String tmpLine = String.valueOf(line);
			terminals = tmpLine.split(",|="); // TODO delete "terminals="
			
			if(DEBUG)
				System.out.println("Line token #" + lineNumber + ": " + tmpLine);

			if(DEBUG)
				printStrings(terminals);

			lineNumber++;

			while( (line = lnr.readLine()) != null) {
				tmpLine = String.valueOf(line);
				
				if(DEBUG)
					System.out.println("Line token #" + lineNumber + ": " + tmpLine);

				nonTerminalLine = parseNonTerminals(tmpLine);
				if(nonTerminalLine == null) { // skip on notes
					lineNumber++;
					continue;
				}
				if(DEBUG)
					System.out.println(nonTerminalLine);

				currNonTerminal = nonTerminalLine.get(0);

				for(int i = 1; i < nonTerminalLine.size(); i++) {
					String[] tmpProd = nonTerminalLine.get(i).split(";");
					
//					if(DEBUG)
//						printStrings(tmpProd);
					
					// create list of production rules (nodes)
					List<Node> prod = new ArrayList<Node>();
					for(int j = 0; j < tmpProd.length; j++) {
						if(!tmpProd[j].equals(" ")) {
							prod.add(new Node(tmpProd[j]));
						}
					}
					if(prod.isEmpty()) { // if there is no rule, do nothing
						continue;
					}
					
					if(DEBUG)
						System.out.println("List of productions: " + prod.toString());
					
					// add production rule to map, suits to its key
					String key = "";
					key += nonTerminalLine.get(0) + " " + terminals[i];
					prodList.put(key, prod);
					
					if(DEBUG)
						System.out.println("Key: " + key + ", Productions: " + prod.toString());
				}
				lineNumber++;
			}
			lnr.close();

		} catch (FileNotFoundException e) {
			System.err.println("ERROR: parse config file failed. File not found.");
			return false;
		} catch (IOException e) {
			System.err.println("ERROR: parse config file failed. Throw IO Execption.");
			return false;
		}
		return true;
	}
	
	private List<String> parseNonTerminals(String line) {
		List<String> nonTerminals = new ArrayList<String>();
		char[] chars = line.toCharArray();
		String str = "";
		if(line.contains("#")) {
			return null;
		}
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == '=' || chars[i] == ',') {
				if(str.isEmpty()) {
					str = " ";
				}
				nonTerminals.add(str);
				str = "";
			}
			else {
				str += chars[i];
			}
		}
		return nonTerminals;
	}

	private void printStrings(String[] strings) {
		for(String str : strings) {
			System.out.println(str);
		}
	}
	
	private void printStrings(List<String> strings) {
		for(String str : strings) {
			System.out.println(str);
		}
	}

}
