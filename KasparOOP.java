import java.io.*;

public class KasparOOP{
	public static void main (String args[]) throws IOException{
		
		Game g = new Game();
		InputReader ir = null;

		try{
			ir = new InputReader(args[0]);
			g.gameStart(ir.returnCommands());
		}catch(IOException io){
			System.out.println("File cannot be opened");
		}finally{
			try{
				ir.closeReaders();
			}catch(NullPointerException n){
				System.out.println("File was never opened");
			}
		}


	}
}