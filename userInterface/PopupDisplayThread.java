package userInterface;

public class PopupDisplayThread extends Thread {
	Popup pop;
	public PopupDisplayThread(Popup pop){
		this.pop = pop;
	}
	
	public void run(){
		pop.show(); //Show popup
		try{
			sleep(pop.getMillis()); //Wait
		}catch(Exception e){
			e.printStackTrace();
		}
		pop.hide(); //Destroy Popup
		pop = null; //Set Popup for garbage collection
	}
}
