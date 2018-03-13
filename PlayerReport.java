//will hold each players statistics
public class PlayerReport{

	//will store number of Moves performed
	private int numberOfMoves;

	//will store all moves performed per piece
	private int[][] piecesMoves;

	//will store score
	private int score;

	//will store if he has won by check mate or not
	private boolean checkMate;

	//will store colour of player
	private Colour playerColour;

	//used to convert from index in array, to symbol of piece
	private Utility u;

	PlayerReport(int numberOfMoves, int score, int[][] piecesMoves, boolean checkMate, Colour playerColour){
		u = new Utility();
		this.numberOfMoves = numberOfMoves;
		this.score = score;
		this.piecesMoves = piecesMoves;
		this.checkMate = checkMate;
		this.playerColour = playerColour;
	}

	//used to print the final player report
	public void generateReport(){
		System.out.println("===========================================");
		System.out.println("Player Colour: " + playerColour);
		System.out.println("Score Obtained: " + score);
		System.out.println("Number Of Moves Performed: " + numberOfMoves);
		printMovesPerPiece();
		printMostPieceUsed();
		System.out.println("===========================================");
	}

	//used to print moves per piece
	private void printMovesPerPiece(){
		for(int i = 0; i<6; i++){
			System.out.print("Piece: " + u.index2piece(piecesMoves[i][0]));
			System.out.print(" || Number Of Times Used: " + piecesMoves[i][1]);
			System.out.println("");
		}
	}

	//used to get most piece used by player;
	private void printMostPieceUsed(){
		//will store maximum value
		int max = 0;
		//will store index where it is stored (row)
		int indexOfMax = 0;

		//iterate through all values of moves
		for (int i = 0;i<6 ;i++) {
			//current value is greater than current max, replace it
			if(max<piecesMoves[i][1]){
				max = piecesMoves[i][1];
				indexOfMax = i;
			}
		}

		System.out.print("Most used piece: " + u.index2piece(piecesMoves[indexOfMax][0]));
		System.out.print(" || Number of Times used: " + max);
		System.out.println("");
	}
}