package utilitaires;

public class mynode {
    private Object data;
    private mynode next;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public mynode getNext() {
        return next;
    }

    public void setNext(mynode next) {
        this.next = next;
    }

    public mynode(Object data) {
        this.data = data;
        this.next = null;
    }
}
