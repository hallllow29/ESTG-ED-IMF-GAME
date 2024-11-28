package lib;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.GraphADT;

import java.util.Iterator;

public class Graph <T> implements GraphADT<T> {

	protected final int DEFAULT_CAPACITY = 10;
	protected int numVertices; // number of vertices in the graph
	protected boolean[][] adjMatrix; // adjacency matrix
	protected T[] vertices; // values of vertices

	/**
	 * Constructs an empty graph with an initial default capacity for vertices.
	 * Initializes the adjacency matrix and the array to hold vertex values.
	 */
	public Graph() {
		numVertices = 0;
		this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
		this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
	}

	@Override
	public void addVertex(T vertex) {
		if (numVertices == vertices.length)
			expandCapacity();
		vertices[numVertices] = vertex;
		for (int i = 0; i <= numVertices; i++) {
			adjMatrix[numVertices][i] = false;
			adjMatrix[i][numVertices] = false;
		}
		numVertices++;
	}

	@Override
	public void removeVertex(T vertex) throws ElementNotFoundException {

		// Finding the index of the vertex
		int index = getVertexIndex(vertex);

		if (index == -1 ) {
			throw new ElementNotFoundException("Element not found");
		}

		// Shifting to the left after removing...
		for (int i = index; i < this.numVertices - 1; i++) {
			this.vertices[i] = this.vertices[i + 1];
		}

		// Shifiting rows in Adjacency matrix...
		for (int row = index; row < this.numVertices - 1; row++) {
			for (int col = 0; col < this.numVertices; col++) {
				this.adjMatrix[row][col] = this.adjMatrix[row + 1][col];
			}
		}

		// Fix the removed column of the vertex...
		for (int col = index; col < this.numVertices - 1; col++) {
			for (int row = 0; row < this.numVertices - 1; row++) {
				this.adjMatrix[row][col] = this.adjMatrix[row][col + 1];
			}
		}

		// Last goes null
		this.vertices[numVertices - 1] = null;

		this.numVertices--;
	}

	@Override
	public void addEdge(T vertex1, T vertex2) {
		addEdge (getVertexIndex(vertex1), getVertexIndex(vertex2));
	}

	public void addEdge(int index1, int index2) {


		if (indexIsValid(index1) && indexIsValid(index2)) {
			adjMatrix[index1][index2] = true;
			adjMatrix[index2][index1] = true;
		}
	}

	@Override
	public void removeEdge(T vertex1, T vertex2) throws ElementNotFoundException {

		int index1 = getVertexIndex(vertex1);
		int index2 = getVertexIndex(vertex2);

		if (index1 == -1 ) {
			throw new ElementNotFoundException("Vertex 1");
		}

		if (index2 == -1) {
			throw new ElementNotFoundException("Vertex 2");
		}

		removeEdge (getVertexIndex(vertex1), getVertexIndex(vertex2));
	}

	public void removeEdge(int index1, int index2) {
		if (indexIsValid(index1) && indexIsValid(index2)) {
			adjMatrix[index1][index2] = false;
			adjMatrix[index2][index1] = false;
		}
	}

	public Iterator<T> iteratorBFS(T startVertex) throws EmptyCollectionException {
		int vertexIndex = getVertexIndex(startVertex);
		return iteratorBFS(vertexIndex);
	}

	@Override
	public Iterator<T> iteratorDFS(T startVertex) throws EmptyCollectionException {
		int vertexIndex = getVertexIndex(startVertex);
		return iteratorDFS(vertexIndex);
	}

	public Iterator<T> iteratorBFS(int startIndex) throws EmptyCollectionException {
		Integer x;
		LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
		ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

		if (!indexIsValid(startIndex)) {
			return resultList.iterator();
		}

		boolean[] visited = new boolean[numVertices];

		for (int i = 0; i < numVertices; i++) {
			visited[i] = false;
		}

		traversalQueue.enqueue(new Integer(startIndex));
		visited[startIndex] = true;

		while (!traversalQueue.isEmpty()) {
			x = traversalQueue.dequeue();
			resultList.addToRear(vertices[x.intValue()]);

			for (int i = 0; i < numVertices; i++) {
				if (adjMatrix[x.intValue()][i] && !visited[i]) {
					traversalQueue.enqueue(new Integer(i));
					visited[i] = true;
				}
			}
		}
		return resultList.iterator();
	}


	public Iterator<T> iteratorDFS(int startIndex) throws EmptyCollectionException {
		Integer x;
		boolean found;
		LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
		ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
		boolean[] visited = new boolean[numVertices];

		if (!indexIsValid(startIndex)) {
			return resultList.iterator();
		}

		for (int i = 0; i < numVertices; i++) {
			visited[i] = false;
		}

		traversalStack.push(new Integer(startIndex));
		resultList.addToRear(vertices[startIndex]);
		visited[startIndex] = true;

		while (!traversalStack.isEmpty()) {
			x = traversalStack.peek();
			found = false;

			for (int i = 0; (i < numVertices) && !found; i++) {
				if (adjMatrix[x.intValue()][i] && !visited[i])
				{
					traversalStack.push(new Integer(i));
					resultList.addToRear(vertices[i]);
					visited[i] = true;
					found = true;
				}
			}
			if (!found && !traversalStack.isEmpty()) {
				traversalStack.pop();
			}
		}
		return resultList.iterator();
	}

	@Override
	public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public int size() {
		return 0;
	}

	private void expandCapacity() {

	}

	private boolean indexIsValid(int index) {
		return ((index < numVertices) && (index >= 0));
	}

	private int getVertexIndex(T vertex) {
		for (int i = 0; i < numVertices; i++) {
			if (vertices[i].equals(vertex)) {
				return i;
			}
		}
		return -1;
	}
}
