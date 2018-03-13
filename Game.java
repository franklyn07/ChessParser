import java.util.*;

public class Game{

	//will be used for conversion purposes only
	private Utility u = new Utility();

	//declaring storage for removed pieces
	private ArrayList<Piece> removedPieces; 

	//each game has a black player and white player
	private Player black;
	private Player white;

	//variable holding the board
	private Board b;

	//variable that holds whether game is over or not
	private boolean checkMate;

	//setting up game watcher which will be updating observers
	private GameWatcher gw;

	//setting up an observer for white player and one for black player
	private PlayerObserver pow;
	private PlayerObserver pob;

	//variables holding number of moves performed and those skipped
	private int movesPerformed;
	private int movesSkipped;
	

	Game(){
		//initialising game to not won
		checkMate = false;

		//initialising arraylist which will store removed pieces
		removedPieces = new ArrayList<Piece>();

		//getting board instance (in singleton pattern)
		b = Board.getInstance();

		//initialisng players
		black = new Player(Colour.BLACK);
		white = new Player(Colour.WHITE);

		//players putting their pieces on board
		//--Black Player
		playerPieceConstructor(black);
		//--White Player
		playerPieceConstructor(white);

		//prints initial state of board
		b.paintBoard();

/*		initialising number of moves performed and those skipped
		(note that we will only be keeping track of moves which where parsed correctly
		however for some reason they were invalid (ex. skip of turn, or destination 
		of eat is empty))*/
		movesPerformed = 0;
		movesSkipped = 0;

		//initialising game watcher and adding two observers watching it
		gw = new GameWatcher();
		pow = new PlayerObserver(gw);
		pob = new PlayerObserver(gw);

	}

	//method that starts game

	public void gameStart(ArrayList<Command> al){
		ListIterator<Command> iteratef = al.listIterator();
		//while list still contains anothre element and game has not been won yet
		while(iteratef.hasNext() && checkMate == false){
			//will hold current command
			Command commands = iteratef.next();

			//will catch all exceptions related to move and eat
			try{
				//check if correct turn
				if(commands.returnPlayer().equals("w") && white.getTurn() == true || 
					commands.returnPlayer().equals("b") && black.getTurn() == true){
					//check type of command
					if(commands.returnCmd().equals("eat")){
						//try to perform command eat
						eat(commands.returnSX(),commands.returnSY(),commands.returnDX(),commands.returnDY(), white.getTurn());
						
						//if no catch than move is performed
						movesPerformed = movesPerformed +1;
					}else if (commands.returnCmd().equals("move")){
						//try to perform command move
						move(commands.returnSX(),commands.returnSY(),commands.returnDX(),commands.returnDY(), white.getTurn());

						//if no catch than move is performed
						movesPerformed = movesPerformed +1;
					}
					//swapping turns - done in this scope st. if a bad command arises, the turn only swaps on good commands
					black.setTurn();
					white.setTurn();
				}else{
					throw new IllegalStateException("Not your turn " + commands.returnPlayer());
				}
			//if we catch an error, that means the move will be skipped
			}catch(IllegalStateException ise){
				movesSkipped = movesSkipped+1;
				System.out.println(ise);
			}catch(IllegalArgumentException iae){
				movesSkipped = movesSkipped+1;
				System.out.println(iae);
			}
		}

		//prints statistics for each player
		gameReporter();
		//if game ends stop observers
		gw.unregister(pow);
		gw.unregister(pob);

		//we print last state of board
		b.paintBoard();
	}
	

	//--The following methods are private, since they will only be used by this class

	//method that allows player to put a piece on board
	private void putPiece(int x, int y, Piece p){
		b.putPieceBoard(x,y,p);
	}

	//method that puts all pieces for players on board
	private void playerPieceConstructor(Player p){
		if(p.getColour() == Colour.BLACK){
			putPiece(4,7,p.getKing());
			putPiece(3,7,p.getQueen());
			putPiece(0,7,p.getRook());
			putPiece(1,7,p.getKnight());
			putPiece(2,7,p.getBishop());
			putPiece(7,7,p.getRook());
			putPiece(6,7,p.getKnight());
			putPiece(5,7,p.getBishop());
			for(int i = 0; i<8;i++){
				putPiece(i,6,p.getPawn());
			}
		}else{
			putPiece(4,0,p.getKing());
			putPiece(3,0,p.getQueen());
			putPiece(0,0,p.getRook());
			putPiece(1,0,p.getKnight());
			putPiece(2,0,p.getBishop());
			putPiece(7,0,p.getRook());
			putPiece(6,0,p.getKnight());
			putPiece(5,0,p.getBishop());
			for(int i = 0; i<8;i++){
				putPiece(i,1,p.getPawn());
			}
		}
	}

