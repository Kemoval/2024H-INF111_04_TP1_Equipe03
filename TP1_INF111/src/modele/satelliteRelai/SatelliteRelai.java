// Source code is decompiled from a .class file using FernFlower decompiler.
package modele.satelliteRelai;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import modele.communication.Message;
import utilitaires.LinkedList;

public class SatelliteRelai extends Thread {
   static final int TEMPS_CYCLE_MS = 500;
   static final double PROBABILITE_PERTE_MESSAGE = 0.15;
   ReentrantLock lock = new ReentrantLock();
   private Random rand = new Random();
   private LinkedList queueCtr = new LinkedList();
   private LinkedList queueRover = new LinkedList();

   public SatelliteRelai() {
   }

   public void envoyerMessageVersCentrOp(Message msg) {
      this.lock.lock();

      try {
         if (this.rand.nextDouble((double)System.currentTimeMillis()) > 0.15) {
            this.queueCtr.ajouterElement(msg);
         }
      } finally {
         this.lock.unlock();
      }

   }

   public void envoyerMessageVersRover(Message msg) {
      this.lock.lock();

      try {
         if (this.rand.nextDouble((double)System.currentTimeMillis()) > 0.15) {
            this.queueRover.ajouterElement(msg);
         }
      } finally {
         this.lock.unlock();
      }

   }

   public void run() {
      while(true) {
         this.queueRover.enleverElement();

         try {
            Thread.sleep(500L);
         } catch (InterruptedException var2) {
            var2.printStackTrace();
         }
      }
   }
}
