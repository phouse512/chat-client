package userInterface;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
public class OriginalPopup implements ActionListener
{
    public OriginalPopup(){ //Default Constructor
        //Does nothing
    }
    JFrame jframe; //JFrame variable
    public void createAndShowGUI(){ //Creates and shows GUI
        jframe = new JFrame("Chat Setup"); //Create JFrame
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.getContentPane().setLayout(new BoxLayout(jframe.getContentPane(),BoxLayout.Y_AXIS));
        
        JButton serverButton = new JButton("New Server"); //Create the buttons
        JButton clientButton = new JButton("New Client");
        JPanel serverPanel = new JPanel(); //Create a panel to hold it (spacing)
        serverPanel.setLayout(new BoxLayout(serverPanel,BoxLayout.Y_AXIS));
        JPanel clientPanel = new JPanel(); //Create a panel to hold button (spacing)
        clientPanel.setLayout(new BoxLayout(clientPanel,BoxLayout.X_AXIS));
        
        serverButton.setMaximumSize(new Dimension(1000000000,30));
        serverButton.setAlignmentX((float)0.5);
        serverButton.setToolTipText("Create a new chat server");
        serverButton.setActionCommand("server");
        serverPanel.setBorder(BorderFactory.createEmptyBorder(10,10,5,10));
        serverPanel.add(serverButton);
        clientButton.setMaximumSize(new Dimension(1000000000,30));
        clientButton.setAlignmentX((float)0.5);
        clientButton.setToolTipText("Connect to a currently running chat server");
        clientButton.setActionCommand("client");
        clientPanel.setBorder(BorderFactory.createEmptyBorder(5,10,10,10));
        clientPanel.add(clientButton);
        
        clientButton.addActionListener(this);
        serverButton.addActionListener(this);
        
        jframe.add(serverPanel);
        jframe.add(clientPanel);
        
        jframe.pack();
        jframe.setSize(jframe.getWidth()+70,jframe.getHeight());
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        
        jframe.setVisible(true);
    }
    
    public void destroy(){
        jframe.dispose();
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equalsIgnoreCase("server")){
            destroy();
            String ip;
            try{
            	ip = InetAddress.getLocalHost().getHostAddress();
            }catch(Exception ex){
            	ex.printStackTrace();
            	ip = "Error fetching IP";
            }
            ServerSetup serverSetup = new ServerSetup(ip);
            serverSetup.createAndShowGUI();
        }
        else if(e.getActionCommand().equalsIgnoreCase("client")){
            destroy();
            ClientSetup clientSetup = new ClientSetup();
            clientSetup.createAndShowGUI();
        }
    }
}
