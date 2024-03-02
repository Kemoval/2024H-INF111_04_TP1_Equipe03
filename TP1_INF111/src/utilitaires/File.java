package utilitaires;

import java.util.ArrayList;

class File<T extends Comparable<T>> {
	private ArrayList<T> elements = new ArrayList<>();

    public File() {
    	// file vide
    }

    public File(ArrayList<T> initialElements) {
        this.elements.addAll(initialElements);
    }
    
    public void ajouterAvecPriorite(T e) {
        for (T elt : elements) {
            if (elt.compareTo(e) > 0) {
                elements.add(elements.indexOf(elt), e);
                return;
            }
        }
        elements.add(e);
    }

    public void enleverElement(T e) {
        if (!elements.isEmpty()) {
            if (e instanceof Integer) {
                elements.remove(e);
            } else {
                elements.remove(elements.size() - 1);
            }
        }
    }

    public boolean estVide() {
        return elements.isEmpty();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (T element : elements) {
            sb.append(element).append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}