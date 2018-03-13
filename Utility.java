import java.util.*;

public class Utility{

	//converts a char into an int
	public int char2int(char c){
		switch(c){
			case 'A':
			case 'a':
				return 0;
			case 'B':
			case 'b':
				return 1;
			case 'C':
			case 'c':
				return 2;
			case 'D':
			case 'd':
				return 3;
			case 'E':
			case 'e':
				return 4;
			case 'F':
			case 'f':
				return 5;
			case 'G':
			case 'g':
				return 6;
			case 'H':
			case 'h':
				return 7;
			default:
				throw new IndexOutOfBoundsException("Bad character: " + c);		
		}
	}

	//converts an int into a char
	public char int2char(int i){
		switch(i){
			case 0:
				return 'A';
			case 1:
				return 'B';
			case 2:
				return 'C';
			case 3:
				return 'D';
			case 4:
				return 'E';
			case 5:
				return 'F';
			case 6:
				return 'G';
			case 7:
				return 'H';
			default:
				throw new IndexOutOfBoundsException("Bad integer: " + i);
		}
	}

	//converts index to int
	public int index2int(int i){
		return i+1;
	}

	//converts command to index (value of 8 is indexed 7)
	public int char2index(char c){
		switch(c){
			case '8':
				return 7;
			case '7':
				return 6;
			case '6':
				return 5;
			case '5':
				return 4;
			case '4':
				return 3;
			case '3':
				return 2;
			case '2':
				return 1;
			case '1':
				return 0;
			default: 
				throw new IndexOutOfBoundsException("Bad character: " + c);
		}
	}

	//converts index to piece name
	public char index2piece(int i){
		switch(i){
			case 0:
				return 'P';
			case 1:
				return 'B';
			case 2:
				return 'K';
			case 3:
				return 'R';
			case 4:
				return 'Q';
			case 5:
				return 'K';
			default:
				throw new IndexOutOfBoundsException("Out of bounds index" + i);
		}
	}

	//checks that possible coordinates are not out of bounds
	public ArrayList<Co_Ordinate> boundChecker(ArrayList<Co_Ordinate> al){
		//Employ iterator to iterate over arraylist
		ListIterator<Co_Ordinate> iteratef = al.listIterator();

		//while list still contains anothre element 
		while(iteratef.hasNext()){
		Co_Ordinate temp = iteratef.next();
			if(temp.get_x()<8 &&temp.get_y()<8 && temp.get_x()>=0 &&temp.get_y()>=0){
				continue;
				//if still in bounds iterate to next eleement
			}else{
				//else remove from list
				iteratef.remove();
			}
		}

		//when finished return filtered arraylist
		return al; 
	}


}