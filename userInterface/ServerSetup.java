package userInterface;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ServerSetup implements ActionListener //Sets up chat in server mode
{   
    private String ip;
    public ServerSetup(String ip){
        this.ip = ip;
    }
    JFrame jframe;
    JTextField portField;
    
    public void createAndShowGUI(){ //Creates and shows the GUI
        jframe = new JFrame("Server Setup");
        jframe.getContentPane().setLayout(new BoxLayout(jframe.getContentPane(),BoxLayout.Y_AXIS));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel portPanel = new JPanel();
        portPanel.setAlignmentX((float)0.5);
        portField = new JTextField(4);
        portField.setText("9999");
        portField.addActionListener(this);
        portField.setActionCommand("create");
        portField.setCaretPosition(4);
        portPanel.setLayout(new BoxLayout(portPanel,BoxLayout.X_AXIS));
        portPanel.add(new JLabel("Server Port: "));
        portPanel.setBorder(BorderFactory.createEmptyBorder(10,10,5,10));
        portPanel.add(portField);
        portPanel.setMaximumSize(new Dimension(10000000,portPanel.getHeight()));
        
        jframe.add(portPanel);
        jframe.add(new JPanel());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
        JButton createButton = new JButton("Create Server");
        createButton.setActionCommand("create");
        createButton.setAlignmentX((float)0.5);
        createButton.addActionListener(this);
        buttonPanel.add(createButton);
        jframe.add(buttonPanel);
        
        JLabel ipLabel = new JLabel("Your IP is: "+ip);
        ipLabel.setAlignmentX((float) 0.5);
        ipLabel.setAlignmentY(1);
        ipLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        jframe.add(ipLabel);
        
        jframe.pack();
        jframe.setSize(jframe.getWidth()+70,jframe.getHeight());
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        jframe.setVisible(true);
    }
    
    public void destroy(){ //Disposes the GUI
        jframe.dispose();
    }
    
    public void actionPerformed(ActionEvent e){ //Action listener
    	if(e.getActionCommand().equalsIgnoreCase("create")){
    		try{
    			int port = Integer.parseInt(portField.getText());
    			if(port>9999||port<1){
    				throw new IllegalArgumentException();
    			}
    			Main.startServerMode(port);
    			destroy();
    		}catch(Exception exc){
    			JOptionPane.showMessageDialog(jframe,"Ports must be between 1 and 9999!","Illegal Input",JOptionPane.ERROR_MESSAGE);
    		}
    	}
    }
}
