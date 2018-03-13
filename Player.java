import java.util.*;

public class Player{
	
	//Arraylists holding pieces belogning to player
	private ArrayList<King> king;
	private ArrayList<Queen> queen;
	private ArrayList<Pawn> pawn;
	private ArrayList<Bishop> bishop;
	private ArrayList<Rook> rook;
	private ArrayList<Knight> knight;

	//colour of player
	private Colour c;

	//turn setter
	private boolean myTurn;

	//default names
	private String name;

	//amount of pieces player has
	private int numKing;
	private int numQueen;
	private int numPawn;
	private int numBishop;
	private int numRook;
	private int numKnight;

	Player(Colour colour){
		//giving player a colour
		c = colour;

		//initialisation of arraylist, with construction of player
		king = new ArrayList<King>();
		queen = new ArrayList<Queen>();
		pawn = new ArrayList<Pawn>();
		bishop = new ArrayList<Bishop>();
		rook = new ArrayList<Rook>();
		knight = new ArrayList<Knight>();

		if(c == Colour.WHITE){
			//setting default name
			name = "White Player";

			//white always starts the game
			myTurn = true;

			//adding white pieces to lists
			king.add(new King("White"));
			queen.add(new Queen("White"));

			for(int i = 0; i<2; i++){
				bishop.add(new Bishop("White"));
				rook.add(new Rook("White"));
				knight.add(new Knight("White"));
			}

			for(int i = 0; i <8 ;i++){
				pawn.add(new Pawn("White"));
			}

		}else{
			//setting default name
			name = "Black Player";

			//black is second to play
			myTurn = false;

			//adding black pieces to lists
			king.add(new King("Black"));
			queen.add(new Queen("Black"));

			for(int i = 0; i<2; i++){
				bishop.add(new Bishop("Black"));
				rook.add(new Rook("Black"));
				knight.add(new Knight("Black"));
			}

			for(int i = 0; i <8 ;i++){
				pawn.add(new Pawn("Black"));
			}
		}

		//update number of pieces player has
		getRemainingPieces();
	}

	//updates the amount of pieces a player has remaining
	private void getRemainingPieces(){
		numKing = king.size();
		numQueen = queen.size();
		numPawn = pawn.size();
		numBishop = bishop.size();
		numRook = rook.size();
		numKnight = knight.size();
	}

	//get colour of player
	public Colour getColour(){
		return c;
	}

/*	gets the last object in the arraylist and returns it.
	size-1 because indexing starts at 0.
	These methods will be used by player to put his pieces
	on the board*/
	public King getKing(){
		return king.get(numKing-1);
	}

	public Queen getQueen(){
		return queen.get(numQueen-1);
	}

	public Pawn getPawn(){
		return pawn.get(numPawn-1);
	}

	public Bishop getBishop(){
		return bishop.get(numBishop-1);
	}

	public Rook getRook(){
		return rook.get(numRook-1);
	}

	public Knight getKnight(){
		return knight.get(numKnight-1);
	}

	//gets turn of player
	public boolean getTurn(){
		return myTurn;
	}

	//sets turn
	public void setTurn(){
		if(myTurn == false){
			myTurn = true;
		}else{
			myTurn = false;
		}
	}

}