/*package Server.Main;

import Server.stoneColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Player extends Thread {
	private Socket socket;
	BufferedReader input;
	PrintWriter output;

	Player opponent;
	stoneColor color;

	public Player(Socket socket){
		try{
			this.socket=socket;
			input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output=new PrintWriter(socket.getOutputStream(),true);
		} catch (IOException e){
			System.out.println(e.getStackTrace());
		}
	}

	public String setBoard() throws WrongPlayerInitiation{
		try {
			String line = input.readLine();
			return line;
		} catch (IOException e){
			throw new WrongPlayerInitiation();
		}
	}
} */