package userInterface;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.GraphicsEnvironment;
import java.awt.Font;

/**
 * Popup represents a Swing based pop-up. Popup is immutable.
 * 
 * @author Karl Otness
 * 
 */
public class Popup {
    protected String title;
    protected String body;
    protected Icon icon;
    protected int millis;
    private JDialog display;
/**
 * Constructor. Accepts a title, body test, icon (optional),millisecond display value.
 * @param popupTitle the title that is displayed at the top of the pop-up.
 * @param popupBody the body text of the pop-up.
 * @param popupIcon the icon that is displayed on the pop-up. For no icon send {@code null}.
 * @param millis the millisecond time for which the pop-up is displayed.
 */
    public Popup(String popupTitle, String popupBody, Icon popupIcon, int millis) {
        title = popupTitle; //Constructor
        body = popupBody;
        icon = popupIcon;
        this.millis = millis;
    }
    
    /**
     * Get the value of the title text of this Popup.
     * @return the title text of this Popup.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Get a {@link String} describing this Popup object.
     * @return a {@link String} describing this Popup object.
     */
    public String toString() {
    	return getTitle()+": "+getBody();
    }
    
    /**
     * Returns a new Popup object which is a copy of the current Popup object.
     * @return a Popup that is a copy of the current Popup object.
     */
    public Popup clone(){
    	return new Popup(getTitle(),getBody(),getIcon(),getMillis());
    }

    /**
     * Returns the body text of the current Popup object.
     * @return a {@link String} that is the body text of the Popup object.
     */
    public String getBody() {
        return body;
    }

    /**
     * Returns the {@link Icon} of the current Popup object. 
     * @return {@link Icon} the Icon of the current Popup object.
     */
    public Icon getIcon() {
        return icon;
    }

    public int getMillis() {
        return millis;
    }

    public void show() { //Creates and displays the Popup
    	if(display != null){
    		display.dispose();
    		display = null;
    	}
        display = new JDialog(new JFrame(),false);
        display.getContentPane().setLayout(new BoxLayout(display.getContentPane(),BoxLayout.X_AXIS));
        display.setUndecorated(true);
        display.setResizable(false);
        JLabel uiTitle = new JLabel(getTitle());
        JLabel uiBody = new JLabel(getBody());
        JPanel panel = new JPanel();
        uiTitle.setAlignmentX((float)0);
        Font boldFont = new Font(uiTitle.getFont().getName(),Font.BOLD,uiTitle.getFont().getSize());
        uiTitle.setFont(boldFont);
        uiTitle.setForeground(java.awt.Color.RED);
        uiBody.setFont(new Font(uiBody.getFont().getName(),Font.PLAIN,uiBody.getFont().getSize()));
        uiBody.setAlignmentX((float)0);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(uiTitle);
        panel.add(uiBody);
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(getIcon());
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        display.add(iconLabel);
        display.add(panel);
        display.setMinimumSize(new Dimension(200,40));
        display.pack();
        display.setFocusable(false);
        display.setFocusableWindowState(false);
        Rectangle maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        display.setLocation((int)(maxBounds.getX()+maxBounds.getWidth()-display.getWidth()),(int)(maxBounds.getY()+maxBounds.getHeight()-display.getHeight()));
        display.setAlwaysOnTop(true);
        display.setVisible(true);
    }

    public void hide() { //Destroys the popup
    	if(display != null){
    		display.dispose();
    	}
        display = null;
    }
}