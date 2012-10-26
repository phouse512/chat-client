package commandLine;

import java.net.*;
import java.io.*;


//Thread for handling many events in CmdServer
public class ServerThread extends Thread {
	private CmdServer serverConsole;//server base class
	private Socket serverCon;//connection to corresponding client
	private String username;//username of corr. client
	private int clientNum;//index of client
	private DataInputStream input;//input
	private DataOutputStream output;//output

	public ServerThread(CmdServer serverConsole, Socket serverCon, int clientNum) {
		super();//initialize
		this.serverConsole = serverConsole;
		this.serverCon = serverCon;
		this.clientNum = clientNum;
	}
	
	//needed for comp. in drJava/BlueJ
	public ServerThread() {
		System.out.println("No input!");
		this.stop();
	}

	//cont. execution of thread
	public void run() {
		System.out.printf("Client %d has connected.", clientNum);
		while (true) {
			try {
				serverConsole.sendMessages(clientNum, input.readUTF());//sends the received messages to all clients
			} catch (Exception ioe) {
				System.out.println(clientNum + " ERROR reading: " + ioe.getMessage());
				ioe.printStackTrace();
				serverConsole.removeUser(clientNum);
				stop();
			}
		}
	}

	//used for command line, not really used now, nice to have
	public void send(String msg) {
		try {
			output.writeUTF(msg);
			output.flush();
		} catch (IOException ioe) {
			System.out.printf("Error: %s\n", username);
			serverConsole.removeUser(clientNum);
			stop();
		}
	}

	//starts connection to client
	public void startCon() throws IOException {
		input = new DataInputStream(new BufferedInputStream(//init
				serverCon.getInputStream()));
		output = new DataOutputStream(new BufferedOutputStream(
				serverCon.getOutputStream()));
		username = input.readUTF();
		serverConsole.setUser(username, clientNum);
		try{
			serverConsole.sendMessageToAll("Welcome, "+username+"!");//notifies all clients
			output.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//stop connection
	public void stopCon() throws IOException {
		if (serverCon != null)
			serverCon.close();
		if (input != null)
			input.close();
		if (output != null)
			output.close();
	}
	
	//returns username
	public String getUser() {
		return username;
	}
	
	//returns index
	public int getClientNum() {
		return clientNum;
	}
	
	//sets username
	public void setUser(String name) {
		username = name;
		serverConsole.setUser(username, clientNum);
	}
	
	//sets index
	public void setClientNum(int clientNum) {
		if (clientNum > -1)
			this.clientNum = clientNum;
	}
	
	//new message sender
	public void sendMessage(String message){
		try{
			output.writeUTF(message);
			output.flush();
		}catch(Exception e){
			System.out.println("Error sending message");
		}
	}
}
