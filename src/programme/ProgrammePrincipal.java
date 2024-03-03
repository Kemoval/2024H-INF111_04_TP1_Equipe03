package programme;

import java.util.Random;
import modele.satelliteRelai.SatelliteRelai;
import modele.communication.Message;
import modele.centreControle.CentreControle.CentreControle;
import modele.rover.Rover.Rover;

public class ProgrammePrincipal {

    public static void main(String[] args) {
        Random rand = new Random();
        System.out.println(rand.nextDouble());
        SatelliteRelai satellite = new SatelliteRelai();
        satellite.start();

        Rover rover = new Rover(satellite);
        CentreControle centreControle = new CentreControle(satellite);
        satellite.lierRover(rover);
        satellite.lierCentrOp(centreControle);

        for(int i = 0; i < 10; ++i) {
        	
            Message msg = new Message(i) {
                @Override
                public String toString() {
                    return "Message " + getCompte();
                }
            };
            centreControle.envoyerMessage(msg);
            rover.envoyerMessage(msg);
            System.out.println("Message " + i + " envoyé.");
        }

        try {
            Thread.sleep(SatelliteRelai.TEMPS_CYCLE_MS * 15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
