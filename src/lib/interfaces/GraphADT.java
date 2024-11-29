package lib.interfaces;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;

public interface GraphADT <T> {

	/**
	 * Adds a vertex to the graph.
	 *
	 * @param vertex the vertex to be added to the graph
	 */
	void addVertex(T vertex);

	/**
	 * Removes a vertex from the graph.
	 *
	 * @param vertex the vertex to be removed from the graph
	 */
	void removeVertex(T vertex) throws ElementNotFoundException;

	/**
	 * Adds an edge between two specified vertices in the graph.
	 *
	 * @param vertex1 the first vertex to be connected by the edge
	 * @param vertex2 the second vertex to be connected by the edge
	 */
	void addEdge(T vertex1, T vertex2);

	/**
	 * Removes an edge between two specified vertices in the graph.
	 *
	 * @param vertex1 the first vertex connected by the edge
	 * @param vertex2 the second vertex connected by the edge
	 */
	void removeEdge(T vertex1, T vertex2) throws ElementNotFoundException;

	/**
	 * Returns an iterator that performs a breadth-first search (BFS) starting from the specified vertex.
	 *
	 * @param startVertex the starting vertex for the BFS traversal
	 * @return an iterator for the BFS traversal of the graph
	 */
	Iterator<T> iteratorBFS(T startVertex) throws EmptyCollectionException;

	/**
	 * Returns an iterator that performs a depth-first search (DFS) starting from the specified vertex.
	 *
	 * @param startVertex the starting vertex for the DFS traversal
	 * @return an iterator for the DFS traversal of the graph
	 */
	Iterator iteratorDFS(T startVertex) throws EmptyCollectionException;

	/**
	 * Returns an iterator that provides the shortest path between two vertices in the graph.
	 *
	 * @param startVertex the starting vertex of the path
	 * @param targetVertex the target vertex of the path
	 * @return an iterator for the shortest path from startVertex to targetVertex in the graph
	 */
	Iterator iteratorShortestPath(T startVertex, T targetVertex) throws ElementNotFoundException, EmptyCollectionException;

	/**
	 * Checks if the graph is empty.
	 *
	 * @return true if the graph contains no vertices, false otherwise
	 */
	boolean isEmpty();

	/**
	 * Checks if the entire graph is connected. A graph is considered connected if there is a path between any two vertices.
	 *
	 * @return true if the graph is connected, false otherwise
	 */
	boolean isConnected();

	/**
	 * Returns the number of vertices in the graph.
	 *
	 * @return the number of vertices in the graph
	 */
	int size();

	/**
	 * Returns a string representation of the graph.
	 *
	 * @return a string description of the graph
	 */
	String toString();

}
