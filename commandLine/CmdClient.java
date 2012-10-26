package commandLine;

import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

import userInterface.*;


//CmdClient is a thread that sends messages and prints received messages to screen. It is the base for the GUI.
public class CmdClient implements Runnable {
	protected DataInputStream input;//I/O streams
	protected DataOutputStream output;//cont...
	protected ClientThread clientThread;//thread for continuous execution of reading input.
	protected Socket connection;//connection to server, passed to thread
	protected Thread threadStart;//starts CmdClient
	protected Chat chatWindow;//GUI


	public CmdClient(String ip, int port,Chat chat) {
		System.out.println("Connecting to server...");
		try {
			connection = new Socket(ip, port);//opens connection to server
			System.out.println("You're online!");
			startConnection();//starts socket
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Error connecting to server!\nTry again!","Connnection Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);//quits upon bad connection
		}
		this.chatWindow = chat;//initializes chatWindow
	}
	
	public CmdClient() {
		//needs this or wont compile in drJava/BlueJ
	}

	//starts socket
	public void startConnection() {
		input = new DataInputStream(System.in);//sets input stream
		try {
			output = new DataOutputStream(connection.getOutputStream());//sets output stream
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (threadStart == null) {
			clientThread = new ClientThread(connection, this);//makes client thread
			threadStart = new Thread(this);//initialize running thread
			threadStart.start();//start running thread
		}
	}

	//stops client
	public void stopConnection() {
		if (threadStart != null) {
			System.exit(0);//kills stuff
		}
	}

	//continuous execution
	public void run() {
		while (threadStart != null) {
			try {
				output.writeUTF(input.readLine()); //OUTPUT
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				output.flush();//clears output
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//sends message, necessary for GUI
	public void sendMessage(String message){
		try{
			output.writeUTF(message);
			output.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//prints input from server
	public void print(String message) { //INPUT
		if (message.equalsIgnoreCase(".quit")) {//string to look for
			System.out.println("Cya later");
			stopConnection();
		} else
			chatWindow.addMessage(message);
	}
	
	//returns itself
	public CmdClient getThis(){
		return this;
	}
}
