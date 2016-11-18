package assignment2;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Alphabeta {
	BuildInputBoard bt;

	public Alphabeta() {
		int random = 0;
	}

	public void alphaBetaPruning(BuildInputBoard bt) throws IOException {
		this.bt = bt;
		Node answer = alphaBetaSearch(bt.tree.get(0));
		writeResult(answer,bt.n);
	}

	public Node alphaBetaSearch(Node root) throws IOException {
		double v;
		root.children = bt.createAction(root);
		v = maxValue(root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		for (int i = 0; i < root.children.size(); i++) {
			if (root.children.get(i).utility_value == v)
				return root.children.get(i);
		}
		return null;

	}

	public double maxValue(Node root1, double a1, double b1) throws IOException {
		double v;
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
		v = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < root1.children.size(); i++) {
			v = max(v, minValue(root1.children.get(i), a1, b1));
			if (v >= b1) {
				root1.utility_value = v;
				return v;
			}
			a1 = max(a1, v);
		}
		root1.utility_value = v;
		return v;
	}

	public double minValue(Node root2, double a2, double b2) throws IOException {
		double v;
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
		v = Double.POSITIVE_INFINITY;
		for (int i = 0; i < root2.children.size(); i++) {
			v = min(v, maxValue(root2.children.get(i), a2, b2));
			if (v <= a2) {
				root2.utility_value = v;
				return v;
			}
			b2 = min(b2, v);
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

	public void writeResult(Node answer,int n) throws IOException
	{
		BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
		char c;
		int r,cal;
		r = answer.row + 1;
		cal = answer.column + 65;
		c = ((char) cal);
	
		out.write(c+""+r+" "+answer.move);
		out.newLine();
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				out.write(answer.play[i][j]);
			}
			out.newLine();
		}
		out.close();
	}
}