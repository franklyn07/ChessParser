import java.util.*;

public class Queen extends Piece{
	
	public Queen(String colour){
		if(colour.equals("Black")){
			c = Colour.BLACK;
		}else{
			c = Colour.WHITE;
		}
		symbol = 'Q';
		value = 9;
	}

	@Override
	public ArrayList<Co_Ordinate> possibleMoves(int x , int y, Colour c){
		//clear old arraylist
		m.clearCoords();
		//generate new coords
		m.queenMoves(x,y);
		//return new coords
		return m.returnCoords();
	}
}