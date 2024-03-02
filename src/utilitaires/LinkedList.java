package utilitaires;

public class LinkedList {

    private Node tail;
    private int count = 0;

    public LinkedList() {
    }

    public boolean estVide() {
        return this.count == 0;
    }

    public Node getTail() {
        return this.tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void ajouterElement(Object ele) {
        Node newnode = new Node(ele);
        if (!this.estVide()) {
            newnode.setNext(this.tail);
        }

        this.tail = newnode;
        ++this.count;
    }

    public void ajouterElement(Object ele, int pos) {
        Node newnode = new Node(ele);
        if (pos <= this.count && pos >= 0) {
            if (pos == 0) {
                newnode.setNext((Node)null);
                this.tail = newnode;
            } else if (this.estVide()) {
                if (this.estVide()) {
                }
            } else {
                Node nodefront = this.tail;

                for(int i = 0; i < pos - 1; ++i) {
                    nodefront = nodefront.getNext();
                }

                newnode.setNext(nodefront.getNext());
                nodefront.setNext(newnode);
            }

            this.setCount(this.count + 1);
        } else {
            System.out.println("Out of bounds");
        }

    }

    public void enleverElement() {
        if (this.count > 0) {
            Node last;
            for(last = this.tail; last.getNext().getNext() != null; last = last.getNext()) {
            }

            last.setNext((Node)null);
            --this.count;
        }

    }

    public void enleverElement(int pos) {
        if (pos >= 0 && pos < this.count) {
            if (pos == 0) {
                if (this.count == 1) {
                    this.tail = null;
                } else {
                    this.tail = this.tail.getNext();
                }
            } else {
                Node last = this.tail;

                for(int i = 0; i < pos - 1; ++i) {
                    last = last.getNext();
                }

                if (pos == this.count - 1) {
                    last.setNext((Node)null);
                } else {
                    last.setNext(last.getNext().getNext());
                }
            }

            this.setCount(this.count - 1);
        }

    }
}
