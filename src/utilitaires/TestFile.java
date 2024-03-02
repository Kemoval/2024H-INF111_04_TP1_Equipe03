// TestFile.java

package utilitaires;

public class TestFile {
    public static void main(String[] args) {
        // Créez une file de type Integer
        File<Integer> file = new File<>();

        System.out.println("File au debut: " + file);
        file.ajouterAvecPriorite(10);
        System.out.println("File apres ajout 10: " + file);
        file.ajouterAvecPriorite(5);
        System.out.println("File apres ajout 5: " + file);
        file.ajouterAvecPriorite(8);
        System.out.println("File apres ajout 8: " + file);
        file.enleverElement(5);
        System.out.println("File apres enlever 5: " + file);
        System.out.println("File: " + file);
        file.enleverElement(15);
        System.out.println("Si on essaie avec 15 ->" +file);
        System.out.println(file.estVide());
        file.enleverElement(8);
        file.enleverElement(10);
        System.out.println(file);
        System.out.println(file.estVide());
        file.enleverElement(null);

    }
}
