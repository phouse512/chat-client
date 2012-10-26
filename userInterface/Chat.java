package userInterface;

import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import commandLine.CmdClient;
import commandLine.CmdServer;

public class Chat extends JFrame implements ActionListener{ //MAIN chat GUI
private static final long serialVersionUID = 2148154737566174542L; //AUTO GENERATED
private JTextArea textArea;
private CmdClient cmdClient;
private JTextField jtf = new JTextField();
private CmdServer server = null;
//Settings menu items below
JCheckBoxMenuItem messageNotify;
JCheckBoxMenuItem autoScroll;


	public Chat() {
	}
		
	public void createAndShowGUI(){	
		//-------------------------//
		//   Set up JFrame frame   // 
		//-------------------------//
		setTitle("JPK Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		
		
		setSize(600, 375);
		
		
		//-------------------------//
		//   Set up JMenu Bar	   // 
		//-------------------------//
		JMenuBar jmb = new JMenuBar();
		setJMenuBar(jmb);
		
		JMenu networkMenu = new JMenu("Network");
		//JMenu helpMenu = new JMenu("Help");

		jmb.add(networkMenu);
		//jmb.add(helpMenu);
		JMenuItem exitChoice = new JMenuItem("Exit");
		exitChoice.setToolTipText("Exits the program. If you are the server, ends the server.");
		exitChoice.setActionCommand("exit");
		exitChoice.addActionListener(this);
		networkMenu.add(exitChoice);
		
		//Add JMenuItem which creates an option to display user IP
		JMenuItem ipChoice = new JMenuItem("My Status");
		ipChoice.setToolTipText("Displays status information");
		ipChoice.setActionCommand("getStatus");
		ipChoice.addActionListener(this);
		networkMenu.add(ipChoice);
		
		//Add JMenu Setting which allows for users to change Chat options
		JMenu settingsMenu = new JMenu("Settings");
		JMenuItem clearOption = new JMenuItem("Clear Log");
		clearOption.setToolTipText("Clears the chat area");
		clearOption.setActionCommand("clear");
		clearOption.addActionListener(this);
		settingsMenu.add(clearOption);
		messageNotify = new JCheckBoxMenuItem("New Message Popup");
		messageNotify.setSelected(true);
		messageNotify.setToolTipText("Sets if new messages should be announced");
		settingsMenu.add(messageNotify);
		autoScroll = new JCheckBoxMenuItem("Scroll to New Messages");
		autoScroll.setSelected(true);
		autoScroll.setToolTipText("Sets if the chat area should scroll to new messages");
		settingsMenu.add(autoScroll);
		jmb.add(settingsMenu);
		
		//Set up and display JMenu which opens Help options for the user
		JMenu helpMenu = new JMenu("Help");
		JMenuItem helpChoice = new JMenuItem("Help");
		helpChoice.setToolTipText("Displays helpful information about JPK Chat Client");
		helpChoice.setActionCommand("help");
		helpChoice.addActionListener(this);
		helpMenu.add(helpChoice);
		
		JMenuItem aboutChoice = new JMenuItem("About");
		aboutChoice.setToolTipText("Displays information about the creation of JPK Chat Client");
		aboutChoice.setActionCommand("about");
		aboutChoice.addActionListener(this);
		helpMenu.add(aboutChoice);
		
		jmb.add(helpMenu);
		
		//-------------------------//
		//   Set up BoxLayout      //
		//-------------------------//
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		
		//-------------------------//
		//   Set up JTextArea      //
		//-------------------------//
		
		textArea = new JTextArea("", 15, 10);
		
		//textArea.setPreferredSize(new Dimension(200,100));
		textArea.setEditable(false);
		textArea.append("Enter your username in the chat box"+"\n");
		textArea.setLineWrap(true);
		getContentPane().add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane);
		
		
		
		//-------------------------//
		//   Set up new JPanel     //
		//-------------------------//
		JPanel newPanel = new JPanel(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		
		
		//-------------------------//
		//   Set up JTextField     //
		//-------------------------//
		jtf = new JTextField(30);
		jtf.addActionListener(this);
		jtf.setActionCommand("send");
		JPanel jptextField = new JPanel();
		//jptextField.setLayout(new BorderLayout(1,10));
		
		jptextField.add(new JLabel("Message:"));
		jptextField.add(jtf);
		
		getContentPane().add(jptextField);
				
		//-------------------------//
		//     Set up JButton      //
		//-------------------------//
		JButton button = new JButton("Enter");
		button.addActionListener(this);
		button.setActionCommand("send");
		jptextField.add(button);
		
		//Set up the window listener which allows user mouse input for closing of the JFrame
		super.addWindowListener(new WindowListener() {
            public void windowClosed(WindowEvent arg0) {
                stopProgram();
            }
            public void windowActivated(WindowEvent arg0) {
            }
            public void windowClosing(WindowEvent arg0) {
            }
            public void windowDeactivated(WindowEvent arg0) {
            }
            public void windowDeiconified(WindowEvent arg0) {
            }
            public void windowIconified(WindowEvent arg0) {
            }
            public void windowOpened(WindowEvent arg0) {
            }
        });
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Set properties of JFrame
		jtf.setText("");
		setLocationRelativeTo(null);
		setResizable(false);
		pack();
		setVisible(true);
		jtf.requestFocus();
		
	}
	
	//Kill JFrame
	public void destroy(){
		this.dispose();
	}
	
	//Add the user input from the server to the JTextArea
	public void addMessage(String message){
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
	    
		//Format the output to username + date
		textArea.append("("+sdf.format(cal.getTime())+") "+message+"\n");
			String popupMessage = message;
			if(messageNotify.isSelected()){
				if(popupMessage.length()> 50){
					popupMessage = popupMessage.substring(0,35);
					popupMessage = popupMessage + "...";
				}
				popupMessage = popupMessage + "   ";
				Popup newMessage = new Popup("New Message",popupMessage,null,1500);
				new PopupDisplayThread(newMessage).start();
			}
			//Auto scroll the JScrollPane
			if(autoScroll.isSelected()){
				textArea.setCaretPosition(textArea.getText().length());
			}
	}
	
	//Return the chat object
	public Chat getThis(){
		return this;
	}
	
	//Activate events when mouse events are performed on the JMenu
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equalsIgnoreCase("send")){
			try{
				if(!jtf.getText().equals("")){
					cmdClient.sendMessage(jtf.getText());
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			jtf.setText("");
			jtf.requestFocus();
		}
		else if(e.getActionCommand().equalsIgnoreCase("exit")){
			stopProgram();
		}
		else if(e.getActionCommand().equalsIgnoreCase("getStatus")){
			try{
				if(server == null){
					JOptionPane.showMessageDialog(this,"Your IP is: "+java.net.InetAddress.getLocalHost().getHostAddress(),"My Status",JOptionPane.PLAIN_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(this, "Your ip is: "+ java.net.InetAddress.getLocalHost().getHostAddress() + "\nServer at port: "+ server.getPort(), "Your IP",JOptionPane.PLAIN_MESSAGE);
				} //END ELSE
			}catch(Exception ex){
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this,"Unable to get your IP!","IP Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equalsIgnoreCase("clear")){
			textArea.setText("");
		}
		else if (e.getActionCommand().equalsIgnoreCase("help")){
			JOptionPane.showMessageDialog(this, "JPK Chat Client Verson 1.3 Help"
					 + "\n Networking Help: \n    • Be sure that all users are logged on to the same Local Area Network"
					 + "\n Chat Help:  \n    • Be sure to enter your username first \n    • Auto Scroll and other features can be turned on and off in the Settings menu \n    • \".username <username>\" changes your username \n    • \".quit\" ends the program", "JPK Chat Client Help", JOptionPane.QUESTION_MESSAGE);
		}
		else if (e.getActionCommand().equalsIgnoreCase("about")) {
		JOptionPane
				.showMessageDialog(
						this,
						"JPK Chat Client: \nVersion 1.3 \n Written by: \n     • Jerry \n     • Phil \n     • Karl",
						"About JPK Chat Client",
						JOptionPane.INFORMATION_MESSAGE);
		} 
	}//END ACTION LISTENER
	
	//Change server to new server object
	public void setServer(CmdServer server){
		this.server = server;
	}
	//Set client as new client object
	public void setClient (CmdClient client){
		cmdClient = client;
	}
	
	//End the server and kill the program
	private void stopProgram(){
		if(server!=null){
			System.out.println("Ending server and program!");
			server.alertServerShutdown();
			System.exit(0);
		}
		else{
			cmdClient.sendMessage(".quit");
			System.out.println("Ending program!");
			System.exit(0);
		}
	}

}
