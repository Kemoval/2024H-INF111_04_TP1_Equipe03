package modele.rover.Rover;

import modele.communication.*;


public class Rover extends TransporteurMessage {

	  public Rover() {
	        super();
	    }

	    @Override
	    protected void envoyerMessage(Message msg) {}

	    @Override
	    protected void gestionnaireMessage(Message msg) {}

}