	//method that checks whether the MOVE for the piece is valid
	private boolean checkMove(int start_x, int start_y, int finish_x, int finish_y, Piece p){
		//stores the array list of the possible moves temporarily
		ArrayList<Co_Ordinate> al = new ArrayList<Co_Ordinate>();
		al = p.possibleMoves(start_x, start_y, p.returnColour());

		//stores the co ordinate object temporarily
		Co_Ordinate temp;

		//iterate through co-ordinates
		for(int i =0 ; i<al.size();i++){
			//temp will hold temporary coordinate
			temp = al.get(i);

			/*get x and y co ordinates from object and compare them to supposed destination.
			if supposed destination is a possible move, then check whether there is a blocking piece.
			Note that this check is only done on queen, bishop and rook, since knight can skip over pieces,
			whilst pawn and king can only do one step moves, and if they are being blocked (aka destination
			box is already full, error will be caught in move())*/
			if(temp.get_x() == finish_x && temp.get_y() == finish_y){
				if(p instanceof Bishop){
					if(isBishopBlocked(start_x,start_y,finish_x,finish_y)){
						return false;
					}else{
						return true;
					}
				}else if(p instanceof Rook){
					if(isRookBlocked(start_x,start_y,finish_x,finish_y)){
						return false;
					}else{
						return true;
					}

				}else if(p instanceof Queen){
					/*since the queen moves just like bishops and rooks, we check whether one of the possible
					movements is blocked. Sinc she will only be assuming one behaviour, if one of the possible
					behaviours is blocked, we block.*/
					if(isBishopBlocked(start_x,start_y,finish_x,finish_y)||isRookBlocked(start_x,start_y,finish_x,finish_y)){
						return false;
					}else{
						return true;
					}
				}else{
					//clearing temporary al
					al.clear();
					return true;
				}
			} 
		}
		//clearing temporary al
		al.clear();
		return false;
	}

	//method that does the MOVE operation
	private void move(int start_x, int start_y, int finish_x, int finish_y, boolean isWhiteTurn) throws IllegalArgumentException{
		//will hold piece located at start coordinate
		Piece p = (b.getBox(start_x,start_y)).returnPiece();
		//check if start box is empty -> it returns a null piece if empty
		if(p == null){
			throw new IllegalArgumentException("Start destination is empty! Start: "+ u.int2char(start_x) + "," + u.index2int(start_y));
		//check if piece being moved, is of correct player (ex. white turn -> white piece)
		}else if ((isWhiteTurn == true && p.returnColour() == Colour.WHITE) ||(isWhiteTurn == false && p.returnColour() == Colour.BLACK)){
			//if not empty check if move is doable and destination box is empty
			if(checkMove(start_x, start_y, finish_x, finish_y, p)==true && (b.getBox(finish_x,finish_y)).isEmpty() == true){
				//if doable, assign start box status to new box/position
				(b.getBox(finish_x,finish_y)).putPieceBox((b.getBox(start_x,start_y)).returnPiece());
				//clean old box from piece and set it to empty
				cleanBox(start_x, start_y);
				//notify gamewatcher that piece has moved and no piece was eaten and it will notify all observers
				gw.setPieceUsed(p);
			}else{
				throw new IllegalArgumentException("Illegal move! Start: " + u.int2char(start_x) + "," + u.index2int(start_y) + " Finish: " + u.int2char(finish_x) + "," + u.index2int(finish_y) );
			}
		}else{
			throw new IllegalArgumentException(p.returnColour() + " piece, was going to be moved by opponent!");
		}
	}

