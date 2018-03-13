import java.util.*;

public class Bishop extends Piece{

	public Bishop(String colour){
		if(colour.equals("Black")){
			c = Colour.BLACK;
		}else{
			c = Colour.WHITE;
		}
		symbol = 'B';
		value = 3;
	}

	@Override
	public ArrayList<Co_Ordinate> possibleMoves(int x , int y, Colour c){
		//clear old arraylist
		m.clearCoords();
		//generate new coords
		m.bishopMoves(x,y);
		//return new coords
		return m.returnCoords();
	}
}