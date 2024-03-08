package modele.communication;

/**
 * Classe qui implémente le protocol de communication entre le Rover
 * et le Centre d'opération.
 * 
 * Il se base sur une interprétation libre du concept de Nack:
 * 	https://webrtcglossary.com/nack/
 *  
 * Les messages envoyés sont mémorisé. À l'aide du compte unique
 * le transporteur de message peut identifier les Messages manquant
 * dans la séquence et demander le renvoi d'un Message à l'aide du Nack.
 * 
 * Pour contourner la situation ou le Nack lui-même est perdu, le Nack
 * est renvoyé periodiquement, tant que le Message manquant n'a pas été reçu.
 * 
 * C'est également cette classe qui gère les comptes unique.
 * 
 * Les messages reçu sont mis en file pour être traité.
 * 
 * La gestion des messages reçu s'effectue comme une tâche s'exécutant indépendamment (Thread)
 * 
 * Quelques détails:
 *  - Le traitement du Nack a priorité sur tout autre message.
 *  - Un message NoOp est envoyé périodiquement pour s'assurer de maintenir
 *    une communication active et identifier les messages manquants en bout de séquence.
 * 
 * Services offerts:
 *  - TransporteurMessage
 *  - receptionMessageDeSatellite
 *  - run
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2024
 */


import utilitaires.LinkedList;
import utilitaires.Node;
import java.util.concurrent.locks.ReentrantLock;

public abstract class TransporteurMessage extends Thread {
    
    // Compteur de messages
    protected CompteurMessage compteurMsg;
    // Verrou pour protéger la liste des messages reçus
    private ReentrantLock lock = new ReentrantLock();
    protected LinkedList messagesRecus = new LinkedList();
    protected LinkedList messagesEnvoyes = new LinkedList();
    
    /**
     * Constructeur, initialise le compteur de messages unique
     */
    public TransporteurMessage() {
        compteurMsg = new CompteurMessage();        
    }
    
    /**
     * Méthode gérant les messages reçus du satellite. La gestion se limite
     * à l'implémentation du Nack, les messages spécialisés sont envoyés
     * aux classes dérivées
     * @param msg, message reçu
     */
    public void receptionMessageDeSatellite(Message msg) {
        lock.lock();
        try {
            if (msg instanceof Nack) {
                Node n = messagesRecus.getTail();
                int p = 0;
                while(n.getNext().getData() instanceof Nack){
                    p++;
                    n = n.getNext();
                }
                // Ajouter le Nack au début de la liste des messages reçus
                messagesRecus.ajouterElement(msg, p);
            } else {
                // Évaluer la position du message dans la liste
                int position = 0;
                Node currentNode = messagesRecus.getTail();
                while (currentNode != null && msg.getCompte() > ((Message)currentNode.getData()).getCompte()) {
                    position++;
                    currentNode = currentNode.getNext();
                }
                // Ajouter le message à la position trouvée
                messagesRecus.ajouterElement(msg, position);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        int compteCourant = 0;
        boolean nackEnvoye = false;

        while(true) {
            lock.lock();
            try {
                while (!messagesRecus.estVide() && !nackEnvoye) {
                    // Obtenir le prochain message à traiter (début de la liste)
                    Message msg = (Message) messagesRecus.getTail().getData();

                    if (msg instanceof Nack) {
                        // Obtenir le compte du message manquant
                        int compteManquant = msg.getCompte();

                        // Rechercher ce message dans la file des messages envoyés
                        // Enlever tous les messages avec un compte inférieur ou ceux de type Nack
                        Node currentNode = messagesEnvoyes.getTail();
                        while (currentNode != null && ((Message)currentNode.getData()).getCompte() != compteManquant) {
                            if (((Message)currentNode.getData()).getCompte() < compteManquant || currentNode.getData() instanceof Nack) {
                                messagesEnvoyes.enleverElement(messagesEnvoyes.getCount() - 1);
                            }
                            currentNode = currentNode.getNext();
                        }

                        // Vérifier s'il reste des messages dans la file
                        if (currentNode != null) {
                            // Obtenir le message à renvoyer (sans l'enlever)
                            Message msgAEnvoyer = (Message) currentNode.getData();

                            // Envoyer le message à répéter
                            envoyerMessage(msgAEnvoyer);

                            // Enlever le message Nack de la liste des reçus
                            messagesRecus.enleverElement(0);
                        }
                    } else if (msg.getCompte() > compteCourant) {
                        // Envoyer un Nack avec la valeur du message manquant (compteCourant)
                        envoyerMessage(new Nack(compteCourant));

                        // Marquer qu'un Nack a été envoyé (pour sortir de la boucle)
                        nackEnvoye = true;
                    } else if (msg.getCompte() < compteCourant) {
                        // Rejeter le message, car il s'agit d'un doublon
                        messagesRecus.enleverElement(0);
                    } else {
                        // Faire suivre le message au gestionnaireMessage
                        gestionnaireMessage(msg);

                        // Défiler le message
                        messagesRecus.enleverElement(0);

                        // Incrémenter le compteCourant
                        compteCourant++;
                    }
                }
            } finally {
                lock.unlock();
            }

            // Obtenir un nouveau compte unique (CompteurMsg)
            int nouveauCompte = compteurMsg.getCompteActuel();

            // Envoyer un message NoOp
            envoyerMessage(new NoOp(nouveauCompte));

            // Pause, cycle de traitement de messages
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Méthode abstraite utilisée pour envoyer un message
     * @param msg, le message à envoyer
     */
    abstract protected void envoyerMessage(Message msg);

    /**
     * Méthode abstraite utilisée pour effectuer le traitement d'un message
     * @param msg, le message à traiter
     */
    abstract protected void gestionnaireMessage(Message msg);
}
