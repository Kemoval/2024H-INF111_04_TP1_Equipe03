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
import modele.communication.Message;
import modele.rover.Rover.Rover;
import utilitaires.LinkedList;
import utilitaires.mylinkedlist;

public class SatelliteRelai extends Thread{
	
	static final int TEMPS_CYCLE_MS = 500;
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

			/*
			 * (5.1) Insérer votre code ici 
			 */
			if(rand.nextDouble(System.currentTimeMillis())>PROBABILITE_PERTE_MESSAGE){
				queueCtr.ajouterElement(msg);
			}
			
		}finally {
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

			/*
			 * (5.2) Insérer votre code ici 
			 */
			if(rand.nextDouble(System.currentTimeMillis())>PROBABILITE_PERTE_MESSAGE){
				queueRover.ajouterElement(msg);
			}
			
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		
		while(true) {

			/*
			 * (5.3) Insérer votre code ici 
			 */
			queueRover.enleverElement();
			// attend le prochain cycle
			try {
				Thread.sleep(TEMPS_CYCLE_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
