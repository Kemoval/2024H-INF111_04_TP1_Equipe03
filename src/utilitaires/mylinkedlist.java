package utilitaires;

public class mylinkedlist {

    private mynode tail;
    private int count;

    public mylinkedlist(){
        this.count = 0;
    }

    public boolean estVide(){
        return this.count == 0;
    }

    public mynode getTail() {
        return tail;
    }

    public void setTail(mynode tail) {
        this.tail = tail;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void ajouterElement(Object ele){
        mynode newnode = new mynode(ele);

        if (!estVide()) {
            newnode.setNext(tail);
        }
        this.tail = newnode;
        this.count+=1;
    }

    public void ajouterElement(Object ele, int pos) {
        mynode newnode = new mynode(ele);

        if(pos>count || pos < 0){
            System.out.println("Out of bounds");
        }else{
            if(pos!=0){
                if (!estVide()) {
                    mynode nodefront = tail;

                    for (int i = 0; i < pos-1; i++) {
                        nodefront = nodefront.getNext();
                    }
                    newnode.setNext(nodefront.getNext());
                    nodefront.setNext(newnode);
                }else if(estVide()) {

                }
            }else{
                newnode.setNext(null);
                this.tail = newnode;
            }
            setCount(this.count+1);
        }
    }

    public void enleverElement(){
        if(this.count > 0){
            mynode last = tail;
            while(last.getNext().getNext() != null){
                last = last.getNext();
            }
            last.setNext(null);
            this.count-=1;
        }
    }

    public void enleverElement(int pos){
        if(pos>=0 && pos < this.count){
            if(pos == 0){
                if(this.count == 1){
                    tail = null;
                }
                else{
                    tail = tail.getNext();
                }
            }
            else{
                mynode last = tail;
                for (int i = 0; i < pos-1; i++) {
                    last = last.getNext();
                }

                if(pos == this.count - 1){
                    last.setNext(null);
                }else{
                    last.setNext(last.getNext().getNext());
                }
            }
            this.setCount(this.count-1);
        }
    }
}
