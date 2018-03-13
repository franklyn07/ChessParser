import java.util.*;

public class Pawn extends Piece{

	public Pawn(String colour){
		if(colour.equals("Black")){
			c = Colour.BLACK;
		}else{
			c = Colour.WHITE;
		}
		symbol = 'P';
		value = 1;
	}

	@Override
	public ArrayList<Co_Ordinate> possibleMoves(int x , int y, Colour c){
		//clear old arraylist
		m.clearCoords();
		//generate new coords
		m.pawnMoves(x,y,c);
		//return new coords
		return m.returnCoords();
	}
}