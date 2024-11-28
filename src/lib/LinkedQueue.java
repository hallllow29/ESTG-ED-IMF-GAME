
package lib;

import lib.exceptions.EmptyCollectionException;
public class LinkedQueue <T> {

	private Node<T> front;
	private Node<T> rear;
	private int size;

	public LinkedQueue(){
		front = null;
		rear = null;
		size = 0;
	}

	public void enqueue(T data) {
		Node<T> newNode = new Node();
		if (isEmpty()) {
			front = newNode;
			rear = newNode;
			newNode.setData(data);
		} else {
			rear.setNext(newNode);
			rear = newNode;
			rear.setData(data);
		}
		size++;
	}

	public T dequeue() throws EmptyCollectionException {
		if(isEmpty()){
			throw new EmptyCollectionException("Queue");
		}
		Node<T> newFront = front.getNext();
		T result = front.getData();
		front = null;
		front = newFront;
		size--;
		return result;
	}

	public boolean isEmpty(){
		return getSize() == 0 || front == null;
	}

	public int getSize(){
		return size;
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "[]";
		}
		String result = " <- FRONT [";
		Node<T> current = front;
		while (current != null) {
			result += current.getData() + ", ";
			current = current.getNext();
		}
		return result + "] <- BACK";
	}

}
