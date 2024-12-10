/**
 * @author 8230068, 8230069
**/

package lib.exceptions;


/**
 * Exception thrown to indicate that an operation requires elements to be comparable,
 * but the provided elements do not meet this requirement.
 *
 */
public class NotElementComparableException extends Throwable {

    /**
     * Constructs a {@code NotElementComparableException} with the specified detail message.
     *
     * @param message the detail message, which provides information about the exception
     */
    public NotElementComparableException(String message) {
        super(message);
    }
}