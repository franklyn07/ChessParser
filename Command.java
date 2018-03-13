public class Command{
	
	//will hold player
	private String player;

	//will hold command
	private String command;

	//will hold source co-ords
	private int s_x, s_y;

	//will hold destination co-ords
	private int d_x,d_y;

	Command(String player, String cmd, int source_x, int source_y, int dest_x, int dest_y){
		this.player = player;
		command = cmd;
		s_x = source_x;
		s_y = source_y;
		d_x = dest_x;
		d_y = dest_y;
	}

	//getters to get attributes
	public String returnPlayer(){
		return player;
	}

	public String returnCmd(){
		return command;
	}

	public int returnSX(){
		return s_x;
	}

	public int returnSY(){
		return s_y;
	}

	public int returnDX(){
		return d_x;
	}

	public int returnDY(){
		return d_y;
	}
}