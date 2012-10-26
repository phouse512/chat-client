package commandLine;

import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;


//This class creates the running thread for a client.
public class ClientThread extends Thread {
	protected CmdClient chatClient;//for interacting w/ Client code
	protected Socket connection;//connection to server
	protected DataInputStream input;//text from server

	public ClientThread(Socket socket, CmdClient client) {
		connection = socket;//initializing variables
		chatClient = client;
		openCon();//opens the server connection
		this.start();//starts thread
	}

	public void run() {//will always be executed
		while (true) {
			try {
				chatClient.print(input.readUTF());//prints text to screen
			} catch (IOException e) {
				chatClient.stopConnection();
				e.printStackTrace();
			}
		}
	}

	public void openCon() {//opens connection to server, sets input
		try {
			input = new DataInputStream(connection.getInputStream());//sets input stream
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeCon() {//close connection to server
		try {
			connection.close();//closes socket
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}