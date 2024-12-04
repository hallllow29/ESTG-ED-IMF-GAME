package lib;
import entities.Room;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.GraphADT;

import java.util.Iterator;

public class Graph <T> implements GraphADT<T>  {

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
		if (vertex == null) {
			throw new IllegalArgumentException("Cant be null");
		}
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

		if (index == -1) {
			throw new ElementNotFoundException("Element not found");
		}

		// Shifting to the left after removing...
		for (int i = index; i < this.numVertices - 1; i++) {
			this.vertices[i] = this.vertices[i + 1];
		}

		// Shifiting rows in Adjacency matrix...
		for (int row = index; row < this.numVertices - 1; row++) {
			if (this.numVertices >= 0)
				System.arraycopy(this.adjMatrix[row + 1], 0, this.adjMatrix[row], 0, this.numVertices);
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
		if (vertex1 == null || vertex2 == null) {
			System.out.println("Erro");
			return;
		}
		addEdge(getVertexIndex(vertex1), getVertexIndex(vertex2));
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

		if (index1 == -1) {
			throw new ElementNotFoundException("Vertex 1");
		}

		if (index2 == -1) {
			throw new ElementNotFoundException("Vertex 2");
		}

		removeEdge(getVertexIndex(vertex1), getVertexIndex(vertex2));
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

		traversalQueue.enqueue(Integer.valueOf(startIndex));
		visited[startIndex] = true;

		while (!traversalQueue.isEmpty()) {
			x = traversalQueue.dequeue();
			resultList.addToRear(vertices[x.intValue()]);

			for (int i = 0; i < numVertices; i++) {
				if (adjMatrix[x.intValue()][i] && !visited[i]) {
					traversalQueue.enqueue(Integer.valueOf(i));
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

		traversalStack.push(startIndex);
		resultList.addToRear(vertices[startIndex]);
		visited[startIndex] = true;

		while (!traversalStack.isEmpty()) {
			x = traversalStack.peek();
			found = false;

			for (int i = 0; (i < numVertices) && !found; i++) {
				if (adjMatrix[x][i] && !visited[i]) {
					traversalStack.push(i);
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
	public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws ElementNotFoundException {
		int startIndex = getVertexIndex(startVertex);
		int targetIndex = getVertexIndex(targetVertex);

		if (startIndex == -1 || targetIndex == -1) {
			throw new ElementNotFoundException("Um dos vértices não foi encontrado.");
		}

		final int INFINITO = Integer.MAX_VALUE;
		int[] distancias = new int[numVertices];
		int[] anteriores = new int[numVertices];
		boolean[] visitados = new boolean[numVertices];

		for (int i = 0; i < numVertices; i++) {
			distancias[i] = INFINITO;
			anteriores[i] = -1;
			visitados[i] = false;
		}
		distancias[startIndex] = 0;

		for (int i = 0; i < numVertices; i++) {
			int maisProximo = -1;
			for (int j = 0; j < numVertices; j++) {
				if (!visitados[j] && (maisProximo == -1 || distancias[j] < distancias[maisProximo])) {
					maisProximo = j;
				}
			}

			if (maisProximo == -1) break; //
			visitados[maisProximo] = true;

			for (int vizinho = 0; vizinho < numVertices; vizinho++) {
				if (adjMatrix[maisProximo][vizinho] && !visitados[vizinho]) {
					int novaDistancia = distancias[maisProximo] + 1;
					if (novaDistancia < distancias[vizinho]) {
						distancias[vizinho] = novaDistancia;
						anteriores[vizinho] = maisProximo;
					}
				}
			}
		}

		ArrayUnorderedList<T> caminho = new ArrayUnorderedList<>();
		int atual = targetIndex;
		while (atual != -1) {
			caminho.addToFront(vertices[atual]);
			atual = anteriores[atual];
		}

		if (distancias[targetIndex] == INFINITO) {
			return new ArrayUnorderedList<T>().iterator();
		}

		return caminho.iterator();
	}

	@Override
	public boolean isEmpty() {
		return numVertices == 0;
	}

	@Override
	public boolean isConnected() throws EmptyCollectionException {
		if (numVertices == 0) {
			return false;
		}

		Iterator<T> it = iteratorBFS(0);
		int count = 0;

		while (it.hasNext()) {
			it.next();
			count++;
		}

		return (count == numVertices);

	}

	@Override
	public int size() {
		return numVertices;
	}

	private void expandCapacity() {
		int newCapacity = vertices.length * 2;

		boolean[][] newAdjMatrix = new boolean[newCapacity][newCapacity];

		for (int i = 0; i < numVertices; i++) {
			System.arraycopy(adjMatrix[i], 0, newAdjMatrix[i], 0, numVertices);
		}

		adjMatrix = newAdjMatrix;

		T[] newVertices = (T[]) new Object[newCapacity];

		if (numVertices >= 0) System.arraycopy(vertices, 0, newVertices, 0, numVertices);

		vertices = newVertices;

	}

	protected boolean indexIsValid(int index) {
		return ((index < numVertices) && (index >= 0));
	}

	protected int getVertexIndex(T vertex) {
		for (int i = 0; i < numVertices; i++) {
			if (vertices[i].equals(vertex)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Retrieves a Room object from the graph based on its name.
	 *
	 * @param name the name of the room to be retrieved
	 * @return the Room object with the specified name if it exists in the graph, or null
	 * if no such room is found
	 */
	public Room getRoom(String name) {
		for (T vertex : vertices) {
			if (((Room) vertex).getName().equals(name)) {
				return (Room) vertex;
			}
		}
		return null;
	}

	public ArrayUnorderedList<T> getVertices() {
		ArrayUnorderedList<T> verticesList = new ArrayUnorderedList<>();
		// Object vertex;

		for (int i = 0; i < numVertices; i++) {
			// vertex = this.vertices[i];

			verticesList.addToRear(this.vertices[i]);
		}

		return verticesList;
	}

	public ArrayUnorderedList<T> getConnectedVertices(T vertex) {
		int index = getVertexIndex(vertex);

		if (index == -1) {
			throw new IllegalArgumentException("Graph");
		}
		ArrayUnorderedList<T> connectedVertices = new ArrayUnorderedList<>();

		for (int i = 0; i < this.numVertices; i++) {
			if (this.adjMatrix[index][i]) {
				connectedVertices.addToRear(vertices[i]);
			}
		}

		return connectedVertices;
	}
}
