/**
 * @author 8230068, 8230069
**/

package lib.exceptions;

/**
 * This exception is thrown to indicate that a specific element
 * could not be found during the execution of a custom collection.
 */
public class ElementNotFoundException extends Exception {

	/**
	 * Constructs a new ElementNotFoundException with a specified detail message.
	 *
	 * @param message the detail message providing additional context about the exception
	 */
	public ElementNotFoundException(String message) {
		super(message);
	}

}
