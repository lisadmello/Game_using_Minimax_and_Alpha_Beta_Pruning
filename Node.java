package assignment2;
import java.util.*;

public class Node {
	double[][] state = null;
	char[][] play = null;
	int depth = 0;
	String move=null;
	Node parent = null;
	List<Node> children = null;
	double utility_value=0;
	double game_score=0;
	int size = 0;
	int row=-1;
	int column=-1;

	public Node(String move, double game[][], char gamepos[][], int size, int depth,int row,int column) {
		this.size = size;
		this.move=move;
		this.row=row;
		this.column=column;
		state = new double[size][size];
		state = game;
		play = new char[size][size];
		play = gamepos;
		this.depth = depth;
		this.children = Collections.synchronizedList(new ArrayList<>());
	}
	public void addChild(Node x)
	{
		children.add(x);									//add the child
	}
	public void addParent(Node x)
	{
		parent = x;
	}
}