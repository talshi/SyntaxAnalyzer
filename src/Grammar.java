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

	boolean DEBUG = false;

	private Map<String, List<Node>> prodList;
	private NonTerminal initTerminal;

	public Grammar(String configFile) {
		// create map of production rules
		prodList = new HashMap<String, List<Node>>();
		this.initTerminal = null;
		boolean parsePassed = parseConfigFile(configFile);
		if(!parsePassed) {
			System.err.println("Config file parsed failed.");
		}

		System.out.println("[SA]Grammar initialize successfully.");
	}

	public List<Node> getProductionRules(Node sthead, TokenInfo input) {
		// concatanate sthead and input
		String key = "";
		key += sthead.getData();
		key += " ";
		key += input.getTokenType().toString();

		return prodList.get(key);
	}

	private boolean parseConfigFile(String configFile) {
		FileReader fr = null;
		BufferedReader br = null;
		LineNumberReader lnr = null;
		String line = "";
		int lineNumber = 0;
		String[] terminals = null;
		List<String> nonTerminalLine = new ArrayList<String>();
		String currNonTerminal = "";

		try {
			fr = new FileReader(configFile);
			br = new BufferedReader(fr);
			lnr = new LineNumberReader(br);

			line = lnr.readLine();

			// parse terminals line
			String tmpLine = String.valueOf(line);
			terminals = tmpLine.split("=|,"); // run from index 1

			if(DEBUG)
				System.out.println("Line token #" + lineNumber + ": " + tmpLine);

			if(DEBUG){
				System.out.print("terminals = ");
				printStrings(terminals);
			}

			lineNumber++;

			while( (line = lnr.readLine()) != null) {
				tmpLine = String.valueOf(line);
				if (tmpLine.equals("")){
					break;
				}

				if(DEBUG)
					System.out.println("Line token #" + lineNumber + ": " + tmpLine);

				nonTerminalLine = parseNonTerminals(tmpLine);
				if(nonTerminalLine == null) { // skip on notes
					lineNumber++;
					continue;
				}
				if(DEBUG){
					System.out.print("nonTerminals = ");
					System.out.println(nonTerminalLine);
				}

				currNonTerminal = nonTerminalLine.get(0);

				// on line 1, init the initTerminal
				if(this.initTerminal == null && currNonTerminal.indexOf(0) != '#') {
					this.initTerminal = new NonTerminal(currNonTerminal, null);
				}

				for(int i = 1; i < nonTerminalLine.size(); i++) {
					String[] tmpProd = nonTerminalLine.get(i).split(";");

					if(DEBUG)
						printStrings(tmpProd);

					// create list of production rules (nodes)
					List<Node> prod = new ArrayList<Node>();
					for(int j = 0; j < tmpProd.length; j++) {
						if(!tmpProd[j].equals(" ")) {
							if(isTerminal(terminals, tmpProd[j])) {
								prod.add(new Terminal(tmpProd[j], null));
							}
							else {
								prod.add(new NonTerminal(tmpProd[j], null));
							}
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

	private boolean isTerminal(String[] terminals, String str) {
		for(int i = 1; i < terminals.length; i++) {
			if(terminals[i].equals(str)) {
				return true;
			}
		}
		return false;
	}

	private List<String> parseNonTerminals(String line) {
		List<String> nonTerminals = new ArrayList<String>();
		//char[] chars = line.toCharArray();

		String str = "";
		if(line.contains("#")) {
			return null;
		}

		for(int i = 0; i < line.length(); i++) {
			if(line.charAt(i) == '=' || line.charAt(i) == ',') {
				if(str.isEmpty()) {
					str = " ";
				}
				nonTerminals.add(str);
				str = "";
			}
			else {
				str += line.charAt(i);
			}
		}
		
		if(str.isEmpty()) {
			str = " ";
		}
		nonTerminals.add(str);

		return nonTerminals;
	}

	public NonTerminal getInitTerminal() {
		return this.initTerminal;
	}

	private void printStrings(String[] strings) {
		for(String str : strings) {
			System.out.println(str);
		}
	}

}
