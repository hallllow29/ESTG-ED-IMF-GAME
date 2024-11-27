package lib;
import lib.exceptions.ElementNotFoundException;
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
		int index = getVertexIndex(vertex);

		if (index == -1 ) {
			throw new ElementNotFoundException("Element not found");
		}
	}

	@Override
	public void addEdge(T vertex1, T vertex2) {
		if (indexIsValid(index1) && indexIsValid(index2)) {
			adjMatrix[index1][index2] = true;
			adjMatrix[index2][index1] = true;
		}
	}

	@Override
	public void removeEdge(T vertex1, T vertex2) {

	}

	@Override
	public Iterator iteratorBFS(T startVertex) {
		return null;
	}

	@Override
	public Iterator iteratorDFS(T startVertex) {
		return null;
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
		// TODO: expand
	}

	private boolean indexIsValid(int index) {
		// TODO: indexIsVali
		return false;
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
