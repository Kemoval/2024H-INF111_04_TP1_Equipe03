package programme;

import java.util.Random;
import modele.satelliteRelai.SatelliteRelai;
import modele.communication.*;
import modele.centreControle.CentreControle.CentreControle;
import modele.rover.Rover.Rover;
import utilitaires.LinkedList;
import utilitaires.Node;
import utilitaires.Vect2D;

public class ProgrammePrincipal {

    public static void main(String[] args) {
    	
    	testerVect2D();
    	
        LinkedList linkedlist = new LinkedList();
		for (int i = 0; i < 10; i++) {
			linkedlist.ajouterElement(i);
		}

		Node n = linkedlist.getTail();
		int k = linkedlist.getCount();
		for (int i = 0; i < k; i++) {
			System.out.print(n.getData()+ " ");
			n=n.getNext();
		}
		linkedlist.enleverElement();
		linkedlist.enleverElement();
		linkedlist.enleverElement();
		linkedlist.enleverElement();
		n = linkedlist.getTail();
		System.out.print("\n");
		for (int i = 0; i < linkedlist.getCount(); i++) {
			System.out.print(n.getData()+ " ");
			n=n.getNext();
		}

		System.out.print("\n");
		
		System.out.print("\n");
		 Random rand = new Random();
	        System.out.println(rand.nextDouble());
	        SatelliteRelai satellite = new SatelliteRelai();
	        satellite.start();
		
        Rover rover = new Rover(satellite);
        CentreControle centreControle = new CentreControle(satellite);

        // Lier le Rover et le CentreControle au SatelliteRelai
        satellite.lierRover(rover);
        satellite.lierCentrOp(centreControle);

        for(int i = 0; i < 10; ++i) {
            Message msg;
            if (i % 3 == 0) {
                msg = new testmessage(i) {
                    @Override
                    public String toString() {
                        return "Message " + getCompte();
                    }
                };
            } else if (i % 3 == 1) {
               msg = new Nack(i) {
                    @Override
                    public String toString() {
                        return "Nack " + getCompte();
                    }
                };
            } else {
                msg = new NoOp(i) {
                    @Override
                    public String toString() {
                        return "NoOp " + getCompte();
                    }
                };
            }
            centreControle.envoyerMessage(msg);
            rover.envoyerMessage(msg);
            System.out.println("Message " + i + " envoyé.");
        }

        for(int i = 0; i < 10; ++i) {
            Message msg1 = new testmessage(i);
            //Message msg2 = new testmessage(i*2);

            centreControle.envoyerMessage(msg1);
            rover.gestionnaireMessage(msg1);
            //rover.envoyerMessage(msg2);
            //centreControle.gestionnaireMessage(msg2);
        }
        rover.run();
        //centreControle.run();
        

        try {
            Thread.sleep(SatelliteRelai.TEMPS_CYCLE_MS * 15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void testerVect2D() {
        Vect2D v1 = new Vect2D(3, 4);
        Vect2D v2 = new Vect2D(1, 2);

        System.out.println("Vecteur v1 : " + v1.toString());
        System.out.println("Vecteur v2 : " + v2.toString());

        double longueurV1 = v1.getLongueur();
        System.out.println("Longueur attendue de v1 : 5.0, Longueur obtenue : " + longueurV1);

        double angleV1 = v1.getAngle();
        System.out.println("Angle attendu de v1 : 0.93, Angle obtenu : " + angleV1);

        Vect2D diff = v1.calculerDiff(v2);
        System.out.println("Différence attendue entre v1 et v2 : Vect2D{x = 2.0 , y = 2.0}, Différence obtenue : " + diff.toString());

        v1.diviser(2);
        System.out.println("v1 après division par 2 : Vect2D{x = 1.5 , y = 2.0}, v1 obtenu : " + v1.toString());

        v1.ajouter(0.5, -1.0);
        System.out.println("v1 après ajout de (0.5, -1.0) : Vect2D{x = 2.0 , y = 1.0}, v1 obtenu : " + v1.toString());

        boolean isEqual = v1.equals(v2);
        System.out.println("v1 est égal à v2 : attendu false, obtenu : " + isEqual);
        
        System.out.print("\n");
        
    }
}
