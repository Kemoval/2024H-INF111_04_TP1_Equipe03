package programme;

import java.io.IOException;

import modele.satelliteRelai.SatelliteRelai;

import utilitaires.Node;

import utilitaires.LinkedList;

public class ProgrammePrincipal {

	/**
	 * Programme principale, instancie les éléments de la simulation,
	 * les lie entre eux, puis lance la séquence de test.
	 * @param args, pas utilisé
	 */
	public static void main(String[] args){
	
		SatelliteRelai satellite = new SatelliteRelai();
		satellite.start();
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
	}

}
