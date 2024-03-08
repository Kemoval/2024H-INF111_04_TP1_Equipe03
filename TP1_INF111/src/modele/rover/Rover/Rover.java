package modele.rover.Rover;

import modele.communication.*;
import modele.satelliteRelai.SatelliteRelai;


public class Rover extends TransporteurMessage {

    SatelliteRelai relai;

    private int numGestionnaire = 0;

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
    public void gestionnaireMessage(Message msg) {

        if (msg != null){
            if (msg.getCompte() == numGestionnaire){
                this.receptionMessageDeSatellite(msg);
                System.out.println("Rover recu message de classe "+msg.getClass().getName()+" \nCompte: "+ msg.getCompte());
                ++numGestionnaire;
            }
            else {
                Nack nack = new Nack(numGestionnaire);
                envoyerMessage(nack);
            }
        }
    }
}
