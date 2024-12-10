/**
 * @author 8230068, 8230069
**/

package lib.exceptions;
/**
 * The EmptyCollectionException is a custom exception class that is thrown to indicate
 * that a specific operation could not be performed because the custom used collection is empty.
 */
public class EmptyCollectionException extends Exception
{
	/**
	 * Constructs a new EmptyCollectionException with the specified detail message.
	 *
	 * @param message the detailed exception message indicating the nature or source of the empty collection error
	 */
	public EmptyCollectionException(String message) {
		super(message);
	}

}