	private boolean checkEat(int start_x, int start_y, int finish_x, int finish_y, Piece p){
		/*catering for pawn diagonal eating (not normal movement): in order to do so, we fool the 
		check move function. Since this function will only generate one step forward move, we check
		that the diagonal move(eat move) is found one to the left of the normal move or one to the
		right. If its the case, then the eat is valid (since it will be a diagonal movement) and we 
		return true*/
		if(p instanceof Pawn){
			if(checkMove(start_x, start_y, finish_x-1, finish_y, p) == true) {
				//will store temporarily a piece found at finish
				Piece piece = b.getBox(finish_x,finish_y).returnPiece();
				//if move is valid, check if destination box contains an opponents piece
				if(piece != null && piece.returnColour() != p.returnColour()){
					return true;
				}else{
					return false;
				}
			}else if(checkMove(start_x, start_y, finish_x+1, finish_y, p) == true){
				//will store temporarily a piece found at finish
				Piece piece = b.getBox(finish_x+1,finish_y).returnPiece();
				//if move is valid, check if destination box contains an opponents piece
				if(piece != null && piece.returnColour() != p.returnColour()){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}		
		}else{
			//move must be valid for rest of pieces(they eat using normal moves)
			if(checkMove(start_x, start_y, finish_x, finish_y, p) == true){
				//will store temporarily a piece found at finish
				Piece piece = b.getBox(finish_x,finish_y).returnPiece();
				//if move is valid, check if destination box contains an opponents piece
				if(piece != null && piece.returnColour() != p.returnColour()){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}	
		}
	}

	private void eat(int start_x, int start_y, int finish_x, int finish_y, boolean isWhiteTurn) throws IllegalArgumentException{
		//will hold piece located at start co-ordinate
		Piece p = (b.getBox(start_x,start_y)).returnPiece();

		//will hold piece which will be removed
		Piece removedPiece = null;

		//check if start box is empty -> it will contain null piece
		if(p == null){
			throw new IllegalArgumentException("Start destination is empty! Start: "+ u.int2char(start_x) + "," + u.index2int(start_y));
		//check if piece being moved, is of correct player (ex. white turn -> white piece)
		}else if((isWhiteTurn == true && p.returnColour() == Colour.WHITE) ||(isWhiteTurn == false && p.returnColour() == Colour.BLACK)){
			//check if eat is doable
			if(checkEat(start_x, start_y, finish_x, finish_y, p)==true){
				/*if doable put your piece instead of the one in box and 
				return the piece in box, and store it in al of removed pieces.*/
				removedPiece = b.getBox(finish_x,finish_y).changePiece(p);
				removedPieces.add(removedPiece);
				//clear original box
				cleanBox(start_x,start_y);
				//notify game watcher what piece has been eaten and what piece has moved
				gw.setPieceUsed(p);
				gw.setPieceEaten(removedPiece);
			}else{
				throw new IllegalArgumentException("Illegal eat! Start: " + u.int2char(start_x) + "," + u.index2int(start_y) + " Finish: " + u.int2char(finish_x) + "," + u.index2int(finish_y) );
			}
		}else{
			throw new IllegalArgumentException(p.returnColour() + " piece, was going to be moved by opponent!");
		}
	}

	//method that deletes piece from box (used for cleanup)
	private void cleanBox(int x, int y){
		(b.getBox(x,y)).putPieceBox(null);
	}

	//method that checks if king has been killed
	private void checkMate(Piece p){
		if (p instanceof King){
			checkMate = true;
			System.out.println("The King Has Been Slain!");
		}
	}

	//checks whether bishop move is blocked assuming that the move is valid
	private boolean isBishopBlocked(int start_x, int start_y, int finish_x, int finish_y){
		//check x and y gradients between start_x and finish_x (same applies for y)
		int gradX = (finish_x>start_x) ? 1:-1;
		int gradY = (finish_y>start_y) ? 1:-1;

		//check whether all boxes found between finish and start are empty
		/*Note the looping condition uses absolute just in case start_x is greater than finish_x.
		Also we include a -1 since we don't want to check destination box, since it will be checked
		in another method*/
		for(int i = 0 ; i < Math.abs(finish_x-start_x)-1 ; ++i){
			//check if block is empty. If one block is not empty, we can stop loop and return true
			if(b.getBox(start_x+((i+1)*gradX),start_y+((i+1)*gradY)).isEmpty()==false){
				System.out.println("Box filled");
				return true;
			}
		}

		//else if no block is filled we return false
		return false;
	}

	private boolean isRookBlocked(int start_x, int start_y, int finish_x, int finish_y){
		//check if we will be moving horizontally or vertically
		if(start_x == finish_x){
			//if vertically check whether we will be moving up or down and check boxes accoridngly
			int gradY = (finish_y>start_y) ? 1:-1;

			for(int i =0; i<Math.abs(finish_y-start_y)-1; ++i){
				if(b.getBox(finish_x,start_y+((i+1)*gradY)).isEmpty()==false){
					return true;
				}
			}

			return false;
		}else{
			//if horizontally chekc wheter we will be moving left or right and check boxes accordingly 
			int gradX = (finish_x>start_x) ? 1:-1;

			for(int i =0; i<Math.abs(finish_x-start_x)-1; ++i){
				if(b.getBox(start_x+((i+1)*gradX),finish_y).isEmpty()==false){
					return true;
				}
			}

			return false;
		}
	}

	//used to publish game stats after it finishes
	private void gameReporter(){
		//player report for black player
		PlayerReport black = new PlayerReport(pob.returnPlayerMoves(), pob.returnScore(), pob.returnPieceMoves(), pob.returnHasWon(), pob.returnColour());
		
		//player report for white player
		PlayerReport white = new PlayerReport(pow.returnPlayerMoves(), pow.returnScore(), pow.returnPieceMoves(), pow.returnHasWon(), pow.returnColour());
		
		//print report for player
		black.generateReport();
		white.generateReport();

		System.out.println("===========================================");
		System.out.println("Note that eat was considered as 2 moves: move and then eat.");

		//check who won
		if(pob.returnScore()>pow.returnScore() && pow.returnHasWon() == false){
			System.out.println(pob.returnColour() + " has won with : " + pob.returnScore() + " points!");
		}else if(pob.returnScore()<pow.returnScore() && pob.returnHasWon() == false){
			System.out.println(pow.returnColour() + " has won with : " + pow.returnScore() + " points!");
		}else if (pow.returnHasWon() == true){
			System.out.println(pow.returnColour() + " has won through Checkmate!");
		}else if (pob.returnHasWon() == true){
			System.out.println(pob.returnColour() + " has won through Checkmate!");
		}else{
			System.out.println("Stalemate!");
		}

		System.out.println("Total amount of moves performed: " + movesPerformed);
		System.out.println("Total amount of moves skipped: " + movesSkipped);
		System.out.println("===========================================");

	}
}