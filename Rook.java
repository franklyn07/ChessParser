import java.util.*;

public class Rook extends Piece{

	public Rook(String colour){
		if(colour.equals("Black")){
			c = Colour.BLACK;
		}else{
			c = Colour.WHITE;
		}
		symbol = 'R';
		value = 5;
	}

	@Override
	public ArrayList<Co_Ordinate> possibleMoves(int x , int y, Colour c){
		//clear old arraylist
		m.clearCoords();
		//generate new coords
		m.rookMoves(x,y);
		//return new coords
		return m.returnCoords();
	}
}