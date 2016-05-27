import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Parser {

	private boolean DEBUG = false;
	private Map<Node, TokenInfo> map = null;
	private Scanner s = null;
	private Grammar gr = null;
	private AST ast;
	private TokenInfo token;

	public Parser(String[] args) {

		// arguments (config file and input file)
		String configFile = args[0];
		String inputFile = args[1];

		// Init
		map = new HashMap<Node, TokenInfo>();
		s = new Scanner(inputFile);
		System.out.println("config-file = " + configFile);
		gr = new Grammar(configFile);
		ast = new AST(gr.getInitTerminal());

		// take first token from input
		do{
			token = s.yylex();
		}while (token == null);
	}

	// return AST or null in case the input is not suit the grammar
	public AST yyLL1Parse() {

		// TODO step 0: pop, (check terminal or nonterminal or EOF or eps), getProductionRules, setChilds, push
		// note 1: non-terminal: current.isChild(T)->if true, setCurrent(T), else while(!current.getParent.isChild(X)) { setCurrent(current.getParent() }, then setCurrent(X)
		// note 2: terminal: compare with stackhead.nodeType == terminal and if compare(stackhead, input(token)), if both true then yylex()
		// note 3: check from step 1 after pop if(current.isChild)
		// note 4: if stackhead.getNodeType == eps->continue
		// TODO step 1: pop, if(current.isChild(T)->setCurrent(T), getProductionRules, 

		Stack<Node> st = new Stack<Node>();
		List<Node> prod = new ArrayList<Node>();
		Node sthead = null;

		// push EOF
		//		st.push(new Terminal("EOF", null)); // ??

		// push init terminal
		st.push(ast.getRoot());

		while(true) {
//			System.out.println("Stack: " + st.toString()); //--
			sthead = st.pop();
			//System.out.println("Stack after pop: " + st.toString()); //--

			// EOF
			if(sthead.getData().equals("EOF")) {
				if(token.getTokenType().toString() == "EOF") {
					return ast;
				}

				// ERROR: input is not equal to stack head
				else {
					System.err.print("[SP]ERROR: input is not equal to stack head");
					return null;
				}
			}

			// epsilon
			else if(sthead.getNodeType() == NodeType.eps){
				continue;
			}

			// Non-Terminal
			else if(sthead.getNodeType() == NodeType.String) { 
				if(sthead.isChild(ast.getCurrent())) {
					ast.setCurrent(sthead);
				}
				else {
					while(ast.getCurrent().getParent() != null && ast.getCurrent().isChild(sthead)) {
						ast.setCurrent(ast.getCurrent().getParent());
					}
					ast.setCurrent(sthead);
				}
			}

			// Terminal
			else if(sthead.getNodeType() == NodeType.Token) {
				if(sthead.getData().equals(token.getTokenType().toString())) {
					
					do{
						token = s.yylex();
					}while (token == null);
					
					continue;
				}

				// ERROR: terminals aren't equal, input did not pass the Syntax Analyzer
				else {
					System.err.println("[SP]ERROR: terminals aren't equal, input did not pass the Syntax Analyzer");
					return null;
				}
			}

			else {
				System.err.println("[SP]ERROR: General Error.");
				return null;
			}

			// get production rules
			prod = gr.getProductionRules(sthead, token);
			if(prod == null) {
				System.err.println("[SP]ERROR. Get production rules failed");
				return null;
			}

			// add children
			// build the ast
			// if there is an epsilon, ignore (don't push to stack and don't add node to ast) 
			if(!(ast.getCurrent()).setChildren(prod)) {
				System.err.println("[SP]ERROR. adding children to current node failed");
				System.exit(0); // TODO return null ?
			}
			
			// push prod to the stack
			for (int i = ast.getCurrent().getChildren().size()-1; i >= 0; i--){
				st.push(ast.getCurrent().getChildren().get(i));
			}
		}
		
		// print to ast file
	}

	public void printAST(String fileName)
	{
		String str = "";
		ast.getRoot().setID();

		str = printAST(str, ast.getRoot());

		File file = new File(fileName.substring(0, fileName.lastIndexOf(".")) + ".ptree");
		Writer writer = null;
		try {
			boolean bool = file.createNewFile();
			if(!bool) {
				System.err.println("ERROR creating output file!");
			}
			writer = new FileWriter(file);
			
			System.out.println("digraph G{ \n" + str + "}");
			writer.write("digraph G{ \n" + str + "\n }");
			
			writer.close();
			
		} catch (IOException e) {
			System.err.println("ERROR. Got an exception!");
		}
		
		
	}

	private String printAST(String str, Node root)
	{
		if (root.isLeaf()){
			for (Node n: root.getChildren())
				System.out.println("i'm a child");
			return str;
		}

		for (Node child : root.getChildren())
		{
			if (root.getID() == 0)
				root.setID();
			if (child.getID() == 0)
				child.setID();

			str +=  root.getData() + "_" + root.getID() + "->" + child.getData() + "_" + child.getID() + "\n";
			str = printAST(str, child);
		}

		return str;
	}

}
