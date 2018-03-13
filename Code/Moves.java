import java.util.*;

public class Moves{
	
	private ArrayList<Co_Ordinate> coords = new ArrayList<Co_Ordinate>();

	//used for bounds check
	private Utility u = new Utility();

/*	--All void moves methods return possible moves add possible moves to the
	private arraylist, and then the array list is returned through returnCoords*/
	public void kingMoves(int x, int y){
		//add all possible co-ordinate moves
		coords.add(new Co_Ordinate(x,y+1));
		coords.add(new Co_Ordinate(x+1,y));
		coords.add(new Co_Ordinate(x-1,y));
		coords.add(new Co_Ordinate(x,y-1));
		coords.add(new Co_Ordinate(x+1,y+1));
		coords.add(new Co_Ordinate(x-1,y-1));
		coords.add(new Co_Ordinate(x+1,y-1));
		coords.add(new Co_Ordinate(x+1,y+1));
	}

	public void bishopMoves(int x, int y){
		//setting counter for loop
		int i =1;

		//i<8 is the worst case scenario... just in case bishop is in (0,0)
		while(i<8){
			coords.add(new Co_Ordinate(x-i,y+i));
			coords.add(new Co_Ordinate(x+i,y+i));
			coords.add(new Co_Ordinate(x+i,y-i));			
			coords.add(new Co_Ordinate(x-i,y-i));			
			i++;
		}
	}

	public void rookMoves(int x, int y){
		//setting counter for loop
		int i =1;

		//i<8 is the worst case scenario... just in case rook is in (0,0)
		while(i<8){
			coords.add(new Co_Ordinate(x,y-i));
			coords.add(new Co_Ordinate(x+i,y));
			coords.add(new Co_Ordinate(x-i,y));
			coords.add(new Co_Ordinate(x,y+i));
			i++;
		}
	}

	public void queenMoves(int x, int y){
		bishopMoves(x, y);
		rookMoves(x,y);
	}

	public void pawnMoves(int x, int y, Colour c){
		if (c == Colour.BLACK){
			coords.add(new Co_Ordinate(x,y-1));
		}else{
			coords.add(new Co_Ordinate(x,y+1));
		}	
	}

	public void knightMoves(int x, int y){
		//add all possible co-ordinate moves
		coords.add(new Co_Ordinate(x+2,y-1));
		coords.add(new Co_Ordinate(x+1,y-2));
		coords.add(new Co_Ordinate(x-1,y+2));
		coords.add(new Co_Ordinate(x-2,y+1));
		coords.add(new Co_Ordinate(x-2,y-1));
		coords.add(new Co_Ordinate(x-1,y-2));
		coords.add(new Co_Ordinate(x+1,y+2));
		coords.add(new Co_Ordinate(x+2,y+1));
	}

	public ArrayList<Co_Ordinate> returnCoords(){
		//check arraylist for out of bounds coords, and removes them
		//return filtered arraylist
		return u.boundChecker(coords);
	}

	public void clearCoords(){
		coords.clear();
	}
}