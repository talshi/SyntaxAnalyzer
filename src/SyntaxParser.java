import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SyntaxParser {

	private Map<Node, Token> map = null;
	Stack<Node> stack = null;
	private Parser p = null;
	private Grammar gr = null;
	private AST ast;
	
	public SyntaxParser(String configFile) {
		map = new HashMap<Node, Token>();
		stack = new Stack<Node>();
		gr = new Grammar(configFile);
		ast = new AST();
		// TODO init first AST node
		// TODO first yylex
	}
	
	// return AST or null in case the input is not suit the grammar
	public AST yyLL1Parse() {
		
		// TODO step 0: pop, (check terminal or nonterminal), getProductionRules, setChilds, push
		// note 1: non-terminal: current.isChild(T)->if true, setCurrent(T), else while(!current.getParrent.isChild(X)) { setCurrent(current.getParent() }, then setCurrent(X)
		// note 2: terminal: compare with stackhead.nodeType == terminal and if compare(stackhead, input(token)), if both true then yylex()
		// note 3: check from step 1 after pop if(current.isChild)
		// note 4: if stackhead.getNodeType == eps->continue
		// TODO step 1: pop, if(current.isChild(T)->setCurrent(T), getProductionRules, 
		
		Stack<Node> st = new Stack<Node>();
		Node sthead = null;
		
		while(true) {
			sthead = st.pop();
			// TODO check terminal/nonterminal
			if(sthead.getNodeType() ==  Node.nodeTypeEnum.String) { // Terminal
				while(!ast.getCurrent().isChild(sthead)) {
					ast.setCurrent(ast.getCurrent().getParent());
				}
				ast.setCurrent(sthead); // TODO why?
			}
			else if(sthead.getNodeType() == Node.nodeTypeEnum.Token) { // Non-Terminal
				if(ast.getCurrent().isChild(sthead)) {
					ast.setCurrent(sthead);
				}
			}
			
			
			
			// TODO if EOF->break
			if(sthead.getData().equals("EOF"))
				break;
			
			return null;
		}
		
		return ast;
	}
	
}
