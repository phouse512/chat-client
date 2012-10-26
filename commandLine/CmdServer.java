package commandLine;

import java.io.*;
import java.net.*;
import java.util.*;


//Base class for the client hosting the server.
public class CmdServer implements Runnable {
	private ServerThread[] conToClients;//thread for connection to clients
	private String[] usernames;//username storage
	private ServerSocket serverCon;//Server sockets for I/O
	private Thread threadStart;//thread for running the class
	private int numOfClients;//keep track of number of clients
	private int port;//port opened

	public CmdServer(int port) {
		try {
			System.out.println("Starting connection on port " + port);
			serverCon = new ServerSocket(port);//new socket on port
			conToClients = new ServerThread[100];//initialization
			threadStart = null;
			usernames = new String[100];
			numOfClients = 0;
			startCon();//start server
			System.out.println("Connection started!");
		} catch (IOException ioe) {
			System.out.println("Cannot use port entered.");
			System.exit(0);//quits on error
		}
		this.port = port;//init
	}

	//needed for comp. in drJava/BlueJ
	public CmdServer() {
		System.out.print("Error! No port.");
		System.exit(0);
	}

	//continuous exec.
	public void run() {
		while (threadStart != null) {//check for validity
			try {
				System.out.println("Thread wait for client!");
				System.out.println("Looking for clients...");
				newClient(serverCon.accept());//accepts clients
			} catch (IOException ioe) {
				System.out.println("Client connection error.");
				stopCon();
			}
		}
	}

	//start server
	public void startCon() {
		if (threadStart == null) {
			threadStart = new Thread(this);
			threadStart.start();
		}
	}

	//stop server
	public void stopCon() {
		if (threadStart != null) {
			threadStart.stop();
			threadStart = null;
		}
	}
	
	//creates newClient
	private synchronized void newClient(Socket socket) {
		System.out.println("Running client connector!");
		if (numOfClients < conToClients.length) {
			conToClients[numOfClients] = new ServerThread(this, socket,
					numOfClients);
			try {//starts connection, adds to num
				conToClients[numOfClients].startCon();
				conToClients[numOfClients].start();
				numOfClients++;
				System.out.println("Client accepted!");
			} catch (IOException ioe) {
				System.out.println("Error opening thread: " + ioe);
			}
		} 
		else
			System.out.println("Client refused: maximum " + conToClients.length
					+ " reached.");
	}

	//remove client
	public synchronized void removeUser(int clientNum) {
		if (clientNum > -1) {//stops stuff
			try {
				conToClients[clientNum].stopCon();
			} catch (IOException e) {
				System.out.println("Error removing user");
			}
			conToClients[clientNum].stop();
			System.out.println("Removing user " + usernames[clientNum]);
			if (clientNum < numOfClients - 1) {
				for (int count = clientNum + 1; count < numOfClients; count++) {
					conToClients[count - 1] = conToClients[count];//kills bubbles
					conToClients[count - 1].setClientNum(count - 1);
					usernames[count - 1] = usernames[count];
					numOfClients--;
				}	
			}
		}
	}
	
	//main class for output
	public synchronized void sendMessages(int clientNum, String input) {
		if (input.equalsIgnoreCase(".quit")) {//check for quit command
			conToClients[clientNum].send(".quit");
			removeUser(clientNum);
			System.out.println("REMOVING USER!");
		} 
		else if (input.length() > 10) {
			if (input.substring(0, 10).equalsIgnoreCase(".username ")) {//check for change username command
				String temp = usernames[clientNum];
				conToClients[clientNum].setUser(input.substring(10));
				for (int count = 0; count < numOfClients; count ++)
					conToClients[count].send(temp + " has changed their username to " + input.substring(10) + ".");
			}
			else {//otherwise
				for (int count = 0; count < numOfClients; count ++)
					conToClients[count].send(usernames[clientNum] + ": " + input);
			}

		}
		else {//sends message
			for (int count = 0; count < numOfClients; count ++)
				conToClients[count].send(usernames[clientNum] + ": " + input);
		}
	}

	//returns list of users
	public String[] getUsers() {
		return usernames;
	}

	//sets username
	public void setUser(String name, int ID) {
		usernames[ID] = name;
	}
	
	//alerts clients of server close
	public void alertServerShutdown(){
		for(ServerThread con:conToClients){
			if(con != null){
				con.sendMessage("Server has shutdown");
			}
		}
	}
	
	//needed for GUI
	public void sendMessageToAll(String message){
			for(ServerThread con:conToClients){
				if(con != null){
					con.sendMessage(message);
				}
			}
	}
	
	//returns port for easy access
	public int getPort(){
		return port;
	}
}
