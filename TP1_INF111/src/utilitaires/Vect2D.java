package utilitaires;

public class Vect2D {
 private double x;
 private double y;
 
 public Vect2D() {
	 this.x = 0.0;
	 this.y = 0.0;
 }
 
 public Vect2D(double x, double y) {
	 this.x = x;
	 this.y = y;
 }
 
 public Vect2D (Vect2D vecteur) {
	 this.x = vecteur.x;
	 this.y = vecteur.y;
 }
 
 public double getX() {
	 return this.x;
 }
 
 public double getY() {
	 return this.y;
 }
 
 public double getLongueur(){
	 return Math.sqrt(x * x + y * y);
 }
 
 public double getAngle() {
	 return Math.atan2(y, x);
 }
 
 public Vect2D calculerDiff(Vect2D posFin) {
     double diffX = posFin.getX() - this.x;
     double diffY = posFin.getY() - this.y;
     return new Vect2D(diffX, diffY);
 }
 
 public void diviser(double a) {
	 this.x /= a;
	 this.y /= a;
 }
 
 public void ajouter(double x, double y) {
	 this.x += x;
	 this.y += y;
 }
 
 public String toString() {
	 return "Vect2D{" + "x = " + x + " , y = " + y + "}";
 
 }
 
 public boolean equals(Object obj) {
     if (this == obj)
         return true;
     if (obj == null || getClass() != obj.getClass())
         return false;
     Vect2D vecteur = (Vect2D) obj;
     return Double.compare(vecteur.x, x) == 0 && Double.compare(vecteur.y, y) == 0;
 }
 
}

