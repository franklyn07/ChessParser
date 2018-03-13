import java.io.*;
import java.util.*;

public class InputReader{

	//will hold parsed commands
	private ArrayList<Command> commandList = new ArrayList<Command>();;

	//will hold object of type filereader
	private FileReader fr;

	//will hold bufferedreader
	private BufferedReader br;

	//size of file
	private int size =0;

	//saves one line command
	private String str;

	//stores the character
	private int n;

	//Used to tokenize command
	private StringTokenizer st;

	//using utility class for conversion
	private Utility u;

	InputReader(String fileloc) throws IOException{

		fr = new FileReader(fileloc);
		br = new BufferedReader(fr);

		//if file is not found, the following command won't be executed
		u = new Utility();
		str = null;
		readInput();

	}

	//continoually reads input until eof is reached
	private void readInput(){

		try{
			//reads each line and passes it to parseCmd for parsing
			while((str = br.readLine())!=null){
				parseCmd(str);
			}
		}catch(IOException io){
			System.out.println("No end of file found: " + io);
		}
	}
	
	private void parseCmd(String str){
		String player;
		String command;
		String source;
		String destination;
		int source_x, source_y, dest_x, dest_y;

		String[] tmp;

		//tokenize the produced string
		st = new StringTokenizer(str,",<> ");

		/*if invalid command catch error and proceed to read next*/
		try{
			//checks if amount of tokens read is valid
			if(st.countTokens()>4){
				throw new IllegalArgumentException("Unexpected amount of arguments: " + st.countTokens());
			}

			//if amount of tokens is right, we store them
			player = st.nextToken();
			command = st.nextToken();
			source = st.nextToken();
			destination = st.nextToken();

			//utility throws out of bounds exception if character is unkown
			source_x = u.char2int(source.charAt(0));
			source_y = u.char2index(source.charAt(1));

			dest_x = u.char2int(destination.charAt(0));
			dest_y = u.char2index(destination.charAt(1));

			//checks that only legal colours are used
			if(player.equals("b") || player.equals("w")){
				//do nothing
			}else{
				throw new IllegalArgumentException(player);
			}

			//checks that only legal command moves are used
			if(command.equals("move") || command.equals("eat")){
				//do nothing
			}else{
				throw new IllegalArgumentException(command);
			}

			//if no error arises we add the command to command list
			commandList.add(new Command(player, command, source_x, source_y, dest_x, dest_y));
		}catch(IndexOutOfBoundsException e){
			System.out.println("Bad command: " + e);
		}catch(IllegalArgumentException a){
			System.out.println("Bad argument: " + a);
		}
	}

	public ArrayList<Command> returnCommands(){
		return commandList;
	}

	public void closeReaders(){

		try{
			br.close();
			fr.close();
		}catch(IOException io){
			System.out.println("File couldn't be closed: " + io);
		}
	}
}