import java.util.*;

public class Knight extends Piece{

	public Knight(String colour){
		if(colour.equals("Black")){
			c = Colour.BLACK;
		}else{
			c = Colour.WHITE;
		}
		symbol = 'N';
		value = 3;
	}

	@Override
	public ArrayList<Co_Ordinate> possibleMoves(int x , int y, Colour c){
		//clear old arraylist
		m.clearCoords();
		//generate new coords
		m.knightMoves(x,y);
		//return new coords
		return m.returnCoords();
	}
}