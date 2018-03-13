public class Box{
	
	//made final so that they cannot be altered once initialised
	private final int x;
	private final int y;

	private boolean isEmpty;
	private Piece p;

	//Empty box constructor
	Box(int x, int y){
		this.x = x;
		this.y = y;
		isEmpty = true;
		p = null;
	}

	//return wether piece is empty
	public boolean isEmpty(){
		return isEmpty;
	}

	//assigns piece to box
	public void putPieceBox(Piece p){
		this.p = p;
		modify_isEmpty();
	}


	//handles whether a box is empty or not
	private void modify_isEmpty(){
		if (isEmpty == true){
			isEmpty = false;
		}else{
			isEmpty = true;
		}
	}

	//replaces current piece with new piece and returns old piece
	public Piece changePiece(Piece newPiece){
		//for every method call a variable temp which holds the piece to return is created.
		Piece temp = p;
		//store newPiece;
		p = newPiece;
		return temp;
	}

	//return contained piece
	public Piece returnPiece(){
		return p;
	}

	//return x value
	public int get_x(){
		return x;
	}

	//return y value
	public int get_y(){
		return y;
	}
}