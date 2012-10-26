package userInterface;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientSetup implements ActionListener {
	private JFrame jframe;
	private JButton setupButton;
	private JTextField ipField;
	private JTextField portField;
	
	//Constructor for client setup
	public ClientSetup(){

	}

	//Function to create the set up options for beginning server/client
	public void createAndShowGUI(){
		jframe = new JFrame("Client Setup");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.getContentPane().setLayout(
				new BoxLayout(jframe.getContentPane(), BoxLayout.Y_AXIS));
		JPanel ipPanel = new JPanel();
		ipPanel.setAlignmentX((float) 0.5);
		ipField = new JTextField(15);
		ipField.setText("");
		ipPanel.setLayout(new BoxLayout(ipPanel, BoxLayout.X_AXIS));
		ipPanel.add(new JLabel("Server IP: "));
		ipPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		ipPanel.add(ipField);
		ipPanel.setMaximumSize(new Dimension(10000000, ipPanel.getHeight()));
		jframe.add(ipPanel);
		jframe.add(new JPanel());

		JPanel portPanel = new JPanel();
		portPanel.setLayout(new BoxLayout(portPanel, BoxLayout.X_AXIS));
		portPanel.add(new JLabel("Server Port: "));
		portField = new JTextField(4);
		portField.setText("9999");
		portPanel.add(portField);
		portPanel.setMaximumSize(new Dimension(10000000, ipPanel.getHeight()));
		portPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
		jframe.add(portPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		setupButton = new JButton("Start Client");
		setupButton.setAlignmentX((float) 0.5);
		setupButton.setActionCommand("start");
		setupButton.addActionListener(this);
		buttonPanel.add(setupButton);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		jframe.add(buttonPanel);

		jframe.pack();
		jframe.setSize(jframe.getWidth() + 70, jframe.getHeight());
		jframe.setLocationRelativeTo(null);
		jframe.setResizable(false);
		jframe.setVisible(true);
	}
	
	//kill the JFrame
	public void destroy(){
		jframe.dispose();
	}
	
	//Act on the event from the jButton.
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equalsIgnoreCase("start")){
			String host = ipField.getText(); //Get host text
			int port = 0;
			try{
				host = ipField.getText();
				port = Integer.parseInt(portField.getText()); //get IP
				if(port<0 || port>9999 ){
					throw new IllegalArgumentException();
				}
				if(!host.equalsIgnoreCase("")){
					Main.startClientMode(host,port);
					destroy();
				}
				else{
					JOptionPane.showMessageDialog(jframe,"IP cannot be empty!","Illegal Input",JOptionPane.ERROR_MESSAGE);
				}
			}catch(Exception exc){
				JOptionPane.showMessageDialog(jframe,"Ports must be from 1 to 9999!","Illegal Input",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
