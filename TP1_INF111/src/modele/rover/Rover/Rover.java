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
    public void gestionnaireMessage(Message msg) {

        if (msg != null){
            this.receptionMessageDeSatellite(msg);
            System.out.println("Rover recu message de classe "+msg.getClass().getName()+" \nNumero du message: "+ msg.getCompte());
        }
    }
}
