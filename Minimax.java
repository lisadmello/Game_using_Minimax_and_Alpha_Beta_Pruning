package assignment2;
import java.io.IOException;

public class Minimax {

	BuildInputBoard bt;

	public Minimax() {
		int random = 0;
	}

	public void minimaxSearch(BuildInputBoard bt) throws IOException {
		this.bt = bt;
		Node answer = minimaxDecision(bt.tree.get(0));
		Alphabeta ab1 = new Alphabeta();
		ab1.writeResult(answer,bt.n);
	}

	public Node minimaxDecision(Node root) throws IOException {

		double v = 0;
		double max = Double.NEGATIVE_INFINITY;
		root.children = bt.createAction(root);
		
		for (int i = 0; i < root.children.size(); i++) {
			v = minValue(root.children.get(i));
			if (v > max)
				max = v;
		}

		for (int i = 0; i < root.children.size(); i++)
			if (root.children.get(i).utility_value == max)
				return root.children.get(i);
		return null;

	}

	public double maxValue(Node root1) throws IOException {
		if (cutoffTest(root1,root1.depth)) {
			double value = eval(root1);
			return value;
		}
		root1.children = bt.createAction(root1);
		if(terminalTest(root1))
		{
			double value = eval(root1);
			return value;
		}
		double v = Double.NEGATIVE_INFINITY;
		
		for (int i = 0; i < root1.children.size(); i++) {
			v = max(v, minValue(root1.children.get(i)));
		}
		root1.utility_value = v;
		return v;
	}

	public double minValue(Node root2) throws IOException {
		if (cutoffTest(root2,root2.depth)) {
			double value = eval(root2);
			return value;
		}
		root2.children = bt.createAction(root2);
		if(terminalTest(root2))
		{
			double value = eval(root2);
			return value;
		}
		double v = Double.POSITIVE_INFINITY;
		for (int i = 0; i < root2.children.size(); i++) {
			v = min(v, maxValue(root2.children.get(i)));
		}
		root2.utility_value = v;
		return v;
	}

	public boolean cutoffTest(Node root3, int depth) {
		if (depth >= bt.depth)
			return true;
		return false;
	}
	public boolean terminalTest(Node root5){
		if(root5.children.isEmpty())
			return true;
		return false;
	}

	public double max(double a3, double b3) {
		if (a3 > b3)
			return a3;
		else
			return b3;
	}

	public double min(double a4, double b4) {
		if (a4 < b4)
			return a4;
		else
			return b4;
	}
	public double eval(Node root4) {
		root4.utility_value = root4.game_score;
		return root4.utility_value;
	}
}