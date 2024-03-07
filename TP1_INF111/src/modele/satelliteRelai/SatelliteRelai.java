package modele.satelliteRelai;

/**
 * Classe simulant le satellite relai
 * 
 * Le satellite ne se contente que de transferer les messages du Rover vers le centre de contrôle
 * et vice-versa.
 * 
 * Le satellite exécute des cycles à intervale de TEMPS_CYCLE_MS. Période à
 * laquelle tous les messages en attente sont transmis. Ceci est implémenté à
 * l'aide d'une tâche (Thread).
 * 
 * Le relai satellite simule également les interférence dans l'envoi des messages.
 * 
 * Services offerts:
 *  - lierCentrOp
 *  - lierRover
 *  - envoyerMessageVersCentrOp
 *  - envoyerMessageVersRover
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2024
 */

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import modele.centreControle.CentreControle.CentreControle;
import modele.communication.*;
import modele.rover.Rover.Rover;
import utilitaires.LinkedList;

public class SatelliteRelai extends Thread{
	
	public static final int TEMPS_CYCLE_MS = 500;
	static final double PROBABILITE_PERTE_MESSAGE = 0.15;
	
	ReentrantLock lock = new ReentrantLock();
	
	private Random rand = new Random();

	private LinkedList queueCtr = new LinkedList();

	private LinkedList queueRover = new LinkedList();

	private Rover rov;

	private CentreControle ctr;

	/**
	 * Méthode permettant d'envoyer un message vers le centre d'opération
	 * param msg, message à envoyer
	 */

	public void lierCentrOp(CentreControle centre){
		this.ctr = centre;
	}

	public void lierRover(Rover rover){
		this.rov = rover;
	}

	public void envoyerMessageVersCentrOp(Message msg) {
	    lock.lock();
	    try {
	        if(rand.nextDouble() > PROBABILITE_PERTE_MESSAGE){
	            queueCtr.ajouterElement(msg);
	        } else {
	            System.out.println("Message " + msg.getCompte() + " perdu en envoyant vers CentreControle.");
	        }
	    } finally {
	        lock.unlock();
	    }
	}


	/**
	 * Méthode permettant d'envoyer un message vers le rover
	 * @param msg, message à envoyer
	 */
	public void envoyerMessageVersRover(Message msg) {
	    lock.lock();
	    try {
	        if(rand.nextDouble() > PROBABILITE_PERTE_MESSAGE){
	            queueRover.ajouterElement(msg);
	        } else {
	            System.out.println("Message " + msg.getCompte() + " perdu en envoyant vers Rover.");
	        }
	    } finally {
	        lock.unlock();
	    }
	}

	@Override
	public void run() {
	    while(true) {
	        for (int i = 0; i < 5; i++) {
	            Nack message = new Nack(i); 
	            envoyerMessageVersCentrOp(message);
	            System.out.println("Message ajouté à la file du satellite: " + message.getCompte());
	        }

	     
	        try {
	            Thread.sleep(TEMPS_CYCLE_MS);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        while (!queueCtr.estVide()) {
	            Message message = (Message) queueCtr.getTail().getData();
	            if (message instanceof Nack) {
	                Nack nackMessage = (Nack) message;
	                queueCtr.enleverElement();
	                System.out.println("Message envoyé au centre de contrôle: " + nackMessage.getCompte());
	            }
	        }
	    }
	}
	
	

}
