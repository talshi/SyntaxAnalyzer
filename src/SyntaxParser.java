import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class SyntaxParser {

	private boolean DEBUG = true;
	private Map<Node, Token> map = null;
	//??Stack<Node> stack = null;
	private Parser p = null;
	private Grammar gr = null;
	private AST ast;
	private Scanner s;
	private Token token;

	public SyntaxParser(String[] args) {

		// arguments (config file and input file)
		String configFile = args[0];
		String inputFile = args[1];

		// Init
		p = new Parser();
		map = new HashMap<Node, Token>(); 
		//??stack = new Stack<Node>();
		gr = new Grammar(configFile);
		ast = new AST();

		// Init scanner for read input file
		try {
			s = new Scanner(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			System.err.println("[SP]ERROR. Could not open input file.");
		}
		// take first token from input
		token = p.yylex(s);
	}

	// return AST or null in case the input is not suit the grammar
	public AST yyLL1Parse() {

		// TODO step 0: pop, (check terminal or nonterminal or EOF or eps), getProductionRules, setChilds, push
		// note 1: non-terminal: current.isChild(T)->if true, setCurrent(T), else while(!current.getParrent.isChild(X)) { setCurrent(current.getParent() }, then setCurrent(X)
		// note 2: terminal: compare with stackhead.nodeType == terminal and if compare(stackhead, input(token)), if both true then yylex()
		// note 3: check from step 1 after pop if(current.isChild)
		// note 4: if stackhead.getNodeType == eps->continue
		// TODO step 1: pop, if(current.isChild(T)->setCurrent(T), getProductionRules, 

		Stack<Node> st = new Stack<Node>();
		List<Node> prod = new ArrayList<Node>();
		Node sthead = null;

		// TODO push the initial terminal
		//st.push(); 
		
		while(true) {
			
			// TODO 
			if (st.isEmpty()){
				//??
				// stack is empty and we read all file
				if (token.getType().equals("EOF")) {
					return ast;
				}
				// stack is empty and we didn't read all file
				else{
					
				}
			}
			sthead = st.pop();
			// TODO check terminal/nonterminal

			// EOF
			if(sthead.getData().equals("EOF"))
				break;

			// epsilon
//			else if(sthead.getNodeType()){

//			}
			// Non-Terminal
			else if(sthead.getNodeType() ==  NodeType.String) { 
				while(!ast.getCurrent().isChild(sthead)) {
					ast.setCurrent(ast.getCurrent().getParent());
				}
				ast.setCurrent(sthead); // TODO why?
			}
			else if(sthead.getNodeType() == NodeType.Token) { // Terminal
				//				if(sthead.getData() == )
			}

			// get production rules
			prod = gr.getProductionRules(sthead, token);
			if(prod == null) {
				System.err.println("[SP]ERROR. Get production rules failed");
				System.exit(0);
			}

			// add children
			if( ast.getCurrent().setChildren(prod) ) {
				System.err.println("[SP]ERROR. adding children to current node failed");
				System.exit(0);
			}

			// push to stack
			for(Node n: prod) {
				st.push(n);
			}

			// get next token
			token = p.yylex(s);
		}

		return ast;
	}

}
