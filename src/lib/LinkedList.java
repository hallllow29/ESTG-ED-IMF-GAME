package lib;

public class LinkedList<T> {

    private Node<T> head;
    private int count;

    public LinkedList() {
        this.head = null;
    }

    public void add(T data) {
        Node<T> newNode = new Node<T>(data);
        if (this.head == null) {
            this.head = newNode;
            this.count++;
        } else {
            Node<T> temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }

            temp.setNext(newNode);
            this.count++;
        }

    }

    public String print() {
        return printList(head);
    }

    //Recursive method to print all elemnts on the list
    private String printList(Node<T> node) {
        if (node == null) {
            return "";
        }

        return node.getData().toString() + "\n" + printList(node.getNext());
    }

    public Node<T> getFirst() {
        return this.head;
    }

    public void remove(T data) {
        //Check if the list is empty or not
        if (head == null) {
            System.out.println("A lista está vazia.");
            return;
        }

        //Check if the receive node is equals to the head
        if (head.getData().equals(data)) {
            head = head.getNext();
            count--;
            return;
        }

        //If all options above are false it will need to iterate through all the list to check where is the node remove it and replace the next to a new one till the
        //last one.
        Node<T> current = head;
        Node<T> previous = null;
        while (current != null && !current.getData().equals(data)) {
            previous = current;
            current = current.getNext();
        }

        if (current != null) {
            previous.setNext(current.getNext());
            count--;
        } else {
            System.out.println("Elemento não encontrado.");
        }
    }

}
