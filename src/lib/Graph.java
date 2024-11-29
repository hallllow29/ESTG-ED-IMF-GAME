package lib;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.GraphADT;

import java.util.Iterator;

public class Graph <T> implements GraphADT<T> {

	protected final int DEFAULT_CAPACITY = 15;
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
		ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

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

			/**
			 * Find all vertices adjacent to x that have not been visited and
			 * queue them up.
			 */
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

		traversalStack.push(Integer.valueOf(startIndex));
		resultList.addToRear(vertices[startIndex]);
		visited[startIndex] = true;

		while (!traversalStack.isEmpty()) {
			x = traversalStack.peek();
			found = false;

			/** Find a vertex adjacent to x that has not been visited
			 and push it on the stack */
			for (int i = 0; (i < numVertices) && !found; i++) {
				if (adjMatrix[x.intValue()][i] && !visited[i]) {
					traversalStack.push(Integer.valueOf(i));
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
	public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws ElementNotFoundException, EmptyCollectionException {
		Integer x;
		int startIndex = getVertexIndex(startVertex);
		int targetIndex = getVertexIndex(targetVertex);

		if (startIndex == -1 || targetIndex == -1) {
			throw new ElementNotFoundException("Vertex");
		}

		LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
		ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
		int[] distances = new int[this.numVertices];

		// ...which would be an array for visited?
		boolean[] tight = new boolean[this.numVertices];

		int[] previous = new int[this.numVertices];

		for (int i = 0; i < numVertices; i++) {
			// All other vertices D[z] as an overestimation to INF.
			distances[i] = Integer.MAX_VALUE;
			// Initialize a tight array for every vertex to false.
			// So you recognize which ones have accurate estimate.
			tight[i] = false;
			previous[i] = -1;
		}

		// Set the distance D[s] to 0.
		distances[startIndex] = 0;

		// ...as well as tighted/visited...
		tight[startIndex] = true;

		// lets then enqueue this vertex
		traversalQueue.enqueue(startIndex);

		// for backtracing purpose...
		// resultList.addToRear(vertices[startIndex]);

		// lets repeat "almost" the same as in BFS?

		boolean is_there_a_path = false;

		while (!traversalQueue.isEmpty()) {
			x = traversalQueue.dequeue();
			System.out.println("WHILE " + x);
			for (int i = 0; i < numVertices; i++) {
				if (adjMatrix[x.intValue()][i] && !tight[i]) {
					// I was traversed
					traversalQueue.enqueue(i);
					// Accurate overestimation for i
					tight[i] = true;

					previous[i] = x;

					// Uniform cost of 1  (has no weight man...)
					// distances[i] = distances[i] + 1;
					System.out.println("i = " + i + " visited = " + true + " previous of " + i + " is " + x);
					// What if? better... what should happen if i reaches the
					// same index as targetIndex?
					if (i == targetIndex) {
						// IT means termination...
						// Backtracking is now needed to build the path, right?
						// Probably with the previous[i]...
						// TECHNICALLY...
						// Can be an an LinkedList or ArrayList!
						// return resultList.iterator();
						// JUST BREAK? ...Okay... but then it will be
						// n * n * n Hold up.
						for (int v = targetIndex; v != -1; v = previous[v]) {
							resultList.addToFront(vertices[v]);
						}
						return resultList.iterator();
					}
				}
			}

		}
		return resultList.iterator();

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
