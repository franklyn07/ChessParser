//singleton class
public class Board{
	//declaring static ansi colours constants
	private static final String RESET = "\u001B[0m";
	private static final String BLACK_PIECE = "\u001B[30m";
	private static final String WHITE_PIECE = "\u001B[37m";
	private static final String BLACK_BACKGROUND = "\u001B[40m";
	private static final String WHITE_BACKGROUND = "\u001B[47m";

	//size of board
	private static final int boardSize = 8;

	//private variable that will store board instance
	private static Board instance;
	
	//2d array declaration holding boxes
	private Box [][] board;

	//private constructor for board
	private Board(){
		board = new Box[boardSize][boardSize];

		//giving board empty boxes
		for (int i = 0; i <boardSize; i++){
			for(int j = 0; j<boardSize; j++){
				board [i][j] = new Box (i,j);
			}
		}
	}

	//initialisation method which returns board
	public static Board getInstance(){
		//check if board is already initialised
		if(instance == null){
			instance = new Board();
		}

		//always return reference to board
		return instance;
	}

	//method that puts piece at specified location
	public void putPieceBoard(int x, int y, Piece p){
		(board[x][y]).putPieceBox(p);
	}

	//get specific box from board
	public Box getBox(int x, int y){
		return board[x][y];
	}

	//paints board
	public void paintBoard(){
		Piece temp;
		for (int j = 7; j >=0; j--){
			//print indexes
			System.out.print(BLACK_BACKGROUND + WHITE_PIECE + (j+1) +RESET);
			for(int i = 0; i<boardSize; i++){
				//stores the supposed returned piece
				temp = board[i][j].returnPiece();
				//if box is not empty draw symbol of piece, else draw -
				if (temp != null){
					//if piece is black draw it black, else draw it white
					if(temp.returnColour() == Colour.BLACK){
						System.out.print(BLACK_PIECE + temp.returnSymbol() + RESET);
					}else{
						System.out.print(WHITE_PIECE + temp.returnSymbol() + RESET);
					}
				}else{
					System.out.print("-");
				}
			}
			System.out.println("");
		}

		//print indexes
		System.out.println(BLACK_BACKGROUND + WHITE_PIECE + " ABCDEFGH" +RESET);
	}
}