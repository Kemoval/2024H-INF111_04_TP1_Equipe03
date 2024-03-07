package modele.rover.Rover;

import modele.communication.*;
import modele.satelliteRelai.SatelliteRelai;


public class Rover extends TransporteurMessage {

    SatelliteRelai relai;

    public Rover(SatelliteRelai satellite) {
        super();
        this.relai = satellite;
    }

    @Override
	public void envoyerMessage(Message msg) {
        this.relai.envoyerMessageVersCentrOp(msg);
        this.messagesEnvoyes.ajouterElement(msg);
    }

    @Override
    protected void gestionnaireMessage(Message msg) {
        if (msg != null){
            this.messagesRecus.ajouterElement(msg);
            System.out.println("Message recu de classe "+msg.getClass().getName()+" \nCompte: "+ msg.getCompte());
        }
    }

}
