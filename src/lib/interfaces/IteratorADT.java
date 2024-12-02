/**
 * @author      8230069
 * @file        IteratorADT.java
 * @copyright   ESTG IPP
 * @brief       ED, Ficha Prática 6, Exercicio 4
 * @date        2024/11/01
**/

package lib.interfaces;

public interface IteratorADT <E> {

	/**
	 * Returns true if the iteration has more elements.
	 *
	 * @return true if the iteration has more elements
	 */
	boolean hasNext();

	/**
	 * Returns the next element in the iteration.
	 *
	 * @return the next element in the iteration
	 */
	E next();

	/**
	 * Removes from the underlying collection the last element returned by this iterator.
	 * This method can be called only once per call to next(). The behavior of an iterator
	 * is unspecified if the underlying collection is modified while the iteration is in
	 * progress in any way other than by calling this method.
	 *
	 * This implementation throws UnsupportedOperationException.
	 *
	 * @throws UnsupportedOperationException if the remove operation is not supported by this iterator
	 */
	default void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
