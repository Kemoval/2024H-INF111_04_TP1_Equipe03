package utilitaires;

public class Vect2D {

    private double X;
    private double Y;

    public Vect2D(double x, double y) {
        this.X = x;
        this.Y = y;
    }

    public Vect2D(){
        this.X = 0;
        this.Y = 0;
    }

    public Vect2D(Vect2D vector){
        this.X = vector.X;
        this.Y = vector.Y;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public void setX(double x) {
        X = x;
    }

    public void setY(double y) {
        Y = y;
    }

    public double getLength(){
        return Math.sqrt(getX()*getX()+getY()*getY());
    }

    public double getAngle(){
        return Math.atan2(getY(), getX());
    }

    public Vect2D calculerDiff(Vect2D another){
        double x = Math.abs(this.getX() - another.getX());
        double y = Math.abs(this.getY() - another.getY());
        return new Vect2D(x,y);
    }

    public void Diviser(double a){
        this.setX(this.getX()/a);
        this.setY(this.getY()/a);
    }

    public void ajouter(double x, double y){
        this.setX(this.getX()+x);
        this.setY(this.getY()+y);
    }

    public boolean equals(Vect2D another){
        return (this.getX() == another.getX() &&
                this.getY() == another.getY());
    }


    public String toString(){
        return("X: "+this.getX()+"\nY: "+this.getY());
    }
}
