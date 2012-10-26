package userInterface;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import commandLine.*;

public class Main {
	private static CmdServer server;
	private static CmdClient client;
	public static void main(String[] args){
		//----------------------------//
		//  Set GUI theme to 'Nimbus' //
		//----------------------------//
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    //nimbus not available
			try {
			    // Set cross-platform Java L&F (also called "Metal")
		        UIManager.setLookAndFeel(
		            UIManager.getCrossPlatformLookAndFeelClassName());
		    } catch(Exception exc){
		    	e.printStackTrace();
		    }
		}//END UI SETUP.

		OriginalPopup originalPopup = new OriginalPopup();
		originalPopup.createAndShowGUI();
	}
	
	//Create startServerMode function to start server at the parameter port
	public static void startServerMode(int port){
		System.out.println("Server mode");
		Chat chat = new Chat();
		server = new CmdServer(port); 
		chat.createAndShowGUI();
		client = new CmdClient("localhost",port,chat);
		chat.setClient(client);
		chat.setServer(server);
	}
	
	//A function which creates a client that is able to chat based on server and port parameters
	public static void startClientMode(String host,int port){
		Chat chat = new Chat();
		chat.createAndShowGUI();
		System.out.println("Client mode");
		client = new CmdClient(host,port,chat);
		chat.setClient(client);
	}
}
