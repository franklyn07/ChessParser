import java.util.*;

public class King extends Piece{

	public King(String colour){
		if(colour.equals("Black")){
			c = Colour.BLACK;
		}else{
			c = Colour.WHITE;
		}
		symbol = 'K';
		value = 20;
	}

	@Override
	public ArrayList<Co_Ordinate> possibleMoves(int x , int y, Colour c){
		//clear old arraylist
		m.clearCoords();
		//generate new coords
		m.kingMoves(x,y);
		//return new coords
		return m.returnCoords();
	}
}