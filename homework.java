package assignment2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class homework {

	public static void main(String[] args) throws IOException {
		int n = 0, i;
		String input_file = "input.txt"; // read input.txt path
		String line,mode;
		FileReader a = new FileReader(input_file);
		BufferedReader b = new BufferedReader(a);
		while ((line = b.readLine()) != null) // till you do not count all the lines
			n = n + 1; // count lines
		b.close();
		String[] gameinput = new String[n]; // create new array for line storage
		FileReader c = new FileReader(input_file);
		BufferedReader d = new BufferedReader(c);
		for (i = 0; i < n; i++)
			gameinput[i] = d.readLine().trim();
		mode= gameinput[1];
		switch(mode)
		{
		case "MINIMAX":
			BuildInputBoard bib = new BuildInputBoard(gameinput,n);
			bib.createBoard();
			Minimax mx= new Minimax();
			mx.minimaxSearch(bib);
			break;
		case "ALPHABETA":
			BuildInputBoard bib1 = new BuildInputBoard(gameinput,n);
			bib1.createBoard();
			Alphabeta ab = new Alphabeta();
			ab.alphaBetaPruning(bib1);
			break;
		}
	}

}