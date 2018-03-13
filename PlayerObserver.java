public class PlayerObserver implements Observer{
	
	//will hold score for player
	private int score;

	//will hold if player has won by killing king
	private boolean hasWon;

	/*will hold number of times each piece was used first column we store piece index(which is translated
	through utilitiy class) and second we store no times moved*/
	private int [][] usage = {{0,0},{1,0},{2,0},{3,0},{4,0},{5,0}};

	//used for utility converter to convert index to string
	private Utility u = new Utility();

	//will hold number of moves performed by player
	private int noMoves;

	//will be used as a counter of player observers
	private static int observerTracker = 0;

	//will be used to track observer
	private int observerID;
	private Colour observerColour;

	//will store local reference to GameWatcher so that we can use its methods
	private Subject GameWatcher;

	public PlayerObserver(Subject GameWatcher){

		//initialising score
		score =0;


		//initialising number of moves
		noMoves = 0;

		//initialising winning by killing king to false
		hasWon = false;

		//storing reference to instance GameWatcher
		this.GameWatcher = GameWatcher;

		//assigning the current count as an id to the observer
		observerID = observerTracker;
		observerTracker++;

		//observer 0 will always be white and the other one black
		observerColour = (observerID == 0) ? Colour.WHITE : Colour.BLACK;

		//adding this observer to the observer list watching the game
		GameWatcher.register(this);
	}

	@Override
	public void update(Piece pieceUsed, Piece pieceEaten){
		
		//incrementing number of times a piece was used (pieceUsed not null) only if it is my piece
		if(pieceUsed != null){
			if(pieceUsed.returnColour() == observerColour){
				//traverse all pieces in the usage list - 6 being number of pieces
				for(int i = 0; i<6; i++){
					//if symbol piece matches to its index, than increment by one the usage value and stop iterating
					if(pieceUsed.returnSymbol() == (u.index2piece(usage[i][0]))){
						usage[i][1] = (usage[i][1]) + 1;
						break;
					}
				}
				//increment player number of moves (this covers also eating, since a peice cannot eat w/o moving first)
				noMoves = noMoves+1;
			}
		}

		//incrementing score only if the piece eaten is not yours and is not null
		if (pieceEaten != null){
			if(pieceEaten.returnColour() != observerColour){
				//if piece eaten is king the observer will know player has won
				if (pieceEaten instanceof King){
					hasWon = true;
				}
				score = score + pieceEaten.returnValue();
			}
		}
	}

	//returns array which contains number of moves per piece
	public int [][] returnPieceMoves(){
		return usage;
	}

	//return the rest of the observer attributes
	public int returnScore(){
		return score/2;
	}

	public boolean returnHasWon(){
		return hasWon;
	}

	public int returnPlayerMoves(){
		return noMoves;
	}

	public Colour returnColour(){
		return observerColour;
	}
}