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

	private Map<String, List<Node>> prodList;

	public Grammar(String configFile) {
		// create map of production rules
		prodList = new HashMap<String, List<Node>>();
		parseConfigFile(configFile);
	}

	public List<Node> getProductionRules(Node sthead, Token input) {
		// concatanate sthead and input
		String s = "";
		s += sthead;
		s += input.getLexema();
		return prodList.get(s);
	}

	private void parseConfigFile(String configFile) {
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
			
			// DEBUG
			printStrings(terminals);

			lineNumber++;

			while( (line = lnr.readLine()) != null) {
				tmpLine = String.valueOf(line);
				System.out.println("Line token #" + lineNumber + ": " + tmpLine);
				if(tmpLine.contains("#")) {
					lineNumber++;
					continue;
				}

				nonTerminalLine = parseNonTerminals(tmpLine);
				
				// DEBUG
				printStrings(nonTerminalLine);
				currNonTerminal = nonTerminalLine.get(0);

				for(int i = 0; i < nonTerminalLine.size(); i++) {
					String[] tmpProd = nonTerminalLine.get(i).split(";");
					printStrings(tmpProd);
					
					// create list of production rules (nodes)
					List<Node> prod = new ArrayList<Node>();
					for(int j = 1; j < tmpProd.length; j++) {
						prod.add(new Node(tmpProd[j]));
					}
					System.out.println("List of productions: " + prod.toString());
					// add production rule to map, suits to its key
					String key = "";
					key += tmpProd[0] + " " + tmpProd[1];
					prodList.put(key, prod);
					System.out.println("Key: " + key + " Productions: " + prod.toString());
				}
				lineNumber++;
			}

		} catch (FileNotFoundException e) {
			System.err.println("ERROR: parse config file failed. File not found.");
		} catch (IOException e) {
			System.err.println("ERROR: parse config file failed. Throw IO Execption.");
		}
	}
	
	private List<String> parseNonTerminals(String line) {
		List<String> nonTerminals = new ArrayList<String>();
		char[] chars = line.toCharArray();
		String str = "";
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
			System.out.println(str);
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
