import java.util.*;

//using protected such that derived classes are able to access

public abstract class Piece{
	protected Colour c;
	protected char symbol;
	protected int value;

/*	note that the colour is passed as a parameter, however only pawn makes use of it
	since its the only piece that is uni directional. The other overriding methods,
	don't use it.*/
	protected abstract ArrayList<Co_Ordinate> possibleMoves(int x, int y, Colour c);

	protected Moves m = new Moves();

	protected Colour returnColour(){
		return c;
	}

	protected char returnSymbol(){
		return symbol;
	}

	protected int returnValue(){
		return value;
	}	
}