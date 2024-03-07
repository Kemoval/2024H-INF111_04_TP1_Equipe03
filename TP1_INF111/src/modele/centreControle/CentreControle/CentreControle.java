package modele.centreControle.CentreControle;
import modele.communication.*;
import modele.satelliteRelai.SatelliteRelai;

public class CentreControle extends TransporteurMessage {

    SatelliteRelai relai;

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
    protected void gestionnaireMessage(Message msg) {
        if (msg != null){
            this.messagesRecus.ajouterElement(msg);
            System.out.println("Message recu de classe "+msg.getClass().getName()+" \nCompte: "+ msg.getCompte());
        }
    }

}
