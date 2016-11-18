package assignment2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildInputBoard {

	public List<Node> tree = Collections.synchronizedList(new ArrayList<Node>());
	private String[] input;
	String youplay;
	int depth;
	public int size, n;
	public double[][] gamematrix;
	public char[][] gameposition;
	public BuildInputBoard(String[] gameinput, int n) {
		size = n;
		input = new String[size];
		input = gameinput;
	}

	public void createBoard() throws IOException {

		String[] store1;
		char[] store2;
		int temp1, k = 0;
		n = Integer.parseInt(input[0]);
		String you = input[2];
		youplay = you;
		int d = Integer.parseInt(input[3]);
		depth = d;
		gamematrix = new double[n][n];
		temp1 = 4 + n;
		for (int i = 4; i < temp1; i++) {
			store1 = input[i].split(" ");
			for (int j = 0; j < n; j++)
				gamematrix[k][j] = Integer.parseInt(store1[j]);
			k = k + 1;
		}
		k=0;
		gameposition = new char[n][n];
		for(int i = temp1;i < size; i++)
		{
			store2 = input[i].toCharArray();
			for (int j = 0; j < n; j++)
				gameposition[k][j] = store2[j];
			k = k + 1;
		}
		
		Node n1 = new Node(null,gamematrix,gameposition,n,0,-1,-1);
		tree.add(n1);
		
	}
	
	public List<Node> createAction(Node state) throws IOException
	{
		List<Node> successors = Collections.synchronizedList(new ArrayList<Node>());
		char theyplay;
		if (youplay.charAt(0) == 'X')
			theyplay = 'O';
		else
			theyplay = 'X';
			Node a;
			a = state;
			int q = a.depth + 1;
			if(q > depth)
				return null;
			int parser =q%2;
			if(parser==0)
			{
				double my_current_score = 0, opponent_score = 0;
				for (int x = 0; x < n; x++)
					for (int y = 0; y < n; y++)
						if (a.play[x][y] == youplay.charAt(0)) {
							// compute your current score
							my_current_score = my_current_score + gamematrix[x][y];
						} else if (a.play[x][y] == theyplay) {
							opponent_score = opponent_score + gamematrix[x][y]; 
						}
				for (int i = 0; i < n; i++) { // for each value in row
					for (int j = 0; j < n; j++) { // for each value in column
						if (a.play[i][j] == '.') { // if current row-column slot of
													// current root is empty
							char[][] temp2 = new char[n][n];
							for (int k = 0; k < n; k++)
								for (int l = 0; l < n; l++)
									temp2[k][l] = a.play[k][l]; // temp = current
																// board
							temp2[i][j] = theyplay; // make stake move at
																// slot
							int child_depth = a.depth + 1;
							Node n2 = new Node("Stake",gamematrix, temp2, n, child_depth, i, j);
							n2.game_score = my_current_score - (opponent_score + gamematrix[i][j]);
							successors.add(n2); 
							a.addChild(n2);
							n2.addParent(a);
						}
					}
				}
				for (int i = 0; i < n; i++) { // for each value in row
					for (int j = 0; j < n; j++) { // for each value in column
						
						if (a.play[i][j] == '.') {
							char[][] temp2 = new char[n][n];
							for (int k = 0; k < n; k++)
								for (int l = 0; l < n; l++)
									temp2[k][l] = a.play[k][l];
							int left = j - 1, right = j + 1, bottom = i + 1, top = i - 1, flag = 0;
							double cs = 0, op = 0;
							cs = my_current_score;
							op = opponent_score;
							if (left >= 0) {
								if (temp2[i][j - 1] == theyplay)
									flag = 1;
							}
							if (right <= (n - 1)) {
								if (temp2[i][j + 1] == theyplay)
									flag = 1;
							}
							if (bottom <= (n - 1)) {
								if (temp2[i + 1][j] == theyplay)
									flag = 1;
							}
							if (top >= 0) {
								if (temp2[i - 1][j] == theyplay)
									flag = 1;
							}
							if (flag == 1) {
								temp2[i][j] = theyplay;
								op = op + gamematrix[i][j];
								if (left >= 0) {
									if (temp2[i][j - 1] == youplay.charAt(0)) {
										temp2[i][j - 1] = theyplay;
										op = op + gamematrix[i][j - 1];
										cs = cs - gamematrix[i][j - 1];
									}
								}
								if (right <= (n - 1)) {
									if (temp2[i][j + 1] == youplay.charAt(0)) {
										temp2[i][j + 1] = theyplay;
										op = op + gamematrix[i][j + 1];
										cs = cs - gamematrix[i][j + 1];
									}
								}
								if (bottom <= (n - 1)) {
									if (temp2[i + 1][j] == youplay.charAt(0)) {
										temp2[i + 1][j] = theyplay;
										op = op + gamematrix[i + 1][j];
										cs = cs - gamematrix[i + 1][j];
									}
								}
								if (top >= 0) {
									if (temp2[i - 1][j] == youplay.charAt(0)) {
										temp2[i - 1][j] = theyplay;
										op = op + gamematrix[i - 1][j];
										cs = cs - gamematrix[i - 1][j];
									}
								}
							int child_depth = a.depth + 1;
							Node n3 = new Node("Raid",gamematrix, temp2, n, child_depth, i, j);
							n3.game_score = cs - op;
							successors.add(n3);
							a.addChild(n3);
							n3.addParent(a);
							}
						}
					}
				}
			}
			else
			{
				double my_current_score = 0, opponent_score = 0;
				for (int x = 0; x < n; x++)
					for (int y = 0; y < n; y++)
						if (a.play[x][y] == youplay.charAt(0)) {
							// compute your current score
							my_current_score = my_current_score + gamematrix[x][y];
						} else if (a.play[x][y] == theyplay) {
							opponent_score = opponent_score + gamematrix[x][y]; 
						}
				for (int i = 0; i < n; i++) { // for each value in row
					for (int j = 0; j < n; j++) { // for each value in column
						if (a.play[i][j] == '.') { // if current row-column slot of
													// current root is empty
							char[][] temp2 = new char[n][n];
							for (int k = 0; k < n; k++)
								for (int l = 0; l < n; l++)
									temp2[k][l] = a.play[k][l]; // temp = current
																// board
							temp2[i][j] = youplay.charAt(0); // make stake move at
																// slot
							int child_depth = a.depth + 1;
							Node n2 = new Node("Stake",gamematrix, temp2, n, child_depth, i, j);
							n2.game_score = (my_current_score + gamematrix[i][j]) - opponent_score;
							successors.add(n2); 
							a.addChild(n2);
							n2.addParent(a);
						}
					}
				}
				for (int i = 0; i < n; i++) { // for each value in row
					for (int j = 0; j < n; j++) { // for each value in column
						
						if (a.play[i][j] == '.') {
							char[][] temp2 = new char[n][n];
							for (int k = 0; k < n; k++)
								for (int l = 0; l < n; l++)
									temp2[k][l] = a.play[k][l];
							int left = j - 1, right = j + 1, bottom = i + 1, top = i - 1, flag = 0;
							double cs = 0, op = 0;
							cs = my_current_score;
							op = opponent_score;
							if (left >= 0) {
								if (temp2[i][j - 1] == youplay.charAt(0))
									flag = 1;
							}
							if (right <= (n - 1)) {
								if (temp2[i][j + 1] == youplay.charAt(0))
									flag = 1;
							}
							if (bottom <= (n - 1)) {
								if (temp2[i + 1][j] == youplay.charAt(0))
									flag = 1;
							}
							if (top >= 0) {
								if (temp2[i - 1][j] == youplay.charAt(0))
									flag = 1;
							}
							if (flag == 1) {
								temp2[i][j] = youplay.charAt(0);
								cs = cs + gamematrix[i][j];
								if (left >= 0) {
									if (temp2[i][j - 1] == theyplay) {
										temp2[i][j - 1] = youplay.charAt(0);
										cs = cs + gamematrix[i][j - 1];
										op = op - gamematrix[i][j - 1];
									}
								}
								if (right <= (n - 1)) {
									if (temp2[i][j + 1] == theyplay) {
										temp2[i][j + 1] = youplay.charAt(0);
										cs = cs + gamematrix[i][j + 1];
										op = op - gamematrix[i][j + 1];
									}
								}
								if (bottom <= (n - 1)) {
									if (temp2[i + 1][j] == theyplay) {
										temp2[i + 1][j] = youplay.charAt(0);
										cs = cs + gamematrix[i + 1][j];
										op = op - gamematrix[i + 1][j];
									}
								}
								if (top >= 0) {
									if (temp2[i - 1][j] == theyplay) {
										temp2[i - 1][j] = youplay.charAt(0);
										cs = cs + gamematrix[i - 1][j];
										op = op - gamematrix[i - 1][j];
									}
								}
							int child_depth = a.depth + 1;
							Node n3 = new Node("Raid",gamematrix, temp2, n, child_depth, i, j);
							n3.game_score = cs - op;
							successors.add(n3);
							a.addChild(n3);
							n3.addParent(a);
							}
						}
					}
				}
			}
			return successors;
	}
}