package modele.centreControle.CentreControle;
import modele.communication.*;
import modele.satelliteRelai.SatelliteRelai;

public class CentreControle extends TransporteurMessage {

    SatelliteRelai relai;

    private int numGestionnaire = 0;

    public CentreControle(SatelliteRelai satellite) {
        super();
        this.relai = satellite;
    }

    @Override
	public void envoyerMessage(Message msg) {
        this.relai.envoyerMessageVersRover(msg);
        this.messagesEnvoyes.ajouterElement(msg);
    }

    @Override
    public void gestionnaireMessage(Message msg) {
        if (msg != null){
            this.receptionMessageDeSatellite(msg);
            System.out.println("CentreControle recu message de classe "+msg.getClass().getName()+" \nCompte: "+ msg.getCompte());  
        }
    }
}